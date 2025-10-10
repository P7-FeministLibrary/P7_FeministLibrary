package com.feministlibrary;

import com.feministlibrary.config.DBManager;
import com.feministlibrary.model.author.Author;
import com.feministlibrary.model.author.AuthorDAOImplementation;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorDAOImplementationTest {

    @Mock private Connection mockConnection;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private Statement mockStatement;
    @Mock private ResultSet mockResultSet;
    private MockedStatic<DBManager> dbManagerMock;

    @InjectMocks
    private AuthorDAOImplementation authorDAO;

    private AutoCloseable mocks;

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
    void test_Insert_Author() throws Exception {
        Author author = new Author("Virginia", "Woolf");

        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        ResultSet mockGeneratedKeys = mock(ResultSet.class);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockGeneratedKeys);
        when(mockGeneratedKeys.next()).thenReturn(true);
        when(mockGeneratedKeys.getInt(1)).thenReturn(1);

        authorDAO.insert(author);

        verify(mockPreparedStatement).setString(1, "Virginia");
        verify(mockPreparedStatement).setString(2, "Woolf");
        verify(mockPreparedStatement).executeUpdate();
        assertEquals(1, author.getIdAuthor());
    }

    @Test
    void test_Get_Author_By_Id() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Simone");
        when(mockResultSet.getString("last_name")).thenReturn("deBeauvoir");

        Author result = authorDAO.getById(1);

        assertNotNull(result);
        assertEquals("Simone", result.getName());
        assertEquals("deBeauvoir", result.getLastName());
    }
}
