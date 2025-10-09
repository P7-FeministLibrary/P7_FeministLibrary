    package com.feministlibrary.model.genre;

    import com.feministlibrary.config.DBManager;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class GenreDAOImplementation implements GenreDAOInterface {

        @Override
        public void insert(Genre genre) {
            String sql = "INSERT INTO genre (genre) VALUES (?) RETURNING id";
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, genre.getGenre());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        genre.setIdGenre(rs.getInt(1));
                    }
                }
                System.out.println("Genre successfully added");
            } catch (SQLException e) {
                System.out.println("Unable to add genre: " + e.getMessage());
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
                System.out.println("Genre successfully updated");
            } catch (SQLException e) {
                System.out.println("Unable to update genre: " + e.getMessage());
            }
        }

        @Override
        public void delete(int idGenre) {
            String sql = "DELETE FROM genre WHERE id=?";
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idGenre);
                stmt.executeUpdate();
                System.out.println("Genre successfully deleted");
            } catch (SQLException e) {
                System.out.println("Unable to delete genre: " + e.getMessage());
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
                    Genre g = new Genre(rs.getString("genre"));
                    g.setIdGenre(rs.getInt("id"));
                    return g;
                }
            } catch (SQLException e) {
                System.out.println("Error searching genre by ID: " + e.getMessage());
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
                    Genre g = new Genre(rs.getString("genre"));
                    g.setIdGenre(rs.getInt("id"));
                    genres.add(g);
                }
            } catch (SQLException e) {
                System.out.println("Unable to list genres: " + e.getMessage());
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
                    Genre g = new Genre(rs.getString("genre"));
                    g.setIdGenre(rs.getInt("id"));
                    genres.add(g);
                }
            } catch (SQLException e) {
                System.out.println("Unable to search genre by name: " + e.getMessage());
            }
            return genres;
        }

        @Override
        public Genre getByName(String name) {
            String sql = "SELECT id, genre FROM genre WHERE genre ILIKE ?";
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Genre g = new Genre(rs.getString("genre"));
                    g.setIdGenre(rs.getInt("id"));
                    return g;
                }
            } catch (SQLException e) {
                System.out.println("Error searching genre by name: " + e.getMessage());
            }
            return null;
        }
    }
