package com.example.library.Service;

import com.example.library.Model.Author;
import com.example.library.Model.Book;
import com.example.library.Model.Category;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepository.findAll();
    }


    public Book getBookById(Long id) {
        logger.info("Fetching book by ID: {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }

    public Book createBook(Book book) {
        logger.info("Creating a new book: {}", book.getTitle());

        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + book.getAuthor().getId()));
        book.setAuthor(author);

        Category category = categoryRepository.findById(book.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + book.getCategory().getId()));
        book.setCategory(category);

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        logger.info("Updating book ID: {}", id);
        return bookRepository.findById(id).map(existingBook -> {
            existingBook.setTitle(book.getTitle());
            existingBook.setIsbn(book.getIsbn());

            if (book.getAuthor() != null && book.getAuthor().getId() != null) {
                existingBook.setAuthor(authorRepository.findById(book.getAuthor().getId())
                        .orElseThrow(() -> new RuntimeException("Author not found with ID: " + book.getAuthor().getId())));
            }

            if (book.getCategory() != null && book.getCategory().getId() != null) {
                existingBook.setCategory(categoryRepository.findById(book.getCategory().getId())
                        .orElseThrow(() -> new RuntimeException("Category not found with ID: " + book.getCategory().getId())));
            }

            existingBook.setStock(book.getStock());
            existingBook.setDescription(book.getDescription());
            existingBook.setImage(book.getImage());

            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }

    public void deleteBook(Long id) {
        logger.info("Deleting book ID: {}", id);

        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with ID: " + id);
        }

        bookRepository.deleteById(id);
    }

    public List<Book> getBooksByCategory(Long categoryId) {
        logger.info("Fetching books by category ID: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }
        return bookRepository.findByCategoryId(categoryId);
    }

    public List<Book> getBooksByAuthor(Long authorId) {
        logger.info("Fetching books by author ID: {}", authorId);
        if (!authorRepository.existsById(authorId)) {
            throw new RuntimeException("Author not found with ID: " + authorId);
        }
        return bookRepository.findByAuthorId(authorId);
    }
}
