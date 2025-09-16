package com.vozni.springjwt.filter;

import com.vozni.springjwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        // allow auth endpoints and static resources through
        if (path.startsWith("/api/auth/") || !path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (!jwtUtil.validateToken(token)) {
                    // Let entry point handle unauthorized response
                    filterChain.doFilter(request, response);
                    return;
                }

                Claims claims = jwtUtil.getClaims(token);
                String username = jwtUtil.getUsername(claims);
                List<String> roles = jwtUtil.getRoles(claims);

                // convert roles to GrantedAuthority (Spring expects "ROLE_" optionally)
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(r -> {
                            // ensure prefix ROLE_ if your code expects it. adjust as needed.
                            if (r.startsWith("ROLE_")) return new SimpleGrantedAuthority(r);
                            return new SimpleGrantedAuthority("ROLE_" + r);
                        })
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // MDC for logging footprint (cleared in finally)
                MDC.put("user", username);
                MDC.put("requestId", request.getHeader("X-Request-Id") != null
                        ? request.getHeader("X-Request-Id")
                        : java.util.UUID.randomUUID().toString());

                // You can store additional attributes on the request if controllers need it
                request.setAttribute("user", username);
            }
        } catch (Exception ex) {
            log.warn("JWT processing failed: {}", ex.getMessage());
            // Don't rethrow here; let exception handling in SecurityConfig handle unauthenticated/forbidden
        }

        try {
            filterChain.doFilter(request, response);
        }finally {
            MDC.remove("user");
            MDC.remove("requestId");
            // Important: clear security context only if you created it here in some designs, but
            // do not clear globally if other mechanisms rely on it. Generally leave Spring to manage lifecycle.
        }
    }
}
