package com.library.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Input DTO for user registration.
 *
 * All fields are validated with Bean Validation annotations so that
 * malformed or malicious input is rejected before it reaches the service
 * or persistence layer. The role is deliberately NOT part of this DTO:
 * clients can never self-assign the LIBRARIAN role through registration
 * (this prevents a classic mass-assignment / privilege-escalation bug).
 */
public record RegisterRequest(

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username contains invalid characters")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid address")
        @Size(max = 254)
        String email,

        // Minimum complexity: at least 8 chars, one letter and one digit.
        // Real strength scoring (e.g. zxcvbn) can be added later; this is
        // a baseline so weak/empty passwords are rejected at the boundary.
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "Password must contain at least one letter and one digit"
        )
        String password
) {
}
