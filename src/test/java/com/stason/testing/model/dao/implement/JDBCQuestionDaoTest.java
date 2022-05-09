package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.DataBaseException;
import com.stason.testing.controller.exceptions.ServiceException;
import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JDBCQuestionDaoTest {
    private final JDBCQuestionDao questionDao = new JDBCQuestionDao();
    MockedStatic<ConnectionPool> cp;
    @Mock
    ConnectionPool connectionPool;
    Connection mockConnection = mock(Connection.class);
    PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
    ResultSet mockResultSet = mock(ResultSet.class);


    @BeforeEach
    public void setUp() throws SQLException {
        cp = mockStatic(ConnectionPool.class);
        cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
        when(connectionPool.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        Connection testConnection = ConnectionPool.getInstance().getConnection();
        Assertions.assertNotNull(testConnection);
    }
    @AfterEach
    public void tearDown() {
        cp.close();
    }
    private static class Query {
        static final String CREATE = "INSERT INTO onlinetesting.questions (tests_id, questionNumber, question) VALUES (?,?,?)";
        static final String FIND_ALL_BY_TEST_ID = "SELECT * FROM onlinetesting.questions WHERE tests_id=?";
        static final String FIND_BY_ID = "SELECT * FROM onlinetesting.questions WHERE id=?";
        static final String DELETE = "DELETE FROM onlinetesting.questions WHERE id=?";
    }
    @Test
    public void testCreate() throws SQLException {
        Question question = new Question(1,2,"Question 2");
        questionDao.create(question);
        verify(mockConnection,times(1)).prepareStatement(Query.CREATE);
        verify(mockPreparedStatement,times(1)).setInt(1,1);
        verify(mockPreparedStatement,times(1)).setInt(2,2);
        verify(mockPreparedStatement,times(1)).setString(3,"Question 2");
        verify(mockPreparedStatement,times(1)).execute();
        verify(mockPreparedStatement,never()).executeQuery();
        verify(mockPreparedStatement,never()).executeUpdate();
    }
    @Test
    public void testCreateError() throws SQLException {
        when(mockPreparedStatement.execute()).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class,()->{
            questionDao.create(new Question());
        });
        assertEquals("Can't create question=null",thrown.getMessage());
    }
    @Test
    public void testFindAllByTestIdEmpty() throws SQLException {
        assertEquals(Collections.EMPTY_LIST,questionDao.findAllByTestId(1));
        verify(mockConnection,times(1)).prepareStatement(Query.FIND_ALL_BY_TEST_ID);
        verify(mockPreparedStatement,times(1)).setInt(1,1);
        verify(mockPreparedStatement,times(1)).executeQuery();
        verify(mockPreparedStatement,never()).execute();
        verify(mockPreparedStatement,never()).executeUpdate();
    }
    @Test
    public void testFindAllByTestIdNotEmpty() throws SQLException {
        Question question = new Question(1,1,1,"Question");
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(question.getId());
        when(mockResultSet.getInt("tests_id")).thenReturn(question.getTestId());
        when(mockResultSet.getInt("questionNumber")).thenReturn(question.getQuestionNumber());
        when(mockResultSet.getString("question")).thenReturn(question.getTextQuestion());

        assertEquals(Collections.singletonList(question),questionDao.findAllByTestId(1));

        verify(mockConnection,times(1)).prepareStatement(Query.FIND_ALL_BY_TEST_ID);
        verify(mockPreparedStatement,times(1)).setInt(1,1);
        verify(mockPreparedStatement,times(1)).executeQuery();
        verify(mockPreparedStatement,never()).execute();
        verify(mockPreparedStatement,never()).executeUpdate();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(1)).getInt("id");
    }
    @Test
    public void testFindAllByTestIdError() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
       DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
           questionDao.findAllByTestId(1);
       });
       assertEquals("Can't all find question for test",thrown.getMessage());
    }
    @Test
    public void testFindByIdError() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            questionDao.findById(1);
        });
        assertEquals("Can't find question for id=1",thrown.getMessage());
    }
    @Test
    public void testFindByIdNull() throws SQLException {
       assertNull(questionDao.findById(1));

    }
    @Test
    public void testFindByIdNotNull() throws SQLException {
        Question question = new Question(1,1,1,"Question");
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(question.getId());
        when(mockResultSet.getInt("tests_id")).thenReturn(question.getTestId());
        when(mockResultSet.getInt("questionNumber")).thenReturn(question.getQuestionNumber());
        when(mockResultSet.getString("question")).thenReturn(question.getTextQuestion());
        assertEquals(question,questionDao.findById(1));

        verify(mockConnection, times(1)).prepareStatement(Query.FIND_BY_ID);
        verify(mockPreparedStatement,times(1)).setInt(1,1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement,never()).execute();
        verify(mockPreparedStatement,never()).executeUpdate();
    }
    @Test
    public void testUpdateError() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            questionDao.update(new Question());
        });
        assertEquals("Can't update question",thrown.getMessage());
    }
    @Test
    public void testDeleteError() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            questionDao.delete(1);
        });
        assertEquals("Can't delete question for id=1",thrown.getMessage());
    }
    @Test
    public void testDelete() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(questionDao.delete(1));
        verify(mockConnection,times(1)).prepareStatement(Query.DELETE);
        verify(mockPreparedStatement,times(1)).setInt(1,1);
        verify(mockPreparedStatement,times(1)).executeUpdate();
    }
}