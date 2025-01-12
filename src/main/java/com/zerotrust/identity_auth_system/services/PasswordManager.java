package com.zerotrust.identity_auth_system.services;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PasswordManager {

    private final Map<String, String> passwordStore = new ConcurrentHashMap<>();

    public String generatePasswordForSession(String sessionId) {
        String password = UUID.randomUUID().toString();
        passwordStore.put(sessionId, password);
        System.out.printf("Generated password %s for session %s%n", password, sessionId);
        return password;
    }

    public boolean validatePassword(String sessionId, String password) {
          return password.equals(passwordStore.get(sessionId));
    }

    public void clearPassword(String sessionId) {
        passwordStore.remove(sessionId);
    }
}