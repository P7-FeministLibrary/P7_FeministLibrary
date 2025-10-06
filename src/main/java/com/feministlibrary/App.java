package com.feministlibrary;

import java.util.Scanner;

import com.feministlibrary.config.DBManager;
import com.feministlibrary.model.author.Author;
import com.feministlibrary.model.author.AuthorDAOImplementation;
import com.feministlibrary.model.author.AuthorDAOInterface;
import com.feministlibrary.model.genre.Genre;
import com.feministlibrary.model.genre.GenreDAOImplementation;
import com.feministlibrary.model.genre.GenreDAOInterface;
import com.feministlibrary.model.book.Book;
import com.feministlibrary.model.book.BookDAOImplementation;
import com.feministlibrary.model.book.BookDAOInterface;
import com.feministlibrary.model.genre.GenreDAOInterface;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        BookDAOInterface dao = new BookDAOImplementation();

        while (running) {
            System.out.println("Welcome to The Matilda Library.");
            System.out.println("1. List book catalog");
            System.out.println("2. Add new book");
            System.out.println("3. Edit book");
            System.out.println("4. Delete book");
            System.out.println("5. Search for a book by title");
            System.out.println("6. Search for a book by author");
            System.out.println("7. Search for a book by genre");
            System.out.println("0. Exit");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    dao.getAll().forEach(System.out::println);
                    break;

                case "2": {
                    System.out.println("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter book description: ");
                    String description = scanner.nextLine();
                    System.out.println("Enter book ISBN code: ");
                    String isbn = scanner.nextLine();
                    System.out.println("Enter book author (ej. Chimamanda Ngozi Adichie): ");
                    String author = scanner.nextLine();
                    System.out.println("Enter book genre: ");
                    String genre = scanner.nextLine();
                }
                break;
                
                case "3":
                    System.out.println("Enter the ID of the book to edit: ");
                    int editId = Integer.parseInt(scanner.nextLine());
                    System.out.print("New title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("New description: ");
                    String newDescription = scanner.nextLine();
                    System.out.print("New ISBN code: ");
                    String newIsbn = scanner.nextLine();
                    Book bookToEdit = dao.getById(editId);
                    if (bookToEdit != null) {
                        bookToEdit.setTitle(newTitle);
                        bookToEdit.setDescription(newDescription);
                        bookToEdit.setIsbn(newIsbn);
                        dao.update(bookToEdit);
                        System.out.println("Book updated successfully!");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
            }
        }

        dao.getAll().forEach(System.out::println);
        AuthorDAOInterface authorDao = new AuthorDAOImplementation();
        authorDao.getAll().forEach(System.out::println);
        GenreDAOInterface genreDao = new GenreDAOImplementation();
        genreDao.getAll().forEach(System.out::println);

        /*
         * Book book = new Book("We should all be feminists", "A book that questions
         * long-held beliefs and gender stereotypes that perpetuate inequality between
         * men and women.", "234567832");
         * dao.insert(book);
         * authorDao.insert(new Author("Chamamanda", "Ngozi Adichie"));
         * genreDao.insert(new Genre("Essay"));
         */

        /*
         * borrar libro
         * 
         * int idToDelete = 9;
         * dao.delete(9);
         * 
         * System.out.println("Libro con ID " + idToDelete +
         * " borrado de la base de datos.");
         */

    }
}
