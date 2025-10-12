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
        if (books.isEmpty())
            view.showMessage(Style.styleRed("No books found."));
        else
            books.forEach(b -> view.showMessage(b.toString()));
    }

    public void addBook() {
        try {
            Book book = new Book(
                    view.getInput(Style.styleBlue("Enter book title (0 to go back):\n")),
                    view.getInput(Style.styleBlue("Enter book description (0 to go back):\n")),
                    view.getInput(Style.styleBlue("Enter ISBN code (0 to go back):\n")));
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
            Book book = selectBookByTitle(Style.styleBlue("Enter book title to edit (0 to go back):\n\n"));
            if (book == null)
                return;

            String newTitle = view.getInput(Style.styleOrange("New title (Enter to keep current, 0 to go back): "));
            if (!newTitle.isEmpty())
                book.setTitle(newTitle);

            String newDescription = view.getInput(Style.styleOrange("New description (Enter to keep current, 0 to go back): "));
            if (!newDescription.isEmpty())
                book.setDescription(newDescription);

            String newIsbn = view.getInput(Style.styleOrange("New ISBN (Enter to keep current, 0 to go back): "));
            if (!newIsbn.isEmpty())
                book.setIsbn(newIsbn);

            view.showMessage("Current authors: " + String.join(", ", book.getAuthors()));
            String newAuthorsInput = view
                    .getInput(Style.styleBlue("Enter new author(s) (comma separated, Enter to keep current, 0 to go back): "));
            if (!newAuthorsInput.isEmpty()) {
                bookDao.removeAuthorsFromBook(book.getIdBook());
                handleAuthors(book);
            }

            view.showMessage("Current genres: " + String.join(", ", book.getGenres()));
            String newGenresInput = view
                    .getInput(Style.styleBlue("Enter new genres (comma separated, Enter to keep current, 0 to go back): "));
            if (!newGenresInput.isEmpty()) {
                bookDao.removeGenresFromBook(book.getIdBook());
                handleGenres(book);
            }

            bookDao.update(book);
            view.showMessage(Style.styleGreen("Book updated successfully!\n\n"));

        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    public void deleteBook() {
        try {
            Book book = selectBookByTitle(Style.styleBlue("Enter title to delete, 0 to go back: "));
            if (book == null) return;
            bookDao.delete(book.getIdBook());
            view.showMessage(Style.styleGreen("Book deleted successfully!\n\n"));
        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    public void searchByTitle() {
        try {
            searchAndShow(bookDao::searchByTitle, Style.styleBlue("Enter title to search, 0 to go back: "));
        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    public void searchByAuthor() {
        try {
            searchAndShow(bookDao::searchByAuthor, Style.styleBlue("Enter author name to search, 0 to go back: "));
        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    public void searchByGenre() {
        try {
            searchAndShow(bookDao::searchByGenre, Style.styleBlue("Enter genre to search, 0 to go back: "));
        } catch (BackToMenuException e) {
            view.showMessage(Style.styleYellow("Operation cancelled. Returning to main menu.\n"));
        }
    }

    private void handleAuthors(Book book) throws BackToMenuException {
        String authorsInput = view
                .getInput(Style.styleBlue("Enter author(s) (comma separated, e.g. First Last, First2 Last2), 0 to go back: "));
        if (authorsInput.isEmpty())
            return;
        String[] authorArray = authorsInput.split(",");
        for (String fullName : authorArray) {
            fullName = fullName.trim();
            if (fullName.isEmpty())
                continue;
            String[] parts = fullName.split(" ", 2);
            String firstName = parts[0];
            String lastName = parts.length > 1 ? parts[1] : "";
            Author author = authorDao.getByName(firstName, lastName);
            if (author == null) {
                author = new Author(firstName, lastName);
                authorDao.insert(author);
            }
            bookDao.addAuthorToBook(book.getIdBook(), author.getIdAuthor());
        }
    }

    private void handleGenres(Book book) {
        String genresInput = view.getInput(Style.styleBlue("Enter genres (comma separated), 0 to go back: "));
        if (genresInput.isEmpty())
            return;
        String[] genreArray = genresInput.split(",");
        for (String genreName : genreArray) {
            genreName = genreName.trim();
            if (genreName.isEmpty())
                continue;
            Genre genre = genreDao.getByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
                genreDao.insert(genre);
            }
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

    private void searchAndShow(java.util.function.Function<String, List<Book>> searchFunc, String prompt) {
        String input = view.getInput(prompt);
        List<Book> books = searchFunc.apply(input);
        if (books.isEmpty())
            view.showMessage(Style.styleRed("No results found."));
        else
            books.forEach(b -> view.showMessage(b.toString()));
    }
}
