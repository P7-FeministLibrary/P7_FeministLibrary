package com.feministlibrary.model.book;

import java.util.List;

public interface BookDAOInterface {

    void insert(Book book);
    void update(Book book);
    void delete(int idBook);
    Book getById(int idBook);

    List<Book> getAll();
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String authorName); 
    List<Book> searchByGenre(String genreName);
    
    void addAuthorToBook(int idBook, int idAuthor);
    void updateAuthor(int idAuthor, String newFirstName, String newLastName);

}
