package com.vozni.springjwt.filter;

import com.vozni.springjwt.model.entity.Role;
import com.vozni.springjwt.model.entity.Roles;
import com.vozni.springjwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/api/auth/") || !path.startsWith("/api/")) {
            filterChain.doFilter(request,response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            if(jwtUtil.validateToken(token)){
                Claims claims = jwtUtil.getClaims(token);
                @SuppressWarnings("unchecked")
                List<String> roleNames = claims.get("roles", List.class);

                request.setAttribute("user",claims.getSubject());

                if (path.startsWith("/api/admin/") && !roleNames.contains("ADMIN")){
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().println("YOU ARE NOT ADMIN");
                    return;
                }

            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("NOT AUTH!!!!");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("NOT AUTH!!!!");
            return;
        }

        filterChain.doFilter(request,response);
    }
}
