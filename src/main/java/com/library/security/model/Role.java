package com.library.security.model;

/**
 * Application roles used for role-based access control (RBAC).
 * Kept as a closed enum (rather than free-text strings) to avoid
 * privilege-escalation via arbitrary role values.
 */
public enum Role {
    USER,
    LIBRARIAN
}
