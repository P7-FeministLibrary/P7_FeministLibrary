package com.feministlibrary.model.book;

import com.feministlibrary.config.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookDAOImplementation implements BookDAOInterface {

    @Override
    public void insert(Book book) {
        String sql = "INSERT INTO book (title, description, isbn) VALUES (?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getDescription());
            stmt.setString(3, book.getIsbn());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    book.setIdBook(rs.getInt(1));
                    System.out.println("Book inserted with ID: " + book.getIdBook());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE book SET title=?, description=?, isbn=? WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getDescription());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getIdBook());
            stmt.executeUpdate();
            System.out.println("Book successfully updated");
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    @Override
    public void delete(int idBook) {
        String sqlDeleteBookAuthor = "DELETE FROM book_author WHERE id_book = ?";
        String sqlDeleteBookGenre = "DELETE FROM book_genre WHERE id_book = ?";
        String sqlDeleteBook = "DELETE FROM book WHERE id=?";

        try (Connection conn = DBManager.getConnection()) {
            Book bookToDelete = getById(idBook);
            if (bookToDelete == null) {
                System.out.println("Book with ID " + idBook + " not found.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteBookAuthor)) {
                stmt.setInt(1, idBook);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteBookGenre)) {
                stmt.setInt(1, idBook);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteBook)) {
                stmt.setInt(1, idBook);
                stmt.executeUpdate();
            }

            System.out.println("Book " + bookToDelete.getTitle() + " with ID " + bookToDelete.getIdBook() +
                    " and its relations were successfully removed");
        } catch (SQLException e) {
            System.out.println("Error removing book " + e.getMessage());
        }
    }

    @Override
    public Book getById(int idBook) {
        String sql = "SELECT * FROM book WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBook);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("isbn"));
            }
        } catch (SQLException e) {
            System.out.println("Unable to find book " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("isbn")));
            }
        } catch (SQLException e) {
            System.out.println("Unable to list books " + e.getMessage());
        }
        return books;
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE title ILIKE ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("isbn")));
            }
        } catch (SQLException e) {
            System.out.println("Error searching book by title: " + e.getMessage());
        }
        return books;
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        List<Book> books = new ArrayList<>();
        String sql = """
                SELECT b.id AS book_id, b.title, b.description, b.isbn,
                       a.id AS author_id, a.name AS author_name, a.last_name AS author_last,
                       g.id AS genre_id, g.genre AS genre_name
                FROM book b
                JOIN book_author ba ON b.id = ba.id_book
                JOIN author a ON ba.id_author = a.id
                LEFT JOIN book_genre bg ON b.id = bg.id_book
                LEFT JOIN genre g ON bg.id_genre = g.id
                WHERE CONCAT(a.name, ' ', a.last_name) ILIKE ?;
                """;
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + authorName + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("isbn")));
            }
        } catch (SQLException e) {
            System.out.println("Error searching book by author: " + e.getMessage());
        }
        return books;
    }

    @Override
    public List<Book> searchByGenre(String genreName) {
        List<Book> books = new ArrayList<>();
        String sql = """
                    SELECT b.id, b.title, b.description, b.isbn, g.genre
                    FROM book b
                    JOIN book_genre bg ON b.id = bg.id_book
                    JOIN genre g ON bg.id_genre = g.id
                    WHERE g.genre ILIKE ?;
                """;

        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + genreName + "%");
            ResultSet rs = stmt.executeQuery();

            Map<Integer, Book> bookMap = new LinkedHashMap<>();

            while (rs.next()) {
                int bookId = rs.getInt("id");
                Book book;
                if (bookMap.containsKey(bookId)) {
                    book = bookMap.get(bookId);
                } else {
                    book = new Book(
                            bookId,
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("isbn"));
                    bookMap.put(bookId, book);
                }

                book.addGenre(rs.getString("genre"));
            }

            books.addAll(bookMap.values());

        } catch (SQLException e) {
            System.out.println("Error searching book by genre: " + e.getMessage());
        }

        return books;
    }

    @Override
    public void addAuthorToBook(int idBook, int idAuthor) {
        String sql = "INSERT INTO book_author (id_book, id_author) VALUES (?, ?)";
        try (Connection conn = DBManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBook);
            stmt.setInt(2, idAuthor);
            stmt.executeUpdate();
            System.out.println("Author linked to book successfully!");
        } catch (SQLException e) {
            System.out.println("Error linking author to book: " + e.getMessage());
        }
    }

    @Override
    public void updateAuthor(int idAuthor, String newFirstName, String newLastName) {
        String sql = "UPDATE author SET name = ?, last_name = ? WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newFirstName);
            stmt.setString(2, newLastName);
            stmt.setInt(3, idAuthor);
            stmt.executeUpdate();

            System.out.println("Author updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error updating author: " + e.getMessage());
        }
    }

    @Override
    public void addGenreToBook(int idBook, int idGenre) {
        String sql = "INSERT INTO book_genre (id_book, id_genre) VALUES (?, ?)";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idBook);
            stmt.setInt(2, idGenre);
            stmt.executeUpdate();
            System.out.println("Genre linked to book successfully!");

        } catch (SQLException e) {
            System.out.println("Error linking genre to book: " + e.getMessage());
        }
    }
}