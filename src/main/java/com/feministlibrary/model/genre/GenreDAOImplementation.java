    package com.feministlibrary.model.genre;

    import com.feministlibrary.config.DBManager;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class GenreDAOImplementation implements GenreDAOInterface {

        private Genre mapResultSetToGenre(ResultSet rs) throws SQLException {
            Genre genre = new Genre(rs.getString("genre"));
            genre.setIdGenre(rs.getInt("id"));
            return genre;
        }

        @Override
        public void insert(Genre genre) {
            String sql = "INSERT INTO genre (genre) VALUES (?)";
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, genre.getGenre());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) genre.setIdGenre(rs.getInt(1));
                }
            } catch (SQLException e) {
                System.out.println("Error adding genre: " + e.getMessage());
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
            } catch (SQLException e) {
                System.out.println("Error updating genre: " + e.getMessage());
            }
        }

        @Override
        public Genre getById(int idGenre) {
            String sql = "SELECT * FROM genre WHERE id=?";
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idGenre);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return mapResultSetToGenre(rs);
                }
            } catch (SQLException e) {
                System.out.println("Error fetching genre by ID: " + e.getMessage());
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
                while (rs.next()) genres.add(mapResultSetToGenre(rs));
            } catch (SQLException e) {
                System.out.println("Error listing genres: " + e.getMessage());
            }
            return genres;
        }

        @Override
        public List<Genre> searchByGenre(String genreName) {
            List<Genre> genres = new ArrayList<>();
            String sql = "SELECT * FROM genre WHERE genre ILIKE ?";
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + genreName + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) genres.add(mapResultSetToGenre(rs));
                }
            } catch (SQLException e) {
                System.out.println("Error searching genres: " + e.getMessage());
            }
            return genres;
        }

        @Override
        public Genre getByName(String name) {
            String sql = "SELECT * FROM genre WHERE genre ILIKE ?";
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return mapResultSetToGenre(rs);
                }
            } catch (SQLException e) {
                System.out.println("Error fetching genre by name: " + e.getMessage());
            }
            return null;
        }
    }
