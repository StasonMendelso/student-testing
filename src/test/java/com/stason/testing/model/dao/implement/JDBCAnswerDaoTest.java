package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.DataBaseException;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.entity.Answer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JDBCAnswerDaoTest {
    private final JDBCAnswerDao answerDao = new JDBCAnswerDao();
    MockedStatic<ConnectionPool> cp;
    @Mock
    ConnectionPool connectionPool;
    Connection mockConnection = mock(Connection.class);
    PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    @BeforeEach
    public void setUp() throws SQLException {
        System.out.println("Open");
        cp = mockStatic(ConnectionPool.class);
        cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
        when(connectionPool.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        Connection testConnection = ConnectionPool.getInstance().getConnection();
        Assertions.assertNotNull(testConnection);
    }

    private static class Query {
        static final String CREATE = "INSERT INTO onlinetesting.answers (answer, isRightAnswer, questions_id) VALUES (?,?,?)";
        static final String FIND_ALL_BY_QUESTION_ID = "SELECT * FROM onlinetesting.answers WHERE questions_id=?";
        static final String DELETE = "DELETE FROM onlinetesting.answers WHERE id=?";
    }
    @AfterEach
    public void tearDown() {
        System.out.println("Close");
        cp.close();
    }
    @Test
    public void testCreate() throws SQLException {
        Answer answer = new Answer("right answer", true, 12);
        answerDao.create(answer);
        verify(mockConnection, times(1)).prepareStatement(Query.CREATE);
        verify(mockPreparedStatement, times(1)).setString(1, "right answer");
        verify(mockPreparedStatement, times(1)).setBoolean(2, true);
        verify(mockPreparedStatement, times(1)).setInt(3, 12);
        verify(mockPreparedStatement, times(1)).execute();
        verify(mockPreparedStatement, never()).executeQuery();
    }

    @Test()
    public void testCreateError() throws SQLException {
        Answer answer = new Answer("right answer", true, 12);
        when(mockPreparedStatement.execute()).thenThrow(SQLException.class);
        assertThrows(DataBaseException.class, () -> {
            answerDao.create(answer);
        });
    }

    @Test
    public void testFindAllByQuestionIdEmpty() throws SQLException {
        assertEquals(Collections.emptyList(), answerDao.findAllByQuestionId(120));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_ALL_BY_QUESTION_ID);
        verify(mockPreparedStatement, times(1)).setInt(1, 120);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, never()).getInt("id");
    }

    @Test
    public void testFindAllByQuestionIdNotEmpty() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("answer")).thenReturn("right answer");
        when(mockResultSet.getBoolean("isRightAnswer")).thenReturn(true);
        when(mockResultSet.getInt("questions_id")).thenReturn(120);
        when(mockResultSet.getInt("id")).thenReturn(1);

        assertEquals(Collections.singletonList(new Answer(1, "right answer", true, 120)), answerDao.findAllByQuestionId(120));

        verify(mockConnection, times(1)).prepareStatement(Query.FIND_ALL_BY_QUESTION_ID);
        verify(mockPreparedStatement, times(1)).setInt(1, 120);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(1)).getInt("id");
    }

    @Test
    public void testFindAllByQuestionIdError() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DataBaseException.class, () -> {
            answerDao.findAllByQuestionId(1);
        });

    }

    @Test
    public void testDeleteError() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DataBaseException.class, () -> {
            answerDao.delete(1);
        });
    }

    @Test
    public void testDelete() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(answerDao.delete(1));
        verify(mockConnection, times(1)).prepareStatement(Query.DELETE);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockResultSet, never()).next();
    }

    @Test
    public void testFindByIdError() {
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            answerDao.findById(1);
        });
        assertEquals("Can't find Answer by id",thrown.getMessage());
    }
    @Test
    public void testUpdate() {
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            answerDao.update(new Answer());
        });
        assertEquals("Can't update answer",thrown.getMessage());
    }

}
