package com.zerotrust.identity_auth_system.listener;

import com.zerotrust.identity_auth_system.services.PasswordManager;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;

@Component
public class SessionEventListener implements HttpSessionListener {

    private final PasswordManager passwordManager;

    public SessionEventListener(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String sessionId = event.getSession().getId();
        passwordManager.clearPassword(sessionId);
    }
}
