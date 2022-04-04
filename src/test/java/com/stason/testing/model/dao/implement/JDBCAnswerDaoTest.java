package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.ServiceException;
import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
@ExtendWith(MockitoExtension.class)
class JDBCAnswerDaoTest {
    @Mock
    ConnectionPool connectionPool;
    @Mock
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinetestingfortest","root","root");

    PreparedStatement create = connection.prepareStatement("INSERT INTO onlinetestingfortest.answers (answer, isRightAnswer, questions_id) VALUES (?,?,?)");
    PreparedStatement findAllByQuestionId = connection.prepareStatement("SELECT * FROM onlinetestingfortest.answers where questions_id=?");
    PreparedStatement delete = connection.prepareStatement("DELETE FROM onlinetestingfortest.answers WHERE questions_id=?");
    PreparedStatement select = connection.prepareStatement("SELECT * FROM onlinetestingfortest.answers where questions_id=? AND answer=?");

    AnswerDao answerDao = new JDBCAnswerDao();
    JDBCAnswerDaoTest() throws SQLException {
    }

    @Test
    void create() throws SQLException {
        try( MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)){
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);

            Answer answer = new Answer("Answer.testCreate",true,183);
            Mockito.when(connection.prepareStatement(anyString())).thenReturn(create);
            answerDao.create(answer);
            Mockito.when(connection.prepareStatement(anyString())).thenReturn(select);
            PreparedStatement preparedStatement = connection.prepareStatement("");
            preparedStatement.setInt(1,183);
            preparedStatement.setString(2,"Answer.testCreate");
            ResultSet resultSet = preparedStatement.executeQuery();
            Answer answerFromDB = new Answer();
            if(resultSet.next()) {
                answerFromDB = new Answer(resultSet.getString("answer"),resultSet.getBoolean("isRightAnswer"),resultSet.getInt("questions_id"));
            }else{
                fail();
            }
            assertEquals(answer.getAnswer(),answerFromDB.getAnswer());
            assertEquals(answer.getQuestionId(),answerFromDB.getQuestionId());
            assertEquals(answer.isRightAnswer(),answerFromDB.isRightAnswer());
        }
    }
    @Test
    void delete() throws SQLException {
        try( MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)){
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(delete);
            answerDao.delete(183);

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(select);
            PreparedStatement preparedStatement = connection.prepareStatement("");
            preparedStatement.setInt(1,183);
            preparedStatement.setString(2,"Answer.testCreate");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) fail();
        }
    }

    @Test
    void findAllByQuestionId() throws SQLException {
        Answer answer1 = new Answer("Right Answer",true,182);
        Answer answer2 = new Answer("Not right answer 1",false,182);
        Answer answer3 = new Answer("Not right answer 2",false,182);
        answer1.setId(459);
        answer2.setId(460);
        answer3.setId(461);
        List<Answer> answerList1 = new ArrayList<>(Arrays.asList(answer1,answer2,answer3));
        List<Answer> answerList2 = new ArrayList<>();
        try( MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)){
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            Connection testConnection = ConnectionPool.getInstance().getConnection();
            Assertions.assertNotNull(testConnection);

            Mockito.when(connection.prepareStatement(anyString())).thenReturn(findAllByQuestionId);
            answerList2=answerDao.findAllByQuestionId(182);
            assertEquals(answerList1,answerList2);
        }
    }


    @Test
    void findById() {
        assertThrows(ServiceException.class, () -> answerDao.findById(1));
    }

    @Test
    void update() {
        assertThrows(ServiceException.class, () -> answerDao.update(null));
    }
}