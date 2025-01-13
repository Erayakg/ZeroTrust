package com.zerotrust.identity_auth_system.filter;

import com.zerotrust.identity_auth_system.services.PasswordManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PasswordValidationFilter extends OncePerRequestFilter {

    private final PasswordManager passwordManager;
    @Autowired
    public PasswordValidationFilter(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication == null || !authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }


        String sessionId = request.getSession().getId();
        String password = request.getHeader("Password");

        System.out.printf("Session ID: %s, Received Password: %s%n", sessionId, password);

        if (password == null || !passwordManager.validatePassword(sessionId, password)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid password");
            return;
        }

        filterChain.doFilter(request, response);
    }


}
