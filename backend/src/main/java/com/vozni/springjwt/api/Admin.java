package com.vozni.springjwt.api;

import com.vozni.springjwt.api.response.ApiResponse;
import com.vozni.springjwt.model.entity.User;
import com.vozni.springjwt.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@RestController
@AllArgsConstructor
public class Admin {
    private UserService userService;

    @GetMapping("/ping")
    public ResponseEntity<?> ping(){
        return ResponseEntity.ok(ApiResponse.success(null).setMessage(String.valueOf(System.currentTimeMillis())));
        }

    @PostMapping("/user/remove")
    public ResponseEntity<ApiResponse<?>> removeUser(@RequestBody User user){
        userService.remove(user);
        return ResponseEntity.ok(ApiResponse.success("User is removed"));
    }

    @GetMapping("/user/all")
    public ResponseEntity<ApiResponse<?>> allUsers(){
        return ResponseEntity.ok(ApiResponse.success(userService.getAll()));
    }



}
