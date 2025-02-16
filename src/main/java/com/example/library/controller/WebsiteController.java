package com.example.library.controller;

import com.example.library.Model.Book;
import com.example.library.Service.BookService;
import com.example.library.Service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/website")
public class WebsiteController {

    private final BookService bookService;
    private final AuthorService authorService;

    public WebsiteController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String home() {
        return "admin/home";
    }

    @GetMapping("/books")
    public String books(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "website/books";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "website/authors";
    }

    @GetMapping("/contact")
    public String contact() {
        return "website/contact";
    }
}
