package com.library.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Listens to Spring Security's authentication events and writes them to
 * a dedicated audit logger. This satisfies the "basic logging for login
 * and admin operations" requirement without putting logging code inside
 * business logic.
 *
 * Note: only usernames are logged, never passwords or password hashes.
 */
@Component
public class AuthenticationEventLogger {

    private static final Logger securityLog = LoggerFactory.getLogger("SECURITY_AUDIT");

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        securityLog.info("Login SUCCESS username={}", event.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // Do not log the attempted password, only the username/identifier.
        securityLog.warn("Login FAILURE username={} reason={}",
                event.getAuthentication().getName(),
                event.getException().getClass().getSimpleName());
    }
}
