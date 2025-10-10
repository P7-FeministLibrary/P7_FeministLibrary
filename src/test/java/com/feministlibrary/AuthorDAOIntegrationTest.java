package com.feministlibrary;

import com.feministlibrary.model.author.Author;
import com.feministlibrary.model.author.AuthorDAOImplementation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDAOIntegrationTest {

    private static AuthorDAOImplementation authorDAO;

    @BeforeAll
    static void setupDatabase() throws Exception {
        authorDAO = new AuthorDAOImplementation();

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE author (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), last_name VARCHAR(255));");
        }
    }

    @Test
    void test_Insert_Author_And_Get_Author_By_Id() {
        Author author = new Author("Audre", "Lorde");
        authorDAO.insert(author);

        assertTrue(author.getIdAuthor() > 0);

        Author found = authorDAO.getById(author.getIdAuthor());
        assertNotNull(found);
        assertEquals("Audre", found.getName());
    }

    @Test
    void test_Update_Author() {
        Author author = new Author("Mary", "Wollstonecraft");
        authorDAO.insert(author);

        author.setName("Mary Updated");
        authorDAO.update(author);

        Author updated = authorDAO.getById(author.getIdAuthor());
        assertEquals("Mary Updated", updated.getName());
    }

    @Test
    void test_Get_All_Authrs() {
        authorDAO.insert(new Author("Gloria", "Steinem"));
        List<Author> authors = authorDAO.getAll();
        assertFalse(authors.isEmpty());
    }

    @Test
    void test_Get_Author_By_Name() {
        Author author = new Author("Betty", "Friedan");
        authorDAO.insert(author);

        Author found = authorDAO.getByName("Betty", "Friedan");
        assertNotNull(found);
    }
}
