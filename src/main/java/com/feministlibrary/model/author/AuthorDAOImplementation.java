package com.feministlibrary.model.author;

import com.feministlibrary.config.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImplementation implements AuthorDAOInterface {

    @Override
    public void insert(Author author) {
        String sql = "INSERT INTO author (name, last_name) VALUES (?, ?)";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, author.getName());
            stmt.setString(2, author.getLastName());
            stmt.executeUpdate();
            System.out.println("Author successfully added");

        } catch (SQLException e) {
            System.out.println("Error adding author: " + e.getMessage());
        }
    }

    @Override
    public void update(Author author) {
        String sql = "UPDATE author SET name=?, last_name=? WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, author.getName());
            stmt.setString(2, author.getLastName());
            stmt.setInt(3, author.getIdAuthor());
            stmt.executeUpdate();
            System.out.println("Author successfully updated");

        } catch (SQLException e) {
            System.out.println("Error updating author information: " + e.getMessage());
        }
    }

    @Override
    public void delete(int idAuthor) {
        String sql = "DELETE FROM author WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAuthor);
            stmt.executeUpdate();
            System.out.println("Author successfully removed");

        } catch (SQLException e) {
            System.out.println("Error removing author: " + e.getMessage());
        }
    }

    @Override
    public Author getById(int idAuthor) {
        String sql = "SELECT * FROM author WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAuthor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Author(
                        rs.getString("name"),
                        rs.getString("last_name"));
            }

        } catch (SQLException e) {
            System.out.println("Unable to find author: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM author";
        try (Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                authors.add(new Author(
                        rs.getString("name"),
                        rs.getString("last_name")));
            }

        } catch (SQLException e) {
            System.out.println("Unable to list authors: " + e.getMessage());
        }
        return authors;
    }

    @Override
    public List<Author> searchByName(String name) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM author WHERE name ILIKE ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(new Author(
                        rs.getString("name"),
                        rs.getString("last_name")));
            }

        } catch (SQLException e) {
            System.out.println("Unable to find author by name: " + e.getMessage());
        }
        return authors;
    }

    @Override
    public List<Author> searchByLastName(String lastName) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM author WHERE last_name ILIKE ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + lastName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(new Author(
                        rs.getString("name"),
                        rs.getString("last_name")));
            }

        } catch (SQLException e) {
            System.out.println("Unable to find author by last name: " + e.getMessage());
        }
        return authors;
    }
}
