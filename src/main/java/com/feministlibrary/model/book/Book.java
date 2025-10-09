package com.feministlibrary.model.book;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int idBook;
    private String title;
    private String description;
    private String isbn;

    public Book() {
    }

    public Book(String title, String description, String isbn) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
    }

    public Book(int idBook, String title, String description, String isbn) {
        this.idBook = idBook;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
    }

    public int getIdBook() {
        return this.idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;

    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    private List<String> genres = new ArrayList<>();

    public void addGenre(String genre) {
        if (!genres.contains(genre)) {
            genres.add(genre);
        }
    }

    public List<String> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "[" + idBook + "] " + title + " (ISBN: " + isbn + ") - Genres: " + String.join(", ", genres);
    }

}