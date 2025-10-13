package com.feministlibrary.model.author;

import java.util.List;

public interface AuthorDAOInterface {
    void insert(Author author);
    void update(Author author);
    Author getById(int idAuthor);
    List<Author> getAll();
    List<Author> searchByNameOrLastName(String nameOrLastName);
    Author getByName(String firstName, String lastName);
}
