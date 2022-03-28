package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.entity.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCAnswerDao implements AnswerDao {
    private static class Query{
        static final String create = "INSERT INTO onlinetesting.answers (answer, isRightAnswer, questions_id) VALUES (?,?,?)";
        static final String findAllByQuestionId = "SELECT * FROM onlinetesting.answers WHERE questions_id=?";
        static final String delete = "DELETE FROM onlinetesting.answers WHERE id=?";

    }
    @Override
    public void create(Answer answer) {
        try  (Connection connection = ConnectionPool.getInstance().getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(Query.create)){
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findAllByQuestionId)){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Answer answer = new Answer();
                    answer.setId(resultSet.getInt("id"));
                    answer.setQuestionId(resultSet.getInt("questions_id"));
                    answer.setAnswer(resultSet.getString("answer"));
                    answer.setRightAnswer(resultSet.getBoolean("isRightAnswer"));
                    answerList.add(answer);
                }
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.delete)){
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
