package com.vozni.springjwt.mock;

import com.vozni.springjwt.model.entity.User;
import com.vozni.springjwt.model.entity.Role;
import com.vozni.springjwt.model.entity.Roles;
import com.vozni.springjwt.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
@AllArgsConstructor
public class DataEntries implements CommandLineRunner {
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User().setUsername("admin").setPassword(passwordEncoder.encode("admin"));
        admin.setRoleSet(Set.of(new Role().setRole(Roles.ADMIN),new Role().setRole(Roles.USER)));
        userService.save(admin);

        User user = new User().setUsername("user").setPassword(passwordEncoder.encode("user"));
        user.setRoleSet(Set.of(new Role().setRole(Roles.USER)));
        userService.save(user);
    }
}

