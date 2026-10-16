package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    // Get book by ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    // Create a new book
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    // Update an existing book
    public Book updateBook(Long id, Book updatedBook) {
        Book existing = bookRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setTitle(updatedBook.getTitle());
        existing.setDescription(updatedBook.getDescription());
        existing.setPublicationYear(updatedBook.getPublicationYear());
        existing.setIsbn(updatedBook.getIsbn());
        existing.setPrice(updatedBook.getPrice());
        existing.setEdition(updatedBook.getEdition());
        existing.setLanguage(updatedBook.getLanguage());
        existing.setStockQuantity(updatedBook.getQuantity());

        // Update relationships
        existing.setPublisher(updatedBook.getPublisher());
        existing.setAuthors(updatedBook.getAuthors());
        existing.setGenres(updatedBook.getGenres());
        existing.setFormats(updatedBook.getFormats());

        return bookRepository.save(existing);
    }
    // Delete a book
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Search methods

    public List<Book> searchByPriceRange(Double minPrice, Double maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthors_NameContainingIgnoreCase(author);
    }

    public List<Book> searchByGenre(String genre) {
        return bookRepository.findByGenres_NameContainingIgnoreCase(genre);
    }

    public List<Book> searchByISBN(String isbn) {
        return bookRepository.findByIsbnContainingIgnoreCase(isbn);
    }
    // Multi-filter search method (title + author + genre + format) + pagination and sorting
    public Page<Book> searchBooks(String title, String author, String genre, String format, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.searchBooks(title, author, genre, format, pageable);
    }
}
