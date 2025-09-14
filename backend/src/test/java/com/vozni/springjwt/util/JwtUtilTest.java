package com.vozni.springjwt.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @Test
    void generateToken() {
        Map<String,Object> claims = new HashMap<>();
        claims.put("role1","ADMIN");
        String username = "allwin07@yahoo.com";
        String token = jwtUtil.generateAccessToken(username,claims);
        String gotUsername = jwtUtil.getUsername(token);
        assertEquals(gotUsername,username);
    }
}