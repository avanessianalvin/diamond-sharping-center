package com.vozni.springjwt.model.repository;

import com.vozni.springjwt.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDA extends JpaRepository<Role,Long> {
}
