package com.vozni.springjwt.model.service;

import com.vozni.springjwt.model.entity.User;
import com.vozni.springjwt.model.repository.UserDA;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Object getAll() {
        return userDA.findAll();
    }
}
