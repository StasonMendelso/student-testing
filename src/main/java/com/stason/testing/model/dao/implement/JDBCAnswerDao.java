package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.DataBaseException;
import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.entity.Answer;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCAnswerDao implements AnswerDao {
    private static final Logger logger = Logger.getLogger(JDBCAnswerDao.class.getName());

    private static class Query {
        static final String CREATE = "INSERT INTO onlinetesting.answers (answer, isRightAnswer, questions_id) VALUES (?,?,?)";
        static final String FIND_ALL_BY_QUESTION_ID = "SELECT * FROM onlinetesting.answers WHERE questions_id=?";
        static final String DELETE = "DELETE FROM onlinetesting.answers WHERE id=?";

    }

    @Override
    public boolean create(Answer answer) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.CREATE)) {
            preparedStatement.setString(1, answer.getAnswer());
            preparedStatement.setBoolean(2, answer.isRightAnswer());
            preparedStatement.setInt(3, answer.getQuestionId());

            return preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can't create Answer for question=" + answer.getQuestionId() + ", because", e);
            throw new DataBaseException("Can't create Answer");
        }
    }

    @Override
    public List<Answer> findAllByQuestionId(int id) {
        List<Answer> answerList = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.FIND_ALL_BY_QUESTION_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Answer answer = new Answer(resultSet.getInt("id"),
                            resultSet.getString("answer"),
                            resultSet.getBoolean("isRightAnswer"),
                            resultSet.getInt("questions_id"));
                    answerList.add(answer);
                }
            }

        } catch (SQLException e) {
            logger.error("Can't find all Answers for question=" + id + ", because", e);
            throw new DataBaseException("Can't find all answers for questions");
        }

        return answerList;
    }


    @Override
    public boolean delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.DELETE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Can't delete Answer for question=" + id + ", because", e);
            throw new DataBaseException("Can't delete Answer");
        }
    }
    @Override
    public Answer findById(int id) {
        throw new DataBaseException("Can't find Answer by id");
    }

    @Override
    public boolean update(Answer entity) {
        throw new DataBaseException("Can't update answer");
    }

    @Override
    public void close() throws Exception {

    }
}
