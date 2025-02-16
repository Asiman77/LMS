package com.example.library.Service;

import com.example.library.Model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.library.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Get all authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Get author by ID
    public Author getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return authorRepository.findById(id).get();
    }

    // Create a new author
    public void createAuthor(Author author) {
        authorRepository.save(author);
    }

    // Update an existing author
    public void updateAuthor(Long id, Author author) {
        author.setId(id);
        authorRepository.save(author);
    }

    // Delete an author
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
