package com.library.security.controller;

import com.library.security.dto.BookRequest;
import com.library.security.exception.ResourceNotFoundException;
import com.library.security.model.Book;
import com.library.security.repository.BookRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Book catalogue endpoints.
 *
 * RBAC is enforced in two places (defence in depth):
 *  1. URL-level rules in SecurityConfig (/admin/**, /api/admin/**).
 *  2. Method-level @PreAuthorize annotations here, so the mutating
 *     operations remain protected even if the URL pattern changes later.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class BookController {

    private static final Logger securityLog = LoggerFactory.getLogger("SECURITY_AUDIT");

    private final BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    // Search is a common injection target; using derived repository
    // methods keeps this fully parameterized (no string concatenation).
    @GetMapping("/books/search")
    public List<Book> search(@RequestParam(required = false) @Size(max = 200) String title,
                              @RequestParam(required = false) @Size(max = 100) String category) {
        if (title != null && !title.isBlank()) {
            return bookRepository.findByTitleContainingIgnoreCase(title.trim());
        }
        if (category != null && !category.isBlank()) {
            return bookRepository.findByCategoryContainingIgnoreCase(category.trim());
        }
        return bookRepository.findAll();
    }

    @PostMapping("/admin/books")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public Book createBook(@Valid @RequestBody BookRequest request, Authentication auth) {
        Book book = Book.builder()
                .title(request.title())
                .author(request.author())
                .category(request.category())
                .totalCopies(request.totalCopies())
                .availableCopies(request.totalCopies())
                .build();
        Book saved = bookRepository.save(book);
        securityLog.info("Book created id={} by librarian={}", saved.getId(), auth.getName());
        return saved;
    }

    @PutMapping("/admin/books/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest request, Authentication auth) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setTotalCopies(request.totalCopies());
        Book saved = bookRepository.save(book);
        securityLog.info("Book updated id={} by librarian={}", saved.getId(), auth.getName());
        return saved;
    }

    @DeleteMapping("/admin/books/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public void deleteBook(@PathVariable Long id, Authentication auth) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
        securityLog.info("Book deleted id={} by librarian={}", id, auth.getName());
    }
}
