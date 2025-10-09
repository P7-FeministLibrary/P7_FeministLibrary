package com.feministlibrary.controller;

import java.util.List;
import com.feministlibrary.model.author.Author;
import com.feministlibrary.model.author.AuthorDAOInterface;
import com.feministlibrary.view.AuthorView;

public class AuthorController {

    private final AuthorDAOInterface authorDao;
    private final AuthorView view;

    public AuthorController(AuthorDAOInterface authorDao, AuthorView view) {
        this.authorDao = authorDao;
        this.view = view;
    }

    public void listAuthors() {
        List<Author> authors = authorDao.getAll();
        if (authors.isEmpty()) {
            view.showMessage("No authors found.");
        } else {
            authors.forEach(a -> view.showMessage(a.toString()));
        }
    }

    public void addAuthor() {
        String fullName = view.getAuthorFullName();
        String[] parts = fullName.split(" ", 2);
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";

        Author author = authorDao.getByName(firstName, lastName);
        if (author == null) {
            author = new Author(firstName, lastName);
            authorDao.insert(author);
            view.showMessage("Author added successfully!");
        } else {
            view.showMessage("Author already exists.");
        }
    }

    public void editAuthor() {
        String fullName = view.getAuthorFullName();
        String[] parts = fullName.split(" ", 2);
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";

        Author author = authorDao.getByName(firstName, lastName);
        if (author == null) {
            view.showMessage("Author not found.");
            return;
        }

        String newFullName = view.getAuthorFullName();
        String[] newParts = newFullName.split(" ", 2);
        String updatedFirst = newParts[0];
        String updatedLast = newParts.length > 1 ? newParts[1] : "";

        author.setName(updatedFirst);
        author.setLastName(updatedLast);
        authorDao.update(author);
        view.showMessage("Author updated successfully!");
    }

}
