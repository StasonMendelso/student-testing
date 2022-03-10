package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.entity.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
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
            preparedStatement = connection.prepareStatement("SELECT id FROM onlinetesting.questions WHERE tests_id=? AND questionNumber=?");
            preparedStatement.setInt(1,question.getTestId());
            preparedStatement.setInt(2,question.getQuestionNumber());

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
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.questions (tests_id, questionNumber, question) VALUES (?,?,?)");
            preparedStatement.setInt(1,question.getTestId());
            preparedStatement.setInt(2,question.getQuestionNumber());
            preparedStatement.setString(3,question.getTextQuestion());

            if(preparedStatement.execute()) System.out.println("Question #"+question.getQuestionNumber()+"was added in DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Question> findAllByTestId(int id){
        List<Question> list = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.questions WHERE tests_id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Question question = new Question();
                question.setId(resultSet.getInt("id"));
                question.setTestId(resultSet.getInt("tests_id"));
                question.setTextQuestion(resultSet.getString("question"));
                question.setQuestionNumber(resultSet.getInt("questionNumber"));
                list.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Question findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.questions WHERE id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Question question = new Question();
                question.setId(resultSet.getInt("id"));
                question.setTestId(resultSet.getInt("tests_id"));
                question.setTextQuestion(resultSet.getString("question"));
                question.setQuestionNumber(resultSet.getInt("questionNumber"));
                return question;
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
    public void update(Question entity) {

    }

    @Override
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.questions WHERE id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {

    }
}
