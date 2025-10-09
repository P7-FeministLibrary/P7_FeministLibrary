package com.feministlibrary;

import com.feministlibrary.controller.BookController;
import com.feministlibrary.controller.AuthorController;
import com.feministlibrary.controller.GenreController;
import com.feministlibrary.model.author.AuthorDAOImplementation;
import com.feministlibrary.model.author.AuthorDAOInterface;
import com.feministlibrary.model.book.BookDAOImplementation;
import com.feministlibrary.model.book.BookDAOInterface;
import com.feministlibrary.model.genre.GenreDAOImplementation;
import com.feministlibrary.model.genre.GenreDAOInterface;
import com.feministlibrary.view.BookView;
import com.feministlibrary.view.AuthorView;
import com.feministlibrary.view.GenreView;

public class App {
    public static void main(String[] args) {
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
                case "0" -> running = false;
                default -> bookView.showMessage("Invalid option, please try again.");
            }
        }
        bookView.close();
    }
}

