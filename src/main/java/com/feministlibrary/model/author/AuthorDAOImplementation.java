package com.feministlibrary.model.author;

import com.feministlibrary.Style;
import com.feministlibrary.config.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImplementation implements AuthorDAOInterface {

    private Author buildAuthorFromResultSet(ResultSet rs) throws SQLException {
        return new Author(rs.getInt("id"), rs.getString("name"), rs.getString("last_name"));
    }

    @Override
    public void insert(Author author) {
        String sql = "INSERT INTO author (name, last_name) VALUES (?, ?)";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, author.getName());
            stmt.setString(2, author.getLastName());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    author.setIdAuthor(rs.getInt(1));
                    System.out.println(Style.styleGreen("Author added successfully with ID " + author.getIdAuthor()));
                }
            }
        } catch (SQLException e) {
            System.out.println(Style.styleRed("Error adding author: " + e.getMessage()));
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
            int rows = stmt.executeUpdate();

            if (rows > 0)
                System.out.println(Style.styleGreen("Author updated successfully with ID " + author.getIdAuthor()));
        } catch (SQLException e) {
            System.out.println(Style.styleRed("Error updating author: " + e.getMessage()));
        }
    }

    @Override
    public Author getById(int idAuthor) {
        String sql = "SELECT * FROM author WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAuthor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return buildAuthorFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(Style.styleRed("Error fetching author by ID: " + e.getMessage()));
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

            while (rs.next())
                authors.add(buildAuthorFromResultSet(rs));

        } catch (SQLException e) {
            System.out.println(Style.styleRed("Error listing authors: " + e.getMessage()));
        }
        return authors;
    }

    @Override
    public List<Author> searchByNameOrLastName(String nameOrLastName) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM author WHERE name ILIKE ? OR last_name ILIKE ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nameOrLastName + "%");
            stmt.setString(2, "%" + nameOrLastName + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next())
                    authors.add(buildAuthorFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println(Style.styleRed("Error searching author: " + e.getMessage()));
        }
        return authors;
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        String sql = "SELECT * FROM author WHERE name=? AND last_name=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return buildAuthorFromResultSet(rs);
            }

        } catch (SQLException e) {
            System.out.println(Style.styleRed("Error fetching author: " + e.getMessage()));
        }
        return null;
    }
}
