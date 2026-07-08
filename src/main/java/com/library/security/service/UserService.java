package com.library.security.service;

import com.library.security.dto.RegisterRequest;
import com.library.security.exception.DuplicateResourceException;
import com.library.security.model.Role;
import com.library.security.model.User;
import com.library.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    // Dedicated logger name so security-relevant events can be routed to
    // their own log file / SIEM pipeline (see logback-spring.xml).
    private static final Logger securityLog = LoggerFactory.getLogger("SECURITY_AUDIT");

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("Username already in use");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already in use");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                // Password is hashed here and only here; the plaintext
                // value never touches the database or the logs.
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(Role.USER) // Self-registration can only ever create USER accounts.
                .enabled(true)
                .build();

        User saved = userRepository.save(user);

        // Log the event, never the password (plaintext or hash).
        securityLog.info("New user registered: username={}", saved.getUsername());

        return saved;
    }
}
