package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JDBCTestDao implements TestDao {

    private final Connection connection;
    private String SQLfindAllTests = "SELECT * FROM onlinetesting.tests";

    public JDBCTestDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int findIdByName(String testName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM onlinetesting.tests where name=?");
            preparedStatement.setString(1,testName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("id");
            }else{
                throw new SQLException("Result set  is NULL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Test> findUnsurpassedTests(int userId) {
        List<Test> list = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL");
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Test test = builtTest(resultSet);
                list.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void create(Test test) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.tests (name, nameOfDiscipline, difficulty, time_minutes, countOfQuestions) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,test.getName());
            preparedStatement.setString(2,test.getNameOfDiscipline());
            preparedStatement.setString(3,test.getDifficulty());
            preparedStatement.setInt(4,test.getTimeMinutes());
            preparedStatement.setInt(5,test.getCountOfQuestions());

            if(preparedStatement.execute()) System.out.println("Test was added to DB");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Test findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.tests WHERE id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Test test = builtTest(resultSet);

                return test;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Test builtTest(ResultSet resultSet) throws SQLException {
        Test test = new Test();
        test.setId(resultSet.getInt("id"));
        test.setName(resultSet.getString("name"));
        test.setNameOfDiscipline(resultSet.getString("nameOfDiscipline"));
        test.setDifficulty(resultSet.getString("difficulty"));
        test.setTimeMinutes(resultSet.getInt("time_minutes"));
        test.setCountOfQuestions(resultSet.getInt("countOfQuestions"));
        return test;
    }

    @Override
    public List<Test> findAll() {
        List<Test> list = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQLfindAllTests);
            while(resultSet.next()){
                Test test = builtTest(resultSet);

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
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.passedtests WHERE test_id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.tests WHERE id=?");
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
