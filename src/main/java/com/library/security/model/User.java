package com.library.security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an application user (either USER or LIBRARIAN role).
 *
 * Security notes:
 * - {@code passwordHash} stores a BCrypt hash, never a plaintext password.
 *   The field name deliberately avoids "password" alone to make the intent
 *   explicit and to discourage accidentally logging/serializing raw passwords.
 * - {@code username}/{@code email} are unique to prevent account confusion
 *   and duplicate-registration abuse.
 */
@Entity
@Table(name = "app_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 254)
    private String email;

    /** BCrypt hash only. Never expose this field via any DTO/JSON response. */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false)
    private boolean enabled = true;
}
