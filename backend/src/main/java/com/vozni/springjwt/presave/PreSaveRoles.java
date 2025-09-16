package com.vozni.springjwt.presave;

import com.vozni.springjwt.model.entity.Role;
import com.vozni.springjwt.model.entity.Roles;
import com.vozni.springjwt.model.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MIN_VALUE+10)
@AllArgsConstructor
public class PreSaveRoles implements CommandLineRunner {

    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        for (Roles roleEnum : Roles.values()) {
            Role role = new Role().setRole(roleEnum);
            roleService.save(role);
        }
    }
}
