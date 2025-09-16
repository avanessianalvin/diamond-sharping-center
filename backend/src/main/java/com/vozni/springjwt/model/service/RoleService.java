package com.vozni.springjwt.model.service;

import com.vozni.springjwt.model.entity.Role;
import com.vozni.springjwt.model.repository.RoleDA;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoleService {
    private RoleDA roleDA;

    public void save(Role role){
        roleDA.save(role);
    }

    public List<Role> all(){
        return roleDA.findAll();
    }

}
