package com.zerotrust.identity_auth_system.config;


import com.zerotrust.identity_auth_system.filter.PasswordValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final PasswordValidationFilter passwordValidationFilter;

    public SecurityConfig(PasswordValidationFilter passwordValidationFilter) {
        this.passwordValidationFilter = passwordValidationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .addFilterBefore(passwordValidationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
