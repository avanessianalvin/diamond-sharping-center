package com.vozni.springjwt.api;

import com.vozni.springjwt.api.response.ApiResponse;
import com.vozni.springjwt.model.entity.User;
import com.vozni.springjwt.model.entity.Role;
import com.vozni.springjwt.model.entity.Roles;
import com.vozni.springjwt.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/api/user")
@RestController
@AllArgsConstructor
public class UserApi {
    private UserService userService;

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
}
