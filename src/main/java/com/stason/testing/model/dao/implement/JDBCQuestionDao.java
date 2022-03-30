package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.entity.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCQuestionDao implements QuestionDao {
    private static class Query{
        static final String findId = "SELECT id FROM onlinetesting.questions WHERE tests_id=? AND questionNumber=?";
        static final String create = "INSERT INTO onlinetesting.questions (tests_id, questionNumber, question) VALUES (?,?,?)";
        static final String findAllByTestId = "SELECT * FROM onlinetesting.questions WHERE tests_id=?";
        static final String findById = "SELECT * FROM onlinetesting.questions WHERE id=?";
        static final String delete = "DELETE FROM onlinetesting.questions WHERE id=?";
    }
    @Override
    public int findId(Question question) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findId)){
           
            preparedStatement.setInt(1,question.getTestId());
            preparedStatement.setInt(2,question.getQuestionNumber());

           try( ResultSet resultSet = preparedStatement.executeQuery()) {
               if (resultSet.next()) {
                   return resultSet.getInt("id");
               } else {
                   throw new SQLException("Result set is NULL");
               }
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean create(Question question) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.create)){
            preparedStatement.setInt(1,question.getTestId());
            preparedStatement.setInt(2,question.getQuestionNumber());
            preparedStatement.setString(3,question.getTextQuestion());

            if(preparedStatement.execute()) System.out.println("Question #"+question.getQuestionNumber()+"was added in DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public List<Question> findAllByTestId(int id){
        List<Question> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findAllByTestId)){
            preparedStatement.setInt(1,id);
            try( ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Question question = new Question();
                    question.setId(resultSet.getInt("id"));
                    question.setTestId(resultSet.getInt("tests_id"));
                    question.setTextQuestion(resultSet.getString("question"));
                    question.setQuestionNumber(resultSet.getInt("questionNumber"));
                    list.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Question findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findById)){
            preparedStatement.setInt(1,id);
            try( ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    Question question = new Question();
                    question.setId(resultSet.getInt("id"));
                    question.setTestId(resultSet.getInt("tests_id"));
                    question.setTextQuestion(resultSet.getString("question"));
                    question.setQuestionNumber(resultSet.getInt("questionNumber"));
                    return question;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Question> findAll() {
        return null;
    }

    @Override
    public boolean update(Question entity) {
        return false;

    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.delete)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public void close() throws Exception {

    }
}
