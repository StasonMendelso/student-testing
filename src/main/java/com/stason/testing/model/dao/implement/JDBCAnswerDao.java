package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.entity.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCAnswerDao implements AnswerDao {
    private final Connection connection;

    public JDBCAnswerDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Answer answer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.answers (answer, isRightAnswer, questions_id) VALUES (?,?,?)");
            preparedStatement.setString(1, answer.getAnswer());
            preparedStatement.setBoolean(2, answer.isRightAnswer());
            preparedStatement.setInt(3,answer.getQuestionId());

            if(preparedStatement.execute()) System.out.println("Відповідь "+answer.getAnswer()+" добавлена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Answer> findAllByQuestionId(int id) {
        List<Answer> answerList = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.answers WHERE questions_id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Answer answer = new Answer();
                answer.setId(resultSet.getInt("id"));
                answer.setQuestionId(resultSet.getInt("questions_id"));
                answer.setAnswer(resultSet.getString("answer"));
                answer.setRightAnswer(resultSet.getBoolean("isRightAnswer"));
                answerList.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answerList;
    }

    @Override
    public Answer findById(int id) {
        return null;
    }

    @Override
    public List<Answer> findAll() {
        return null;
    }

    @Override
    public void update(Answer entity) {

    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.answers WHERE id=?");
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
