package com.zerotrust.identity_auth_system.services;

import java.util.Map;
import java.util.UUID;

@Component
public class PasswordManager {

    private final Map<String, String> passwordStore = new ConcurrentHashMap<>();

    // Yeni şifre oluştur
    public String generatePasswordForSession(String sessionId) {
        String password = UUID.randomUUID().toString(); // Rastgele şifre oluştur
        passwordStore.put(sessionId, password);
        return password;
    }

    // Şifre doğrula
    public boolean validatePassword(String sessionId, String password) {
        return password.equals(passwordStore.get(sessionId));
    }

    // Oturum sonlandığında şifreyi temizle
    public void clearPassword(String sessionId) {
        passwordStore.remove(sessionId);
    }
}