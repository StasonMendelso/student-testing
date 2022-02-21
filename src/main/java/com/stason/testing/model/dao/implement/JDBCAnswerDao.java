package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.entity.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    }

    @Override
    public void close() throws Exception {

    }
}
