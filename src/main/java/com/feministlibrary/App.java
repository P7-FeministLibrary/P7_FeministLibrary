package com.feministlibrary;

import com.feministlibrary.config.DBManager;
import java.sql.Connection;
import java.sql.SQLException;

import com.feministlibrary.controller.*;
import com.feministlibrary.model.author.*;
import com.feministlibrary.model.book.*;
import com.feministlibrary.model.genre.*;
import com.feministlibrary.view.*;

public class App {
    public static void main(String[] args) {
        try (Connection conn = DBManager.getConnection()) {
            System.out.println(Style.styleGreen("Connected to The Matilda Library database."));

            BookView bookView = new BookView();
            AuthorView authorView = new AuthorView();
            GenreView genreView = new GenreView();

            BookDAOInterface bookDao = new BookDAOImplementation();
            AuthorDAOInterface authorDao = new AuthorDAOImplementation();
            GenreDAOInterface genreDao = new GenreDAOImplementation();

            BookController bookController = new BookController(bookDao, authorDao, genreDao, bookView);
            AuthorController authorController = new AuthorController(authorDao, authorView);
            GenreController genreController = new GenreController(genreDao, genreView);

            boolean running = true;
            while (running) {
                String opt = bookView.showMenu();
                switch (opt) {
                    case "1" -> bookController.listBooks();
                    case "2" -> bookController.addBook();
                    case "3" -> bookController.editBook();
                    case "4" -> bookController.deleteBook();
                    case "5" -> bookController.searchByTitle();
                    case "6" -> bookController.searchByAuthor();
                    case "7" -> bookController.searchByGenre();
                    case "8" -> authorController.listAuthors();
                    case "9" -> authorController.addAuthor();
                    case "10" -> authorController.editAuthor();
                    case "12" -> genreController.listGenres();
                    case "13" -> genreController.addGenre();
                    case "14" -> genreController.editGenre();
                    case "0" -> {
                        running = false;
                        System.out.println(Style.styleGreen("Disconnected from database."));
                    }
                    default -> bookView.showMessage(Style.styleRed("Invalid option, try again."));
                }
            }
            bookView.close();
        } catch (SQLException e) {
            System.out.println(Style.styleRed("Connection failed:\n" + e.getMessage()));
        }
    }
}
