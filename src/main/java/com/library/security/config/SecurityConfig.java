package com.library.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.http.HttpStatus;

/**
 * Central security configuration.
 *
 * Key decisions:
 * - Passwords are hashed with BCrypt (adaptive, salted) - never stored or
 *   compared in plaintext.
 * - Role-based access control (RBAC) is enforced both at the URL level
 *   (this class) and at the method level (@PreAuthorize, enabled via
 *   @EnableMethodSecurity) so that controllers/services are protected
 *   even if a route mapping is later added carelessly.
 * - CSRF protection is enabled by default (Spring Security default) for
 *   the browser-facing (Thymeleaf) endpoints.
 * - Sessions are stateless-friendly but session fixation protection is
 *   kept on for the form-login flow.
 * - Security headers (HSTS, no-sniff, referrer-policy, frame-options)
 *   are enabled to reduce common web attack surface.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Strength 12 is a reasonable balance of security vs. login latency.
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/", "/register", "/login", "/css/**", "/js/**", "/error").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                // Read-only catalogue browsing available to any authenticated user
                .requestMatchers("/books", "/books/search").authenticated()
                // Librarian/admin-only management endpoints
                .requestMatchers("/admin/**", "/api/admin/**").hasRole("LIBRARIAN")
                .requestMatchers("/api/books/*/borrow-requests/*/approve",
                                  "/api/books/*/borrow-requests/*/reject").hasRole("LIBRARIAN")
                // Everything else requires authentication by default (fail closed)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/books", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation(fixation -> fixation.migrateSession())
                .maximumSessions(1)
            )
            .exceptionHandling(ex -> ex
                .defaultAuthenticationEntryPointFor(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        request -> request.getRequestURI().startsWith("/api/")
                )
            )
            .headers(headers -> headers
                .contentTypeOptions(withDefaults -> {})
                .frameOptions(frame -> frame.deny())
                .httpStrictTransportSecurity(hsts -> hsts
                        .includeSubDomains(true)
                        .maxAgeInSeconds(31536000)
                )
                .referrerPolicy(referrer -> referrer
                        .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                )
            );
            // Note: CSRF protection is left ENABLED (Spring Security default).
            // If a pure stateless JSON API is added later, prefer token-based
            // auth (e.g. JWT) over disabling CSRF wholesale.

        return http.build();
    }
}
