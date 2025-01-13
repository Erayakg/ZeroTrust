package com.zerotrust.identity_auth_system.controller;

import com.zerotrust.identity_auth_system.dto.LoginRequest;

import com.zerotrust.identity_auth_system.service.LoginService;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public void login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        loginService.authenticateUser(loginRequest, request, response);
    }
}
