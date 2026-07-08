package com.library.security.controller;

import com.library.security.dto.RegisterRequest;
import com.library.security.model.User;
import com.library.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        User created = userService.register(request);
        // Never return the password hash or any credential material.
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id", created.getId(),
                "username", created.getUsername(),
                "role", created.getRole()
        ));
    }

    // Actual login is handled by Spring Security's form-login filter
    // (see SecurityConfig) rather than a custom endpoint, so that
    // authentication logic - rate limiting, lockout, session handling -
    // stays inside the well-reviewed framework code path instead of
    // being reimplemented by hand.
}
