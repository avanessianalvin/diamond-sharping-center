package com.vozni.springjwt.api;

import com.vozni.springjwt.api.response.ApiResponse;
import com.vozni.springjwt.model.entity.User;
import com.vozni.springjwt.model.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/admin")
@RestController
@AllArgsConstructor
public class Admin {
    private UserService userService;


    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        log.error("/ping");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth == null) ? null : auth.getName();
        return ResponseEntity.ok(ApiResponse.success("Hello" + username).setMessage(String.valueOf(System.currentTimeMillis())));
    }


}
