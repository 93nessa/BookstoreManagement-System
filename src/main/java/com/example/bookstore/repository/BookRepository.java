package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
    //Basic search methods
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthors_NameContainingIgnoreCase(String name);
    List<Book> findByGenres_NameContainingIgnoreCase(String name);
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Book> findByIsbnContainingIgnoreCase(String isbn);

    //multi-filter search method (title + author + genre + format)
    @Query("""
        SELECT DISTINCT b FROM Book b 
        LEFT JOIN b.authors a 
        LEFT JOIN b.genres g 
        LEFT JOIN b.formats f 
            WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
            AND (:author IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :author, '%')))
            AND (:genre IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :genre, '%'))) 
            AND (:format IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :format, '%')))""")

    Page<Book> searchBooks(
        @Param("title") String title,
        @Param("author") String author,
        @Param("genre") String genre,
        @Param("format") String format,
        Pageable pageable
    ) ;
}

