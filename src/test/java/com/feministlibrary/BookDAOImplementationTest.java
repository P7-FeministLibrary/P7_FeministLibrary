package com.feministlibrary;

import com.feministlibrary.config.DBManager;
import com.feministlibrary.model.book.Book;
import com.feministlibrary.model.book.BookDAOImplementation;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookDAOImplementationTest {

    @Mock private Connection mockConnection;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private ResultSet mockResultSet;

    @InjectMocks private BookDAOImplementation bookDAO;

    private AutoCloseable mocks;
    private MockedStatic<DBManager> dbManagerMock;

    @BeforeEach
    void setUp() throws Exception {
        mocks = MockitoAnnotations.openMocks(this);
        dbManagerMock = mockStatic(DBManager.class);
        dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
    }

    @AfterEach
    void tearDown() throws Exception {
        dbManagerMock.close();
        mocks.close();
    }

    @Test
    void testInsertBook() throws Exception {
        Book book = new Book("My Title", "Desc", "12345");

        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        ResultSet mockKeys = mock(ResultSet.class);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockKeys);
        when(mockKeys.next()).thenReturn(true);
        when(mockKeys.getInt(1)).thenReturn(1);

        bookDAO.insert(book);

        verify(mockPreparedStatement).setString(1, "My Title");
        verify(mockPreparedStatement).setString(2, "Desc");
        verify(mockPreparedStatement).setString(3, "12345");
        verify(mockPreparedStatement).executeUpdate();
        assertEquals(1, book.getIdBook());
    }

    @Test
    void testGetById() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("title")).thenReturn("Book 1");
        when(mockResultSet.getString("description")).thenReturn("Desc");
        when(mockResultSet.getString("isbn")).thenReturn("123");

        Book book = bookDAO.getById(1);

        assertNotNull(book);
        assertEquals("Book 1", book.getTitle());
        assertEquals("Desc", book.getDescription());
        assertEquals("123", book.getIsbn());
    }
}
