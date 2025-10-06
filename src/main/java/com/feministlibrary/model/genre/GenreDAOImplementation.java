package com.feministlibrary.model.genre;

import com.feministlibrary.config.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImplementation implements GenreDAOInterface {

    @Override
    public void insert(Genre genre) {
        String sql = "INSERT INTO genre (genre) VALUES (?)";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, genre.getGenre());
            stmt.executeUpdate();
            System.out.println("Genre added successfully");

        } catch (SQLException e) {
            System.out.println("Error when adding genre: " + e.getMessage());
        }
    }

    @Override
    public void update(Genre genre) {
        String sql = "UPDATE genre SET genre=? WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, genre.getGenre());
            stmt.setInt(2, genre.getIdGenre());
            stmt.executeUpdate();
            System.out.println("Genre updated successfully");

        } catch (SQLException e) {
            System.out.println("Error while updating genre: " + e.getMessage());
        }
    }

    @Override
    public void delete(int idGenre) {
        String sql = "DELETE FROM genre WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idGenre);
            stmt.executeUpdate();
            System.out.println("Genre deleted successfully");

        } catch (SQLException e) {
            System.out.println("Error deleting genre: " + e.getMessage());
        }
    }

    @Override
    public Genre getById(int idGenre) {
        String sql = "SELECT * FROM genre WHERE id=?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idGenre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Genre(
                        rs.getString("genre"));
            }

        } catch (SQLException e) {
            System.out.println("Error searching genre: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genre";
        try (Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                genres.add(new Genre(
                        rs.getString("genre")));
            }

        } catch (SQLException e) {
            System.out.println("Error when listing genres: " + e.getMessage());
        }
        return genres;
    }

    @Override
    public List<Genre> searchByGenre(String genre) {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genre WHERE genre ILIKE ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + genre + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                genres.add(new Genre(
                        rs.getString("genre")));
            }

        } catch (SQLException e) {
            System.out.println("Error searching genre by name: " + e.getMessage());
        }
        return genres;
    }

    
}
