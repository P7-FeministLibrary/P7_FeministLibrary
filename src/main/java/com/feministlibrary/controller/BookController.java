                        package com.feministlibrary.controller;

import com.feministlibrary.Style;
import com.feministlibrary.model.author.Author;
import com.feministlibrary.model.author.AuthorDAOInterface;
import com.feministlibrary.model.book.Book;
import com.feministlibrary.model.book.BookDAOInterface;
import com.feministlibrary.model.genre.Genre;
import com.feministlibrary.model.genre.GenreDAOInterface;
import com.feministlibrary.view.BookView;
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
                                if (books.isEmpty()) view.showMessage("No books found.");
                                else books.forEach(b -> view.showMessage(b.toString()));
                            }

                            public void addBook() {
                    Book book = new Book(
                        view.getInput(Style.styleBlue("Enter book title:\n\n")),
                        view.getInput(Style.styleBlue("Enter book description:\n\n")),
                        view.getInput(Style.styleBlue("Enter ISBN code:\n\n"))
                    );
                                bookDao.insert(book);

                                handleAuthors(book);
                                handleGenres(book);

                view.showMessage(Style.styleGreen("Book added successfully!\n\n"));
                            }

                            public void editBook() {
                                Book book = selectBookByTitle(Style.styleBlue("Enter book title to edit:\n\n"));
                                if (book == null) return;

            String newTitle = view.getInput(Style.styleOrange("New title (Enter to keep current): "));
                                if (!newTitle.isEmpty()) book.setTitle(newTitle);

            String newDescription = view.getInput(Style.styleOrange("New description (Enter to keep current): "));
                                if (!newDescription.isEmpty()) book.setDescription(newDescription);

            String newIsbn = view.getInput(Style.styleOrange("New ISBN (Enter to keep current): "));
                                if (!newIsbn.isEmpty()) book.setIsbn(newIsbn);

                view.showMessage("Current authors: " + String.join(", ", book.getAuthors()));
                String newAuthorsInput = view.getInput(Style.styleOrange("Enter new author(s) (comma separated, Enter to keep current): "));
                if (!newAuthorsInput.isEmpty()) {
                    bookDao.removeAuthorsFromBook(book.getIdBook());
                    handleAuthors(book);
                }

                        view.showMessage("Current genres: " + String.join(", ", book.getGenres()));
                String newGenresInput = view.getInput(Style.styleOrange("Enter new genres (comma separated, Enter to keep current): "));
                if (!newGenresInput.isEmpty()) {
                    bookDao.removeGenresFromBook(book.getIdBook());
                    handleGenres(book);
                }

                                bookDao.update(book);
        view.showMessage(Style.styleGreen("Book updated successfully!\n\n"));
                            }

                            public void deleteBook() {
                                Book book = selectBookByTitle(Style.styleBlue("Enter title to delete: "));
                                if (book == null) return;

                                bookDao.delete(book.getIdBook());
        view.showMessage(Style.styleGreen("Book deleted successfully!\n\n"));
                            }

                            public void searchByTitle() {
                                searchAndShow(bookDao::searchByTitle, "Enter title to search: ");
                            }

                            public void searchByAuthor() {
                                searchAndShow(bookDao::searchByAuthor, "Enter author name to search: ");
                            }

                            public void searchByGenre() {
                                searchAndShow(bookDao::searchByGenre, "Enter genre to search: ");
                            }

                            private void handleAuthors(Book book) {
                                String authorsInput = view.getInput(Style.styleBlue("Enter author(s) (comma separated, e.g. First Last, First2 Last2): "));
                                if (authorsInput.isEmpty()) return;
                                String[] authorArray = authorsInput.split(",");
                                for (String fullName : authorArray) {
                                    fullName = fullName.trim();
                                    if (fullName.isEmpty()) continue;
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
                                String genresInput = view.getInput(Style.styleBlue("Enter genres (comma separated): "));
                                if (genresInput.isEmpty()) return;
                                String[] genreArray = genresInput.split(",");
                                for (String genreName : genreArray) {
                                    genreName = genreName.trim();
                                    if (genreName.isEmpty()) continue;
                                    Genre genre = genreDao.getByName(genreName);
                                    if (genre == null) {
                                        genre = new Genre(genreName);
                                        genreDao.insert(genre);
                                    }
                                    bookDao.addGenreToBook(book.getIdBook(), genre.getIdGenre());
                                }
                            }

                            private Book selectBookByTitle(String prompt) {
                                String title = view.getInput(prompt);
                                List<Book> books = bookDao.searchByTitle(title);
                                if (books.isEmpty()) {
    view.showMessage(Style.styleRed("No book found."));
                                    return null;
                                }
                                if (books.size() == 1) return books.get(0);

                                books.forEach(b -> view.showMessage(b.getIdBook() + " | " + b.getTitle()));
                                int id = Integer.parseInt(view.getInput("Enter the ID of the book: "));
                                Book book = bookDao.getById(id);
    view.showMessage(Style.styleYellow("Invalid selection."));
                                return book;
                            }

                            private void searchAndShow(java.util.function.Function<String, List<Book>> searchFunc, String prompt) {
                                String input = view.getInput(prompt);
                                List<Book> books = searchFunc.apply(input);
                                if (books.isEmpty()) view.showMessage("No results found.");
                                else books.forEach(b -> view.showMessage(b.toString()));
                            }
                        }
