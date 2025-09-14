package com.vozni.springjwt.model.repository;

import com.vozni.springjwt.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDA extends JpaRepository<User,Long> {
    public User findByUsernameIgnoreCase(String username);
}
