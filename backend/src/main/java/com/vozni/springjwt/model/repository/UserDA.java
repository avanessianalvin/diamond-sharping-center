package com.vozni.springjwt.model.repository;

import com.vozni.springjwt.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDA extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    public User findByUsernameIgnoreCase(String username);
    public Page<User> findAll(Specification<User> spec, Pageable pageable);
}
