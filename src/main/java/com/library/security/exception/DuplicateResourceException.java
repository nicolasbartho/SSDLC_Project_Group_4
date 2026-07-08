package com.library.security.exception;

/** Thrown when a unique-constraint business rule is violated (e.g. username taken). */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
