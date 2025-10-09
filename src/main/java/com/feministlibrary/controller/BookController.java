package com.feministlibrary.controller;

import java.util.List;
import com.feministlibrary.model.author.Author;
import com.feministlibrary.model.author.AuthorDAOInterface;
import com.feministlibrary.model.book.Book;
import com.feministlibrary.model.book.BookDAOInterface;
import com.feministlibrary.model.genre.Genre;
import com.feministlibrary.model.genre.GenreDAOInterface;
import com.feministlibrary.view.BookView;

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
            view.showMessage("No books found.");
        } else {
            books.forEach(b -> view.showMessage(b.toString()));
        }
    }

    public void addBook() {
        String title = view.getInput("Enter book title: ");
        String description = view.getInput("Enter book description: ");
        String isbn = view.getInput("Enter ISBN code: ");

        String authorsInput = view.getInput("Enter author(s) (comma separated, e.g. First Last, First2 Last2): ");
        String[] authorArray = authorsInput.split(",");
        for (int i = 0; i < authorArray.length; i++) authorArray[i] = authorArray[i].trim();

        Book book = new Book(title, description, isbn);
        bookDao.insert(book);

        for (String fullName : authorArray) {
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

        String genresInput = view.getInput("Enter genres (comma separated): ");
        String[] genreArray = genresInput.split(",");
        for (String genreName : genreArray) {
            genreName = genreName.trim();
            Genre genre = genreDao.getByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
                genreDao.insert(genre);
            }
            bookDao.addGenreToBook(book.getIdBook(), genre.getIdGenre());
        }

        view.showMessage("Book added successfully!");
    }

    public void editBook() {
        String title = view.getInput("Enter book title to edit: ");
        List<Book> books = bookDao.searchByTitle(title);
        if (books.isEmpty()) {
            view.showMessage("No book found.");
            return;
        }

        Book book;
        if (books.size() == 1) {
            book = books.get(0);
        } else {
            books.forEach(b -> view.showMessage(b.getIdBook() + " | " + b.getTitle()));
            int id = Integer.parseInt(view.getInput("Enter the ID of the book to edit: "));
            book = bookDao.getById(id);
            if (book == null) {
                view.showMessage("Invalid selection.");
                return;
            }
        }

        String newTitle = view.getInput("New title (Enter to keep current): ");
        if (!newTitle.isEmpty()) book.setTitle(newTitle);

        String newDescription = view.getInput("New description (Enter to keep current): ");
        if (!newDescription.isEmpty()) book.setDescription(newDescription);

        String newIsbn = view.getInput("New ISBN (Enter to keep current): ");
        if (!newIsbn.isEmpty()) book.setIsbn(newIsbn);

        view.showMessage("Current authors: " + String.join(", ", book.getAuthors()));
        String authorsInput = view.getInput("Enter new author(s) (comma separated, Enter to keep current): ");
        if (!authorsInput.isEmpty()) {
            bookDao.removeAuthorsFromBook(book.getIdBook());
            String[] authorArray = authorsInput.split(",");
            for (int i = 0; i < authorArray.length; i++) authorArray[i] = authorArray[i].trim();

            for (String fullName : authorArray) {
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

        view.showMessage("Current genres: " + String.join(", ", book.getGenres()));
        String genresInput = view.getInput("Enter new genres (comma separated, Enter to keep current): ");
        if (!genresInput.isEmpty()) {
            bookDao.removeGenresFromBook(book.getIdBook());
            String[] genreArray = genresInput.split(",");
            for (String genreName : genreArray) {
                genreName = genreName.trim();
                Genre genre = genreDao.getByName(genreName);
                if (genre == null) {
                    genre = new Genre(genreName);
                    genreDao.insert(genre);
                }
                bookDao.addGenreToBook(book.getIdBook(), genre.getIdGenre());
            }
        }

        bookDao.update(book);
        view.showMessage("Book updated successfully!");
    }

    public void deleteBook() {
        String title = view.getInput("Enter title to delete: ");
        List<Book> books = bookDao.searchByTitle(title);
        if (books.isEmpty()) {
            view.showMessage("No books found.");
            return;
        }
        books.forEach(b -> view.showMessage(b.getIdBook() + " | " + b.getTitle()));
        int id = Integer.parseInt(view.getInput("Enter ID to delete: "));
        bookDao.delete(id);
        view.showMessage("Book deleted successfully!");
    }

    public void searchByTitle() {
        String title = view.getInput("Enter title to search: ");
        List<Book> books = bookDao.searchByTitle(title);
        books.forEach(b -> view.showMessage(b.toString()));
    }

    public void searchByAuthor() {
        String authorName = view.getInput("Enter author name to search: ");
        List<Book> books = bookDao.searchByAuthor(authorName);
        books.forEach(b -> view.showMessage(b.toString()));
    }

    public void searchByGenre() {
        String genreName = view.getInput("Enter genre to search: ");
        List<Book> books = bookDao.searchByGenre(genreName);
        if (books.isEmpty()) {
            view.showMessage("No books found for genre: " + genreName);
        } else {
            books.forEach(b -> view.showMessage(b.toString()));
        }
    }
}
