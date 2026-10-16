package com.example.bookstore.controller;

import com.example.bookstore.model.Book;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.bookstore.service.BookService;
import org.springframework.data.domain.Page;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //Get all books
    @GetMapping
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }
    //Get book by ID
    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
    //Create a new book
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }
    //Update an existing book
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        return bookService.updateBook(id, book);
    }
    //Delete a book
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
    //Search by title
    @GetMapping("/search/title/{title}")
    public List<Book> searchByTitle(@PathVariable String title) {
        return bookService.searchByTitle(title);
    }
    //Search by author
    @GetMapping("/search/author/{author}")
    public List<Book> searchByAuthor(@PathVariable String author) {
        return bookService.searchByAuthor(author);
    }
    //Search by genre
    @GetMapping("/search/genre/{genre}")
    public List<Book> searchByGenre(@PathVariable String genre) {
        return bookService.searchByGenre(genre);
    }
    //Search by ISBN
    @GetMapping("/search/isbn/{isbn}")
    public List<Book> searchByISBN(@PathVariable String isbn) {
        return bookService.searchByISBN(isbn);
    }

    @GetMapping("/search")
    public Page<Book> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String format,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) 
            {
        return bookService.searchBooks(title, author, genre, format, page, size, sortBy, sortDir);
    }
    



    
}

