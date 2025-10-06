package com.feministlibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.feministlibrary.model.author.Author;
import com.feministlibrary.model.author.AuthorDAOImplementation;
import com.feministlibrary.model.author.AuthorDAOInterface;
import com.feministlibrary.model.genre.Genre;
import com.feministlibrary.model.genre.GenreDAOImplementation;
import com.feministlibrary.model.genre.GenreDAOInterface;
import com.feministlibrary.model.book.Book;
import com.feministlibrary.model.book.BookDAOImplementation;
import com.feministlibrary.model.book.BookDAOInterface;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        BookDAOInterface bookDao = new BookDAOImplementation();
        AuthorDAOInterface authorDao = new AuthorDAOImplementation();
        GenreDAOInterface genreDao = new GenreDAOImplementation();

        while (running) {
            System.out.println("Welcome to The Matilda Library.");
            System.out.println("1. List book catalog");
            System.out.println("2. Add new book");
            System.out.println("3. Edit book");
            System.out.println("4. Delete book");
            System.out.println("5. Search book by title");
            System.out.println("6. Search book by author");
            System.out.println("7. Search book by genre");
            System.out.println("0. Exit");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    bookDao.getAll().forEach(System.out::println);
                    break;

                case "2": {
                    System.out.println("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter book description: ");
                    String description = scanner.nextLine();
                    System.out.println("Enter book ISBN code: ");
                    String isbn = scanner.nextLine();

                    System.out.println(
                            "Enter book author (separate multiple authors by comma, e.g. Chimamanda Ngozi, Clarice Lispector): ");
                    String authorsInput = scanner.nextLine().trim();
                    String[] authorArray = authorsInput.split(",");

                    List<Author> authors = new ArrayList<>();
                    for (String authorFullName : authorArray) {
                        String[] parts = authorFullName.trim().split(" ", 2);
                        String firstName = parts[0];
                        String lastName = parts.length > 1 ? parts[1] : "";
                        // lo inserté para que busque en la db antes de crear nuevo autor
                        Author author = authorDao.getByName(firstName, lastName);
                        if (author == null) {
                            author = new Author(firstName, lastName);
                            authorDao.insert(author);
                        }
                        authors.add(author);

                    }

                    System.out.println("Enter book genres (comma separated, e.g.: Essay, Philosophy): ");
                    String genresInput = scanner.nextLine().trim();
                    String[] genreArray = genresInput.split(",");
                    for (String genreName : genreArray) {
                        genreDao.insert(new Genre(genreName.trim()));
                    }

                    Book newBook = new Book(title, description, isbn);
                    bookDao.insert(newBook);

                    System.out.println("Book added successfully!");
                    break;
                }

                case "3":
                    System.out.print("Enter the title of the book to edit: ");
                    String titleToEdit = scanner.nextLine();
                    var booksFound = bookDao.searchByTitle(titleToEdit);

                    if (booksFound.isEmpty()) {
                        System.out.println("No book found with that title.");
                        break;
                    }
                    // REVERLOOOOO!!
                    if (booksFound.size() > 1) {
                        System.out.println("Multiple books found with that title:");
                        for (Book b : booksFound) {
                            System.out.println(b.getIdBook() + " | " + b.getTitle());
                        }
                        System.out.print("Enter the ID of the one you want to edit: ");
                        int chosenId = Integer.parseInt(scanner.nextLine());
                        booksFound = booksFound.stream()
                                .filter(b -> b.getIdBook() == chosenId)
                                .toList();
                    }

                    if (booksFound.isEmpty()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    Book bookToEdit = booksFound.get(0);

                    System.out.print("New title (press Enter to keep current): ");
                    String newTitle = scanner.nextLine();
                    if (!newTitle.isEmpty())
                        bookToEdit.setTitle(newTitle);

                    System.out.print("New author (press Enter to keep current): ");
                    String newAuthorInput = scanner.nextLine();
                    if (!newAuthorInput.isEmpty()) {
                        String[] parts = newAuthorInput.split(" ", 2);
                        String firstName = parts[0];
                        String lastName = parts.length > 1 ? parts[1] : "";

                        Author newAuthor = new Author(firstName, lastName);
                        authorDao.update(newAuthor);
                        // bookDao.addAuthorToBook(bookToEdit.getIdBook(), newAuthor.getIdAuthor());
                        System.out.println("New author added to the book!");
                    }

                    System.out.print("New description (press Enter to keep current): ");
                    String newDescription = scanner.nextLine();
                    if (!newDescription.isEmpty())
                        bookToEdit.setDescription(newDescription);

                    System.out.print("New ISBN code (press Enter to keep current): ");
                    String newIsbn = scanner.nextLine();
                    if (!newIsbn.isEmpty())
                        bookToEdit.setIsbn(newIsbn);

                    bookDao.update(bookToEdit);
                    System.out.println("Book updated successfully!");
                    break;

                case "4": {
                    System.out.println("\n--- Delete Book ---");
                    System.out.print("Enter title to delete: ");
                    String titleToDelete = scanner.nextLine();
                    List<Book> found = bookDao.searchByTitle(titleToDelete);
                    if (found.isEmpty()) {
                        System.out.println("No book found with that title.");
                        break;
                    }
                    found.forEach(b -> System.out.println(b.getIdBook() + " | " + b.getTitle()));
                    System.out.print("Enter ID to delete: ");

                    int idToDelete = Integer.parseInt(scanner.nextLine());
                    bookDao.delete(idToDelete);
                    System.out.println("Book deleted successfully!");
                    break;
                }

                case "0":
                    running = false;
                    System.out.println("Exiting the library system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }

        scanner.close();

        authorDao.getAll().forEach(System.out::println);
        genreDao.getAll().forEach(System.out::println);
    }
}
