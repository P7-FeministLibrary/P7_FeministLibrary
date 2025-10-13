package com.feministlibrary.controller;

import com.feministlibrary.Style;
import com.feministlibrary.model.author.*;
import com.feministlibrary.model.book.*;
import com.feministlibrary.model.genre.*;
import com.feministlibrary.view.*;
import java.util.List;

public class BookController {

    private final BookDAOInterface bookDao;
    private final AuthorDAOInterface authorDao;
    private final GenreDAOInterface genreDao;
    private final BookView view;

    public BookController(BookDAOInterface bookDao, AuthorDAOInterface authorDao,
            GenreDAOInterface genreDao, BookView view) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.view = view;
    }

    public void listBooks() {
        List<Book> books = bookDao.getAll();
        if (books.isEmpty()) {
            view.showMessage(Style.styleRed("No books found."));
        } else {
            for (Book b : books) {
                view.showMessage("ID: " + b.getIdBook()
                        + " | Title: " + b.getTitle()
                        + " | ISBN: " + b.getIsbn()
                        + " | Authors: " + String.join(", ", b.getAuthors())
                        + " | Genres: " + String.join(", ", b.getGenres()));
            }
        }
    }

    public void addBook() {
        try {
            String title, description, isbn;
            do {
                title = view.getInput(Style.styleBlue("Enter book title (0 to go back): ")).trim();
                if (title.equals("0")) throw new BackToMenuException();
                if (title.isEmpty())
                    view.showMessage(Style.styleRed("Title cannot be empty."));
            } while (title.isEmpty());

            do {
                description = view.getInput(Style.styleBlue("Enter book description (0 to go back): ")).trim();
                if (description.equals("0")) throw new BackToMenuException();
                if (description.isEmpty())
                    view.showMessage(Style.styleRed("Description cannot be empty."));
            } while (description.isEmpty());

            do {
                isbn = view.getInput(Style.styleBlue("Enter ISBN code (0 to go back): ")).trim();
                if (isbn.equals("0")) throw new BackToMenuException();
                if (isbn.isEmpty())
                    view.showMessage(Style.styleRed("ISBN cannot be empty."));
                else if (isbn.length() > 20) {
                    view.showMessage(Style.styleRed("ISBN too long (" + isbn.length() + " chars). Max allowed: 20."));
                    isbn = ""; 
                }
                } while (isbn.isEmpty());

            Book book = new Book(title, description, isbn);

            bookDao.insert(book);

            handleAuthors(book);
            handleGenres(book);

            view.showMessage(Style.styleGreen("Book added successfully!\n\n"));
        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Returning to main menu\n"));
        }
    }

    public void editBook() {
        try {
            Book book = selectBookByTitle(Style.styleBlue("Enter book title to edit (0 to go back): "));
            if (book == null)
                return;

            String newTitle = view.getInput(Style.styleOrange("New title (Enter to keep current, 0 to go back): "))
                    .trim();
            if (!newTitle.isEmpty())
                book.setTitle(newTitle);

            String newDescription = view
                    .getInput(Style.styleOrange("New description (Enter to keep current, 0 to go back): ")).trim();
            if (!newDescription.isEmpty())
                book.setDescription(newDescription);

            String newIsbn = view.getInput(Style.styleOrange("New ISBN (Enter to keep current, 0 to go back): "))
                    .trim();
            if (!newIsbn.isEmpty())
                book.setIsbn(newIsbn);

            view.showMessage("Current authors: " + String.join(", ", book.getAuthors()));
            String newAuthorsInput = view
                    .getInput(Style
                            .styleBlue("Enter new author(s) (comma separated, Enter to keep current, 0 to go back): "))
                    .trim();
            if (!newAuthorsInput.isEmpty()) {
                updateAuthors(book, newAuthorsInput);
            }

            view.showMessage("Current genres: " + String.join(", ", book.getGenres()));
            String newGenresInput = view
                    .getInput(
                            Style.styleBlue(
                                    "Enter new genres (comma separated, Enter to keep current, 0 to go back): "))
                    .trim();
            if (!newGenresInput.isEmpty()) {
                updateGenres(book, newGenresInput);
            }

            bookDao.update(book);
            view.showMessage(Style.styleGreen("Book updated successfully!\n\n"));

        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    private void updateAuthors(Book book, String authorsInput) {
        bookDao.removeAuthorsFromBook(book.getIdBook());
        for (String fullName : authorsInput.split(",")) {
            fullName = fullName.trim();
            if (fullName.isEmpty())
                continue;
            String[] parts = fullName.split(" ", 2);
            String firstName = parts[0];
            String lastName = parts.length > 1 ? parts[1].trim() : "";
            Author author = authorDao.getByName(firstName, lastName);
            if (author == null) {
                author = new Author(firstName, lastName);
                authorDao.insert(author);
            }
            book.addAuthor(firstName + (lastName.isEmpty() ? "" : " " + lastName));
            bookDao.addAuthorToBook(book.getIdBook(), author.getIdAuthor());
        }
    }

    private void updateGenres(Book book, String genresInput) {
        bookDao.removeGenresFromBook(book.getIdBook());
        for (String genreName : genresInput.split(",")) {
            genreName = genreName.trim();
            if (genreName.isEmpty())
                continue;
            Genre genre = genreDao.getByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
                genreDao.insert(genre);
            }
            book.addGenre(genreName);
            bookDao.addGenreToBook(book.getIdBook(), genre.getIdGenre());
        }
    }

    public void deleteBook() {
        try {
            Book book = selectBookByTitle(Style.styleBlue("Enter title to delete, 0 to go back: "));
            if (book == null)
                return;
            bookDao.delete(book.getIdBook());
        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    public void searchByTitle() {
        try {
            searchAndShow(bookDao::searchByTitle, Style.styleBlue("Enter title to search, 0 to go back: "), true);
        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    public void searchByAuthor() {
        try {
            searchAndShow(bookDao::searchByAuthor, Style.styleBlue("Enter author name to search, 0 to go back: "),
                    true);
        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    public void searchByGenre() {
        try {
            searchAndShow(bookDao::searchByGenre, Style.styleBlue("Enter genre to search, 0 to go back: "), false);
        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    private void handleAuthors(Book book) throws BackToMenuException {
        String authorsInput;
        do {
            authorsInput = view.getInput(Style.styleBlue("Enter author(s) (comma separated), 0 to go back: ")).trim();
            if (authorsInput.isEmpty())
                view.showMessage(Style.styleRed("Authors cannot be empty."));
        } while (authorsInput.isEmpty());

        for (String fullName : authorsInput.split(",")) {
            fullName = fullName.trim();
            if (fullName.isEmpty())
                throw new BackToMenuException();
            String[] parts = fullName.split(" ", 2);
            String firstName = parts[0];
            String lastName = parts.length > 1 ? parts[1].trim() : "";
            Author author = authorDao.getByName(firstName, lastName);
            if (author == null) {
                author = new Author(firstName, lastName);
                authorDao.insert(author);
            }
            book.addAuthor(firstName + (lastName.isEmpty() ? "" : " " + lastName));
            bookDao.addAuthorToBook(book.getIdBook(), author.getIdAuthor());
        }
    }

    private void handleGenres(Book book) throws BackToMenuException {
        String genresInput;
        do {
            genresInput = view.getInput(Style.styleBlue("Enter genres (comma separated), 0 to go back: ")).trim();
            if (genresInput.isEmpty())
                view.showMessage(Style.styleRed("Genres cannot be empty."));
        } while (genresInput.isEmpty());

        for (String genreName : genresInput.split(",")) {
            genreName = genreName.trim();
            if (genreName.isEmpty())
                throw new BackToMenuException();
            Genre genre = genreDao.getByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
                genreDao.insert(genre);
            }
            book.addGenre(genreName);
            bookDao.addGenreToBook(book.getIdBook(), genre.getIdGenre());
        }
    }

    private Book selectBookByTitle(String prompt) throws BackToMenuException {
        String title = view.getInput(prompt);
        List<Book> books = bookDao.searchByTitle(title);
        if (books.isEmpty()) {
            view.showMessage(Style.styleRed("No book found."));
            return null;
        }
        if (books.size() == 1)
            return books.get(0);

        books.forEach(b -> view.showMessage(b.getIdBook() + " | " + b.getTitle()));
        int id = Integer.parseInt(view.getInput(Style.styleBlue("Enter the ID of the book (0 to go back): ")));
        return bookDao.getById(id);
    }

    private void searchAndShow(java.util.function.Function<String, List<Book>> searchFunc, String prompt,
            boolean showDescription) {
        String input = view.getInput(prompt);
        List<Book> books = searchFunc.apply(input);
        if (books.isEmpty())
            view.showMessage(Style.styleRed("No results found."));
        else
            for (Book b : books) {
                if (showDescription)
                    view.showMessage(b.toString());
                else
                    view.showMessage("ID: " + b.getIdBook()
                            + " | Title: " + b.getTitle()
                            + " | ISBN: " + b.getIsbn()
                            + " | Authors: " + String.join(", ", b.getAuthors())
                            + " | Genres: " + String.join(", ", b.getGenres()));
            }
    }
}
