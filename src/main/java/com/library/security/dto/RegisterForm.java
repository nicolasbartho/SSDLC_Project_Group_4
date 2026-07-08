package com.library.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Form-backing bean for the /register Thymeleaf view.
 * Same validation rules as {@link RegisterRequest} (used by the JSON API);
 * kept as a separate mutable class because Thymeleaf's th:field binding
 * needs getters/setters, which Java records don't provide.
 */
@Getter
@Setter
public class RegisterForm {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username contains invalid characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid address")
    @Size(max = 254)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "Password must contain at least one letter and one digit"
    )
    private String password;

    public RegisterRequest toRegisterRequest() {
        return new RegisterRequest(username, email, password);
    }
}
