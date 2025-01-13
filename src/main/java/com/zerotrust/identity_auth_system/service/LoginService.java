package com.zerotrust.identity_auth_system.service;

import com.zerotrust.identity_auth_system.dto.LoginRequest;
import com.zerotrust.identity_auth_system.services.CustomAuthenticationSuccessHandler;
import com.zerotrust.identity_auth_system.services.PasswordManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final PasswordManager passwordManager;
    private final UserDetailsService userDetailsService;

    public LoginService(AuthenticationManager authenticationManager,
                        CustomAuthenticationSuccessHandler authenticationSuccessHandler,
                        PasswordManager passwordManager,
                        UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.passwordManager = passwordManager;
        this.userDetailsService = userDetailsService;
    }

    public void authenticateUser(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.username());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password());


        authenticationManager.authenticate(authentication);


        authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);


        String sessionId = request.getSession().getId();
        String generatedPassword = passwordManager.generatePasswordForSession(sessionId);


        response.getWriter().write(" Successfully logged in. Session generated password: " + generatedPassword);
    }
}

