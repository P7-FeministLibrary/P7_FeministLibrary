package com.feministlibrary.model.book;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int idBook;
    private String title;
    private String description;
    private String isbn;
    private List<String> authors = new ArrayList<>();
    private List<String> genres = new ArrayList<>();

    public Book() {}
    public Book(String title, String description, String isbn) {
        this.title = title; this.description = description; this.isbn = isbn;
    }
    public Book(int idBook, String title, String description, String isbn) {
        this.idBook = idBook; this.title = title; this.description = description; this.isbn = isbn;
    }

    public int getIdBook() { return idBook; }
    public void setIdBook(int idBook) { this.idBook = idBook; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public List<String> getAuthors() { return authors; }
    public void addAuthor(String author) { if (!authors.contains(author)) authors.add(author); }
    public List<String> getGenres() { return genres; }
    public void addGenre(String genre) { if (!genres.contains(genre)) genres.add(genre); }

    @Override
    public String toString() {
        return "[" + idBook + "] Title: " + title + " | ISBN: " + isbn +
                (description != null && !description.isEmpty() ? " | Description: " + description : "") +
                (!authors.isEmpty() ? " | Authors: [" + String.join(", ", authors) + "]" : "") +
                (!genres.isEmpty() ? " | Genres: [" + String.join(", ", genres) + "]" : "");
    }
}
