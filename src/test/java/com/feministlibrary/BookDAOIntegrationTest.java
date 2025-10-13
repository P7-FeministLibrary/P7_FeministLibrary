package com.feministlibrary;

import com.feministlibrary.config.DBManager;
import com.feministlibrary.model.book.Book;
import com.feministlibrary.model.book.BookDAOImplementation;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookDAOIntegrationTest {

    private static BookDAOImplementation bookDAO;
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
            stmt.execute("""
                        CREATE TABLE book (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            title VARCHAR(255),
                            description TEXT,
                            isbn VARCHAR(50)
                        );
                    """);
            stmt.execute("""
                        CREATE TABLE author (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255),
                            last_name VARCHAR(255)
                        );
                    """);

            stmt.execute("""
                        CREATE TABLE genre (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            genre VARCHAR(255)
                        );
                    """);

            stmt.execute("""
                        CREATE TABLE book_author (
                            id_book INT,
                            id_author INT
                        );
                    """);

            stmt.execute("""
                        CREATE TABLE book_genre (
                            id_book INT,
                            id_genre INT
                        );
                    """);

        }

        dbManagerMock = mockStatic(DBManager.class);
        dbManagerMock.when(DBManager::getConnection)
                .thenAnswer(invocation -> ds.getConnection());

        bookDAO = new BookDAOImplementation();
    }

    @BeforeEach
    void cleanTable() throws Exception {
        try (Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM book_author");
            stmt.execute("DELETE FROM book_genre");
            stmt.execute("DELETE FROM book");
            stmt.execute("DELETE FROM author");
            stmt.execute("DELETE FROM genre");
        }
    }

    @AfterAll
    void tearDown() {
        dbManagerMock.close();
    }

    @Test
    void test_Insert_Book_And_Get_Book_By_Id() {
        Book book = new Book("My Book", "Description", "12345");
        bookDAO.insert(book);

        assertTrue(book.getIdBook() > 0);

        Book found = bookDAO.getById(book.getIdBook());
        assertNotNull(found);
        assertEquals("My Book", found.getTitle());
    }

    @Test
    void test_Get_All() {
        bookDAO.insert(new Book("Book1", "Description1", "111111"));
        bookDAO.insert(new Book("Book2", "Description2", "222222"));

        List<Book> allBooks = bookDAO.getAll();
        assertEquals(2, allBooks.size());
    }

    @Test
    void testDelete() {
        Book book = new Book("Book to Delete", "Description", "333333");
        bookDAO.insert(book);

        bookDAO.delete(book.getIdBook());
        Book deleted = bookDAO.getById(book.getIdBook());
        assertNull(deleted);
    }

   
}
