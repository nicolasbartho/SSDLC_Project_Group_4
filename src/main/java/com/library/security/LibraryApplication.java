package com.library.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Mini Library Management Web Application.
 *
 * Security note: this class intentionally contains no configuration,
 * credentials or secrets. All sensitive configuration is externalized
 * to environment variables (see application.yml).
 */
@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }
}
