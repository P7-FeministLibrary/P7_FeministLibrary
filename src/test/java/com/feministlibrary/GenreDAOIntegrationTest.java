package com.feministlibrary;

import com.feministlibrary.model.genre.Genre;
import com.feministlibrary.model.genre.GenreDAOImplementation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenreDAOIntegrationTest {

    private static GenreDAOImplementation genreDAO;

    @BeforeAll
    static void setUp() throws Exception {
        genreDAO = new GenreDAOImplementation();
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {

            // Crear tabla en H2
            stmt.execute("CREATE TABLE genre (id INT AUTO_INCREMENT PRIMARY KEY, genre VARCHAR(255));");
        }
        }

    @Test
    void testInsertAndGetById() throws Exception {
        Genre genre = new Genre("Essay");
        genreDAO.insert(genre);

        assertTrue(genre.getIdGenre() > 0);

        Genre found = genreDAO.getById(genre.getIdGenre());
        assertNotNull(found);
        assertEquals("Essay", found.getGenre());
    }

    @Test
    void testUpdate() throws Exception {
        Genre genre = new Genre("Politics");
        genreDAO.insert(genre);

        genre.setGenre("Political Theory");
        genreDAO.update(genre);

        Genre updated = genreDAO.getById(genre.getIdGenre());
        assertEquals("Political Theory", updated.getGenre());
    }

    @Test
    void testGetAll() throws Exception {
        genreDAO.insert(new Genre("Novel"));
        List<Genre> allGenres = genreDAO.getAll();
        assertFalse(allGenres.isEmpty());
    }
}
