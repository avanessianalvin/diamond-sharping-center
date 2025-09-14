package com.vozni.springjwt.api;

import com.vozni.springjwt.api.response.ApiResponse;
import com.vozni.springjwt.model.entity.User;
import com.vozni.springjwt.model.service.UserService;
import com.vozni.springjwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthApi {

    private JwtUtil jwtUtil;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> signup(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody User user){

        User u = userService.get(user.getUsername());
        if (u == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid credentials");
        }

        if (!passwordEncoder.matches(user.getPassword(),u.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials");
        }

        Map<String,Object> claims = new HashMap<>();

        claims.put("roles", u.getRoleSet().stream().map(r->r.getRole().name()).collect(Collectors.toList()));

        String accessToken = jwtUtil.generateAccessToken(user.getUsername(),claims);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());


        ResponseCookie cookie = ResponseCookie.from("refreshToken")
                .httpOnly(true)
                .secure(true)              // only send over HTTPS
                .path("/api/auth/refresh")     // only sent to refresh endpoint
                .sameSite("Strict")        // prevent CSRF
                .maxAge(Duration.ofDays(7))
                .value(refreshToken)
                .build();


        Map<String,Object> data = new HashMap<>();
        data.put("accessToken",accessToken);
        data.put("user",u);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(ApiResponse.success(data).setMessage("Logged in successfully"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken){
        if(refreshToken != null && jwtUtil.validateToken(refreshToken)){
            String username = jwtUtil.getUsername(refreshToken);
            User user = userService.get(username);
            Map<String,Object> claims = new HashMap<>();
            claims.put("roles", user.getRoleSet().stream().map(r->r.getRole().name()).collect(Collectors.toList()));
            String accessToken = jwtUtil.generateAccessToken(username,claims);
            return ResponseEntity.ok(ApiResponse.success(Map.of("accessToken",accessToken)));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid refresh token");
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(){

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")   // must match the original path!
                .sameSite("Strict")
                .maxAge(0)                   // expire immediately
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,deleteCookie.toString()).body(ApiResponse.success(null).setMessage("Logged out successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            if(jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.getClaims(token);
                String username = claims.getSubject();
                if (username != null) {
                    User user = userService.get(username);
                    return ResponseEntity.ok(ApiResponse.success(user));
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not logged in");
    }

}