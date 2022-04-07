package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.ServiceException;
import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class JDBCQuestionDaoTest {
    @Mock
    ConnectionPool connectionPool;
    @Mock
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinetestingfortest", "root", "root");

    PreparedStatement create = connection.prepareStatement("INSERT INTO onlinetestingfortest.questions (tests_id, questionNumber, question) VALUES (?,?,?)");
    PreparedStatement findAllByTestId = connection.prepareStatement("SELECT * FROM onlinetestingfortest.questions WHERE tests_id=?");
    PreparedStatement findId = connection.prepareStatement("SELECT id FROM onlinetestingfortest.questions WHERE tests_id=? AND questionNumber=?");
    PreparedStatement findById = connection.prepareStatement("SELECT * FROM onlinetestingfortest.questions WHERE id=?");
    PreparedStatement delete = connection.prepareStatement("DELETE FROM onlinetestingfortest.questions WHERE id=?");
    PreparedStatement select = connection.prepareStatement("SELECT * FROM onlinetestingfortest.questions where tests_id=? AND question=?");

    QuestionDao questionDao = new JDBCQuestionDao();

    JDBCQuestionDaoTest() throws SQLException {
    }

    @Test
    void create() throws SQLException {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);

            Question question = new Question(107, 1, "QuestionCreate");
            Mockito.when(connection.prepareStatement(anyString())).thenReturn(create);
            questionDao.create(question);
            Mockito.when(connection.prepareStatement(anyString())).thenReturn(select);
            PreparedStatement preparedStatement = connection.prepareStatement("");
            preparedStatement.setInt(1, 107);
            preparedStatement.setString(2, "QuestionCreate");
            ResultSet resultSet = preparedStatement.executeQuery();
            Question questionFromDB = new Question();
            if (resultSet.next()) {
                questionFromDB = new Question(resultSet.getInt("tests_id"), resultSet.getInt("questionNumber"), resultSet.getString("question"));
            } else {
                fail();
            }
            assertEquals(question.getQuestionNumber(), questionFromDB.getQuestionNumber());
            assertEquals(question.getTextQuestion(), questionFromDB.getTextQuestion());
            assertEquals(question.getTestId(), questionFromDB.getTestId());
        }
    }

    @Test
    void delete() throws SQLException {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(select);
            PreparedStatement preparedStatement = connection.prepareStatement("");
            preparedStatement.setInt(1, 107);
            preparedStatement.setString(2, "QuestionCreate");
            ResultSet resultSet = preparedStatement.executeQuery();
            Question questionFromDB = new Question();
            if (resultSet.next()) {
                questionFromDB = new Question(resultSet.getInt("tests_id"), resultSet.getInt("questionNumber"), resultSet.getString("question"));
                questionFromDB.setId(resultSet.getInt("id"));
            } else {
                fail();
            }
            Mockito.when(connection.prepareStatement(anyString())).thenReturn(delete);
            questionDao.delete(questionFromDB.getId());

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(select);
            preparedStatement = connection.prepareStatement("");
            preparedStatement.setInt(1, 107);
            preparedStatement.setString(2, "QuestionCreate");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) fail();
        }
    }

    @Test
    void findAllByTestId() throws SQLException {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(findAllByTestId);
            List<Question> listFromDB = questionDao.findAllByTestId(107);

            Question question1 = new Question(107, 1, "Question 1");
            question1.setId(182);
            Question question2 = new Question(107, 2, "Питання 2");
            question2.setId(183);
            List<Question> list = Arrays.asList(question1, question2);
            assertEquals(list, listFromDB);

        }
    }

    @Test
    void findById() throws SQLException {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(findById);
            Question questionFromDB = questionDao.findById(183);
            Question question = new Question(107, 2, "Питання 2");
            assertEquals(question.getTextQuestion(), questionFromDB.getTextQuestion());
            assertEquals(question.getQuestionNumber(), questionFromDB.getQuestionNumber());
            assertEquals(question.getTestId(), questionFromDB.getTestId());
        }
    }


    @Test
    void update() {
        assertThrows(ServiceException.class, () -> questionDao.update(null));
    }
}