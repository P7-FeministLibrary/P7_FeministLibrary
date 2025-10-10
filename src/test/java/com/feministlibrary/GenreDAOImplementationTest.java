package com.feministlibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import com.feministlibrary.config.DBManager;
import com.feministlibrary.model.genre.Genre;
import com.feministlibrary.model.genre.GenreDAOImplementation;

public class GenreDAOImplementationTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private GenreDAOImplementation genreDAO;

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
    void testInsertGenre() throws Exception {
        Genre genre = new Genre("Feminism");

        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        ResultSet mockGeneratedKeys = mock(ResultSet.class);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockGeneratedKeys);
        when(mockGeneratedKeys.next()).thenReturn(true);
        when(mockGeneratedKeys.getInt(1)).thenReturn(1);

        genreDAO.insert(genre);

        verify(mockPreparedStatement).setString(1, "Feminism");
        verify(mockPreparedStatement).executeUpdate();
        assertEquals(1, genre.getIdGenre());
    }

@Test
    void testGetById() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("genre")).thenReturn("Politics");

        Genre genre = genreDAO.getById(1);

        assertNotNull(genre);
        assertEquals("Politics", genre.getGenre());
    }

}