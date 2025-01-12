package com.zerotrust.identity_auth_system.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordManager passwordManager;

    public CustomAuthenticationSuccessHandler(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String sessionId = request.getSession().getId();
        String generatedPassword = passwordManager.generatePasswordForSession(sessionId);

        response.getWriter().write("Generated Password for Session: " + generatedPassword);
    }
}