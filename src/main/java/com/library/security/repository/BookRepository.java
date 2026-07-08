package com.library.security.repository;

import com.library.security.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Derived query methods -> Spring Data generates parameterized JPQL,
    // user input is never concatenated into the query string.
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByCategoryContainingIgnoreCase(String category);
}
