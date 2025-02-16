package com.example.library.controller;

import com.example.library.Model.Book;
import com.example.library.Model.Author;
import com.example.library.Model.Category;
import com.example.library.Service.BookService;
import com.example.library.Service.AuthorService;
import com.example.library.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    // ✅ List all books
    @GetMapping
    public String listBooks(Model model) {
        logger.info("Fetching all books");
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/list";  // Thymeleaf template: books/list.html
    }

    // ✅ Show book creation form (Thymeleaf)
    @GetMapping("/create")
    public String createBookForm(Model model) {
        logger.info("Displaying book creation form");
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());  // Fetch authors for dropdown
        model.addAttribute("categories", categoryService.getAllCategories());  // Fetch categories for dropdown
        return "books/create";  // Thymeleaf template: books/create.html
    }

    // ✅ Handle book creation form submission
    @PostMapping("/create")
    public String createBook(@ModelAttribute Book book) {
        logger.info("Creating a new book: {}", book.getTitle());

        try {
            bookService.createBook(book);
            return "redirect:/books";
        } catch (RuntimeException e) {
            logger.error("Error creating book: {}", e.getMessage());
            return "redirect:/books/create?error=true";
        }
    }

    // ✅ Show book update form
    @GetMapping("/update/{id}")
    public String updateBookForm(@PathVariable Long id, Model model) {
        logger.info("Displaying update form for book ID: {}", id);
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());  // Fetch authors for dropdown
        model.addAttribute("categories", categoryService.getAllCategories());  // Fetch categories for dropdown
        return "books/update";  // Thymeleaf template: books/update.html
    }

    // ✅ Handle book update form submission
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        logger.info("Updating book ID: {}", id);

        try {
            bookService.updateBook(id, book);
            return "redirect:/books";
        } catch (RuntimeException e) {
            logger.error("Error updating book: {}", e.getMessage());
            return "redirect:/books/update/" + id + "?error=true";
        }
    }

    // ✅ Delete a book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        logger.info("Deleting book ID: {}", id);

        try {
            bookService.deleteBook(id);
            return "redirect:/books";
        } catch (RuntimeException e) {
            logger.error("Error deleting book: {}", e.getMessage());
            return "redirect:/books?error=true";
        }
    }

    // ✅ View details of a single book
    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        logger.info("Viewing details for book ID: {}", id);
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "books/view";  // Thymeleaf template: books/view.html
    }
}
