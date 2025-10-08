package com.feministlibrary;

import com.feministlibrary.controller.BookController;
import com.feministlibrary.model.author.AuthorDAOImplementation;
import com.feministlibrary.model.book.BookDAOImplementation;
import com.feministlibrary.model.genre.GenreDAOImplementation;
import com.feministlibrary.view.BookView;

public class App {
    public static void main(String[] args) {
        BookView view = new BookView();
        BookController controller = new BookController(
                new BookDAOImplementation(),
                new AuthorDAOImplementation(),
                new GenreDAOImplementation(),
                view
        );

        boolean running = true;
        while (running) {
            String opt = view.showMenu();
            switch (opt) {
                case "1" -> controller.listBooks();
                case "2" -> controller.addBook();
                case "3" -> controller.editBook();
                case "4" -> controller.deleteBook();
                case "5" -> controller.searchByTitle();
                case "6" -> controller.searchByAuthor();
                case "7" -> controller.searchByGenre();
                case "0" -> {
                    running = false;
                    view.showMessage("Exiting the library system. Goodbye!");
                }
                default -> view.showMessage("Invalid option, please try again.");
            }
        }
        view.close();
    }
}
