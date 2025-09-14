
package com.vozni.springjwt.model.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class UserTest {
    @Test
    public void createPersonTest() {
        User user = new User().setUsername("Alvin");
        System.out.println(user);
    }
}
