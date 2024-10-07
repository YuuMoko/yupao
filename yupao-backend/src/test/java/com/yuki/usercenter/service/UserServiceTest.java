package com.yuki.usercenter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        String userAccount = "123@4";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        System.out.println(userService.userRegister(userAccount, userPassword, checkPassword));
    }

}
