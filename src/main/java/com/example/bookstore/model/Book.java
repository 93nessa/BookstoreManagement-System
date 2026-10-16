package com.example.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import java.util.List;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double price;

    @Column(unique = true)
    private String isbn;

    @Column(length = 2000)
    private String description;
    
    private Integer publicationYear;
    private String edition;
    private String language;

    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany 
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;


    @ManyToMany
    @JoinTable (
        name = "book_genre",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable (
        name = "book_format",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    private List<Format> formats;


    //Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getQuantity() {
        return stockQuantity;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public String getEdition() {
        return edition;
    }

    public String getLanguage() {
        return language;
    }

    public Publisher getPublisher() {
        return publisher;
    }
    
    public List<Format> getFormats() {
        return formats;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }
}
