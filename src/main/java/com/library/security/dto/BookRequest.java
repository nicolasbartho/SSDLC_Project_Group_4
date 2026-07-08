package com.library.security.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Input DTO used by librarians to create or update a book.
 * Never bind the JPA entity directly to a web request - always go
 * through a validated DTO to avoid over-posting / mass assignment.
 */
public record BookRequest(

        @NotBlank(message = "Title is required")
        @Size(max = 200)
        String title,

        @NotBlank(message = "Author is required")
        @Size(max = 100)
        String author,

        @NotBlank(message = "Category is required")
        @Size(max = 100)
        String category,

        @Min(value = 0, message = "Total copies cannot be negative")
        int totalCopies
) {
}
