package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.DataBaseException;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.entity.Question;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCQuestionDao implements QuestionDao {
    private static final Logger logger = Logger.getLogger(JDBCQuestionDao.class.getName());

    private static class Query {
        static final String CREATE = "INSERT INTO onlinetesting.questions (tests_id, questionNumber, question) VALUES (?,?,?)";
        static final String FIND_ALL_BY_TEST_ID = "SELECT * FROM onlinetesting.questions WHERE tests_id=?";
        static final String FIND_BY_ID = "SELECT * FROM onlinetesting.questions WHERE id=?";
        static final String DELETE = "DELETE FROM onlinetesting.questions WHERE id=?";
    }

    @Override
    public boolean create(Question question) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.CREATE)) {
            preparedStatement.setInt(1, question.getTestId());
            preparedStatement.setInt(2, question.getQuestionNumber());
            preparedStatement.setString(3, question.getTextQuestion());

            return preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can't create question=" + question.getTextQuestion() + ", because", e);
            throw new DataBaseException("Can't create question=" + question.getTextQuestion());
        }
    }

    @Override
    public List<Question> findAllByTestId(int id) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.FIND_ALL_BY_TEST_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Question> list = new LinkedList<>();
                while (resultSet.next()) {
                    Question question = new Question(resultSet.getInt("id"),resultSet.getInt("tests_id"),resultSet.getInt("questionNumber"),resultSet.getString("question"));
                    list.add(question);
                }
                return list;
            }
        } catch (SQLException e) {
            logger.error("Can't all question for test with id=" + id + ",because", e);
            throw new DataBaseException("Can't all find question for test");
        }
    }

    @Override
    public Question findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Question(resultSet.getInt("id"),resultSet.getInt("tests_id"),resultSet.getInt("questionNumber"),resultSet.getString("question"));
                }
            }
        } catch (SQLException e) {
            logger.error("Can't find question for id=" + id + ",because", e);
            throw new DataBaseException("Can't find question for id=" + id);
        }
        return null;
    }


    @Override
    public boolean update(Question entity) {
        throw new DataBaseException("Can't update question");

    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.DELETE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate()!=0;
        } catch (SQLException e) {
            logger.error("Can't delete question for id=" + id + ",because", e);
            throw new DataBaseException("Can't delete question for id=" + id);
        }
    }

    @Override
    public void close() throws Exception {
    }
}
