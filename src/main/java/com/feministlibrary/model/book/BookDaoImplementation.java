    package com.feministlibrary.model.book;

    import com.feministlibrary.Style;
    import com.feministlibrary.config.DBManager;
    import java.sql.*;
    import java.util.*;

    public class BookDaoImplementation implements BookDAOInterface {

        private void executeUpdate(String sql, int... params) {
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < params.length; i++) stmt.setInt(i + 1, params[i]);
                stmt.executeUpdate();
            } catch (SQLException e) { System.out.println(Style.styleRed("DB Error: " + e.getMessage())); }
        }

        private List<Book> mapBooks(ResultSet rs) throws SQLException {
            Map<Integer, Book> bookMap = new LinkedHashMap<>();
            while (rs.next()) {
                int id = rs.getInt("book_id");
                Book book = bookMap.get(id);
                if (book == null) {
                    book = new Book(id, rs.getString("title"), rs.getString("description"), rs.getString("isbn"));
                    bookMap.put(id, book);
                }
                String authorName = rs.getString("author_name");
                String authorLast = rs.getString("author_last");
                if (authorName != null && authorLast != null) book.addAuthor(authorName + " " + authorLast);
                String genre = rs.getString("genre_name");
                if (genre != null) book.addGenre(genre);
            }
            return new ArrayList<>(bookMap.values());
        }

        @Override
        public void insert(Book book) {
            String sql = "INSERT INTO book (title, description, isbn) VALUES (?, ?, ?)";
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getDescription());
                stmt.setString(3, book.getIsbn());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) { if (rs.next()) book.setIdBook(rs.getInt(1)); }
            } catch (SQLException e) { System.out.println(Style.styleRed("Error adding book: " + e.getMessage())); }
        }

        @Override
        public void update(Book book) {
            executeUpdate("UPDATE book SET title=?, description=?, isbn=? WHERE id=?", book.getIdBook());
        }

        @Override
        public void delete(int idBook) {
            executeUpdate("DELETE FROM book_author WHERE id_book = ?", idBook);
            executeUpdate("DELETE FROM book_genre WHERE id_book = ?", idBook);
            executeUpdate("DELETE FROM book WHERE id=?", idBook);
        }

        @Override
        public Book getById(int idBook) {
            String sql = "SELECT * FROM book WHERE id=?";
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idBook);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) return new Book(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("isbn"));
            } catch (SQLException e) { System.out.println(Style.styleRed("Error: " + e.getMessage())); }
            return null;
        }

        private List<Book> search(String sql, String param) {
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + param + "%");
                return mapBooks(stmt.executeQuery());
            } catch (SQLException e) { System.out.println(Style.styleRed("Search error: " + e.getMessage())); }
            return new ArrayList<>();
        }

        @Override
        public List<Book> getAll() {
            String sql = """
                    SELECT b.id AS book_id, b.title, b.description, b.isbn,
                        a.name AS author_name, a.last_name AS author_last,
                        g.genre AS genre_name
                    FROM book b
                    LEFT JOIN book_author ba ON b.id = ba.id_book
                    LEFT JOIN author a ON ba.id_author = a.id
                    LEFT JOIN book_genre bg ON b.id = bg.id_book
                    LEFT JOIN genre g ON bg.id_genre = g.id
                    ORDER BY b.id;
                    """;
            try (Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                return mapBooks(rs);
            } catch (SQLException e) { System.out.println(Style.styleRed("List error: " + e.getMessage())); }
            return new ArrayList<>();
        }

        @Override
        public List<Book> searchByTitle(String title) {
            String sql = """
                    SELECT b.id AS book_id, b.title, b.description, b.isbn,
                        a.name AS author_name, a.last_name AS author_last,
                        g.genre AS genre_name
                    FROM book b
                    JOIN book_author ba ON b.id = ba.id_book
                    JOIN author a ON ba.id_author = a.id
                    LEFT JOIN book_genre bg ON b.id = bg.id_book
                    LEFT JOIN genre g ON bg.id_genre = g.id
                    WHERE b.title ILIKE ?
                    ORDER BY b.id;
                    """;
            return search(sql, title);
        }

        @Override
        public List<Book> searchByAuthor(String authorName) {
            String sql = """
                    SELECT b.id AS book_id, b.title, b.description, b.isbn,
                        a.name AS author_name, a.last_name AS author_last,
                        g.genre AS genre_name
                    FROM book b
                    JOIN book_author ba ON b.id = ba.id_book
                    JOIN author a ON ba.id_author = a.id
                    LEFT JOIN book_genre bg ON b.id = bg.id_book
                    LEFT JOIN genre g ON bg.id_genre = g.id
                    WHERE CONCAT(a.name,' ',a.last_name) ILIKE ?;
                    """;
            return search(sql, authorName);
        }

        @Override
        public List<Book> searchByGenre(String genreName) {
            String sql = """
                    SELECT b.id AS book_id, b.title, b.description, b.isbn,
                        a.name AS author_name, a.last_name AS author_last,
                        g.genre AS genre_name
                    FROM book b
                    JOIN book_genre bg ON b.id = bg.id_book
                    JOIN genre g ON bg.id_genre = g.id
                    LEFT JOIN book_author ba ON b.id = ba.id_book
                    LEFT JOIN author a ON ba.id_author = a.id
                    WHERE g.genre ILIKE ?;
                    """;
            return search(sql, genreName);
        }

        @Override
        public void addAuthorToBook(int idBook, int idAuthor) { executeUpdate("INSERT INTO book_author (id_book,id_author) VALUES (?,?)", idBook, idAuthor); }
        @Override
        public void addGenreToBook(int idBook, int idGenre) { executeUpdate("INSERT INTO book_genre (id_book,id_genre) VALUES (?,?)", idBook, idGenre); }
        @Override
        public void updateAuthor(int idAuthor, String newFirstName, String newLastName) {
            try (Connection conn = DBManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE author SET name=?, last_name=? WHERE id=?")) {
                stmt.setString(1,newFirstName); stmt.setString(2,newLastName); stmt.setInt(3,idAuthor);
                stmt.executeUpdate();
            } catch (SQLException e) { System.out.println(Style.styleRed("Error updating author: "+e.getMessage())); }
        }
        @Override
        public void removeAuthorsFromBook(int idBook) { executeUpdate("DELETE FROM book_author WHERE id_book=?", idBook); }
        @Override
        public void removeGenresFromBook(int idBook) { executeUpdate("DELETE FROM book_genre WHERE id_book=?", idBook); }
    }
