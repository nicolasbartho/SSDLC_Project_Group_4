package com.library.security.controller;

import com.library.security.dto.RegisterForm;
import com.library.security.exception.DuplicateResourceException;
import com.library.security.model.Book;
import com.library.security.repository.BookRepository;
import com.library.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Server-rendered (Thymeleaf) pages. Kept deliberately separate from the
 * JSON REST controllers (AuthController, BookController) so the browser
 * flow and the API can evolve independently.
 */
@Controller
@RequiredArgsConstructor
public class ViewController {

    private final BookRepository bookRepository;
    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // The actual login POST is handled by Spring Security's form-login
    // filter (see SecurityConfig) - this GET mapping only renders the form.
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterForm registerForm,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.register(registerForm.toRegisterRequest());
        } catch (DuplicateResourceException e) {
            // Generic field-level error only; no internal detail leaked.
            bindingResult.rejectValue("username", "duplicate", e.getMessage());
            return "register";
        }
        return "redirect:/login?registered";
    }

    @GetMapping("/books")
    public String books(@RequestParam(required = false) String q, Model model) {
        List<Book> books = (q == null || q.isBlank())
                ? bookRepository.findAll()
                : bookRepository.findByTitleContainingIgnoreCase(q.trim());
        model.addAttribute("books", books);
        model.addAttribute("query", q);
        return "books";
    }
}
