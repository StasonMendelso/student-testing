package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class JDBCTestDao implements TestDao {

    private final Connection connection;
    private String SQLfindAllTests = "SELECT * FROM onlinetesting.tests";

    public JDBCTestDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Test entity) {

    }

    @Override
    public Test findById(int id) {
        return null;
    }

    @Override
    public List<Test> findAll() {
        List<Test> list = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQLfindAllTests);
            while(resultSet.next()){
                Test test = new Test();
                test.setId(resultSet.getInt("id"));
                test.setName(resultSet.getString("name"));
                test.setNameOfDiscipline(resultSet.getString("nameOfDiscipline"));
                test.setDifficulty(resultSet.getString("difficulty"));
                test.setTimeMinutes(resultSet.getInt("time_minutes"));
                test.setCountOfQuestions(resultSet.getInt("countOfQuestions"));

                list.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Test entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() throws Exception {

    }
}
