package com.feministlibrary;

import com.feministlibrary.model.genre.Genre;
import com.feministlibrary.model.genre.GenreDAOImplementation;
import com.feministlibrary.config.DBManager;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreDAOIntegrationTest {

    private static GenreDAOImplementation genreDAO;
    private static JdbcDataSource ds;
    private static MockedStatic<DBManager> dbManagerMock;

    @BeforeAll
    static void setUp() throws Exception {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");

        try (Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE genre (id INT AUTO_INCREMENT PRIMARY KEY, genre VARCHAR(255));");
        }

        dbManagerMock = mockStatic(DBManager.class);
        dbManagerMock.when(DBManager::getConnection)
                .thenAnswer(invocation -> ds.getConnection());

        genreDAO = new GenreDAOImplementation();
    }

    @AfterAll
    static void tearDown() {
        dbManagerMock.close();
    }

    @Test
    void test_Insert_Genre_and_Get_Genre_By_Id() {
        Genre genre = new Genre("Essay");
        genreDAO.insert(genre);

        assertTrue(genre.getIdGenre() > 0);
        Genre found = genreDAO.getById(genre.getIdGenre());
        assertNotNull(found);
        assertEquals("Essay", found.getGenre());
    }

    @Test
    void test_Update_Genre() {
        Genre genre = new Genre("Politics");
        genreDAO.insert(genre);

        genre.setGenre("Political Theory");
        genreDAO.update(genre);

        Genre updated = genreDAO.getById(genre.getIdGenre());
        assertNotNull(updated);
        assertEquals("Political Theory", updated.getGenre());
    }

    @Test
    void test_Get_All_Genres() {
        genreDAO.insert(new Genre("Novel"));
        genreDAO.insert(new Genre("Poetry"));

        List<Genre> allGenres = genreDAO.getAll();
        assertFalse(allGenres.isEmpty());
        assertTrue(allGenres.size() >= 2);
    }

    @Test
    void test_Search_By_Genre() {
        genreDAO.insert(new Genre("Memoir"));
        List<Genre> results = genreDAO.searchByGenre("Memoir");

        assertEquals(1, results.size());
        assertEquals("Memoir", results.get(0).getGenre()); 
    }
}
