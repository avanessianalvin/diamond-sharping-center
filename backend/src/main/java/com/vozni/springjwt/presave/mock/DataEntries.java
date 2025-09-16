package com.vozni.springjwt.presave.mock;

import com.vozni.springjwt.model.entity.User;
import com.vozni.springjwt.model.entity.Role;
import com.vozni.springjwt.model.entity.Roles;
import com.vozni.springjwt.model.service.RoleService;
import com.vozni.springjwt.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
@AllArgsConstructor
public class DataEntries implements CommandLineRunner {
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {

        List<Role> roleList = roleService.all();


        Role adminRole =  roleList.get(0);//new Role().setRole(Roles.ADMIN);
        Role userRole = roleList.get(1);//new Role().setRole(Roles.USER);

        User admin = new User().setUsername("admin").setPassword(passwordEncoder.encode("admin"));
        admin.setRoleSet(Set.of(adminRole, userRole));
        userService.save(admin);

        for (int i = 0; i < 25; i++) {
            User user = new User().setUsername("user"+i).setPassword(passwordEncoder.encode("user"+i));
            user.setRoleSet(Set.of(userRole));
            userService.save(user);
        }

    }
}

