package com.vozni.springjwt.model.service;

import com.vozni.springjwt.model.entity.User;
import com.vozni.springjwt.model.repository.UserDA;
import com.vozni.springjwt.model.repository.UserSpecs;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserDA userDA;

    public void save(User user){
        userDA.save(user);
    }

    public User get(String username){
        return userDA.findByUsernameIgnoreCase(username);
    }

    public void remove(User user) {
        userDA.delete(user);
    }

    public List<User> getAll() {

        return userDA.findAll();
    }

    public Page<User> getByParams(Filter filter, Pageable pageable) {

        Specification<User> spec = Specification.<User>unrestricted()
                .and(UserSpecs.usernameContains(filter.username()))
                .and(UserSpecs.hasRole(filter.role()));

        return userDA.findAll(spec, pageable);
    }

    public static record Filter(
            String username,
            String role
    ) {}
}
