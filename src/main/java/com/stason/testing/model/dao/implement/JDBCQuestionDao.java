package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.entity.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCQuestionDao implements QuestionDao {
    private final Connection connection;

    public JDBCQuestionDao(Connection connection) {
        this.connection=connection;
    }

    @Override
    public int findId(Question question) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT id FROM onlinetesting.questions WHERE tests_id=? AND nomerQuestion=?");
            preparedStatement.setInt(1,question.getTestId());
            preparedStatement.setInt(2,question.getNomerQuestion());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("id");
            }else{
                throw  new SQLException("Result set is NULL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void create(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.questions (tests_id, nomerQuestion, question) VALUES (?,?,?)");
            preparedStatement.setInt(1,question.getTestId());
            preparedStatement.setInt(2,question.getNomerQuestion());
            preparedStatement.setString(3,question.getTextQuestion());

            if(preparedStatement.execute()) System.out.println("Question #"+question.getNomerQuestion()+"was added in DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Question findById(int id) {
        return null;
    }

    @Override
    public List<Question> findAll() {
        return null;
    }

    @Override
    public void update(Question entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() throws Exception {

    }
}
