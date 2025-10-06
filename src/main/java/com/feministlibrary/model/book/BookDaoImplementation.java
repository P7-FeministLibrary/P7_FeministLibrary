package com.feministlibrary.model.book;

import com.feministlibrary.config.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImplementation implements BookDAOInterface {


    @Override
    public void insert(Book book) {
        String sql = "INSERT INTO book (title, description, isbn) VALUES (?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getDescription());
            stmt.setString(3, book.getIsbn());
            stmt.executeUpdate();
            System.out.println("Book successfully added");
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
        String sql = "DELETE FROM book WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBook);
            stmt.executeUpdate();
            System.out.println("Book successfully removed");
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
                SELECT b.id_book, b.title, b.description, b.isbn
                FROM book b
                JOIN book_author ba ON b.id = ba.id
                JOIN author a ON ba.id = a.id
                WHERE a.name ILIKE ?;
                """;
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + authorName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
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
                SELECT b.id, b.title, b.description, b.isbn
                FROM book b
                JOIN book_genre bg ON b.id = bg.id
                JOIN genre g ON bg.id = g.id
                WHERE g.name ILIKE ?;
                """;
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + genreName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("isbn")));
            }
        } catch (SQLException e) {
            System.out.println("Error searching booking by genre: " + e.getMessage());
        }
        return books;
    }
}    

