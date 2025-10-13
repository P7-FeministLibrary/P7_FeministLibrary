package com.feministlibrary;

import com.feministlibrary.config.DBManager;
import com.feministlibrary.model.author.Author;
import com.feministlibrary.model.author.AuthorDAOImplementation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class AuthorDAOIntegrationTest {

    private static AuthorDAOImplementation authorDAO;
    private static JdbcDataSource ds;
    private static MockedStatic<DBManager> dbManagerMock;

    @BeforeAll
    static void setupDatabase() throws Exception {
        authorDAO = new AuthorDAOImplementation();

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");

        try (Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "CREATE TABLE author (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), last_name VARCHAR(255));");
        }
        dbManagerMock = mockStatic(DBManager.class);
        dbManagerMock.when(DBManager::getConnection)
                .thenAnswer(invocation -> ds.getConnection());

        authorDAO = new AuthorDAOImplementation();
    }

    @AfterAll
    static void tearDown() {
        dbManagerMock.close();
    }

    @Test
    void test_Insert_Author_And_Get_Author_By_Id() {
        Author author = new Author("Chimamanda", "Ngozi");
        authorDAO.insert(author);

        assertTrue(author.getIdAuthor() > 0);

        Author found = authorDAO.getById(author.getIdAuthor());
        assertNotNull(found);
        assertEquals("Chimamanda", found.getName());
    }

    @Test
    void test_Update_Author() {
        Author author = new Author("Roxane", "Gay");
        authorDAO.insert(author);

        author.setName("Roxane Updated");
        authorDAO.update(author);

        Author updated = authorDAO.getById(author.getIdAuthor());
        assertEquals("Roxane Updated", updated.getName());
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
