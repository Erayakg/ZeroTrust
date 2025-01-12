package com.zerotrust.identity_auth_system.filter;

import com.zerotrust.identity_auth_system.services.PasswordManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PasswordValidationFilter extends OncePerRequestFilter {

    private final PasswordManager passwordManager;

    public PasswordValidationFilter(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String username = request.getHeader("Username");
        String password = request.getHeader("Password");

        if (username == null || password == null || !passwordManager.validatePassword(username, password)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid password");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
