package com.feministlibrary.controller;

import java.util.List;

import com.feministlibrary.Style;
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
        if (authors.isEmpty()) view.showMessage(Style.styleRed("No authors found."));
        else authors.forEach(a -> view.showMessage(a.toString()));
    }

    public void addAuthor() {
        String[] parts = view.getAuthorFullName().split(" ", 2);
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";

        Author author = authorDao.getByName(firstName, lastName);
        if (author == null) {
            authorDao.insert(new Author(firstName, lastName));
            view.showMessage(Style.styleGreen("Author added successfully!"));
        } else {
            view.showMessage(Style.styleRed("Author already exists."));
        }
    }

    public void editAuthor() {
        String[] parts = view.getAuthorFullName().split(" ", 2);
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";

        Author author = authorDao.getByName(firstName, lastName);
        if (author == null) {
            view.showMessage(Style.styleRed("Author not found."));
            return;
        }

        String[] newParts = view.getAuthorFullName().split(" ", 2);
        author.setName(newParts[0]);
        author.setLastName(newParts.length > 1 ? newParts[1] : "");
        authorDao.update(author);
        view.showMessage(Style.styleGreen("Author updated successfully!"));
    }
}
