package com.vozni.springjwt.api;

import com.vozni.springjwt.api.response.ApiResponse;
import com.vozni.springjwt.model.entity.User;
import com.vozni.springjwt.model.entity.Role;
import com.vozni.springjwt.model.entity.Roles;
import com.vozni.springjwt.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/api/user")
@RestController
@AllArgsConstructor
public class UserApi {
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.get(username);
        return ResponseEntity.ok(ApiResponse.success(user));
    }
    @GetMapping("/ping")
    public ResponseEntity<?> ping(){
        return ResponseEntity.ok(ApiResponse.success(null).setMessage(String.valueOf(System.currentTimeMillis())));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<User>> save(@RequestBody User user){
        // if user has no roles, set USER role to person
        Set<Role> roleSet = user.getRoleSet();
        if (roleSet==null) user.setRoleSet(new HashSet<>());
        user.getRoleSet().add(new Role().setRole(Roles.USER));

        userService.save(user);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestAttribute("username") String username){
        return ResponseEntity.ok(ApiResponse.success(userService.get(username)));
    }

    @GetMapping("/islogged")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(ApiResponse.success("logged in"));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/remove")
    public ResponseEntity<ApiResponse<?>> removeUser(@RequestBody User user) {
        userService.remove(user);
        return ResponseEntity.ok(ApiResponse.success("User is removed").setMessage("User is removed"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> allUsers(@ModelAttribute UserService.Filter filter,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        System.out.println(page + "  "+ size);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(userService.getByParams(filter,pageable )));
    }

}
