package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JDBCTestDao implements TestDao {

    private final Connection connection;
    private String SQLfindAllTests = "SELECT * FROM onlinetesting.tests";

    public JDBCTestDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int countTestByDiscipline(String discipline) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(1) FROM onlinetesting.tests WHERE tests.nameOfDiscipline=?");
            preparedStatement.setString(1,discipline);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnpassedTests(int index, int paginationParameter, String orderBy, String order) {
        List<Test> list = new LinkedList<>();
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests ORDER BY " + orderBy + " " + order + " limit ?,?;");
            preparedStatement.setInt(1,index);
            preparedStatement.setInt(2,paginationParameter);
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
    public List<Test> findAndPaginateAndSortUnpassedTests(int index, int paginationParameter, String orderBy, String order, String discipline) {
        List<Test> list = new LinkedList<>();
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests WHERE tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;");
            preparedStatement.setString(1,discipline);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,paginationParameter);
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
    public void updatePassedTest(int userId, int testId, double mark) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("update onlinetesting.passedtests SET mark=? WHERE user_id=? AND test_id=?;");
            preparedStatement.setDouble(1,mark);
            preparedStatement.setInt(2,userId);
            preparedStatement.setInt(3,testId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int countAllTest() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM onlinetesting.tests");
            if(resultSet.next()){
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Test> findAndPaginateAllTests(int index, int paginationParameter) {
        List<Test> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.`tests` limit ?,?;");
            preparedStatement.setInt(1,index);
            preparedStatement.setInt(2,paginationParameter);
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
    public int countPaginateAndSortUnpassedTests(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(" SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL;");
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int countPaginateAndSortUnpassedTests(int userId, String discipline) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(" SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id AND tests.nameOfDiscipline=? IS NULL;");
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,discipline);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> findAllDisciplines(){
        List<String> disciplinesList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT onlinetesting.tests.nameOfDiscipline FROM onlinetesting.tests;");
            while(resultSet.next()){
                disciplinesList.add(resultSet.getString("nameOfDiscipline"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplinesList;
    }

    @Override
    public int countUnpassedTestByUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(" SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL;");
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int countPassedTestByUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(1) FROM onlinetesting.`passedtests` WHERE user_id=?");
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnpassedTests(int userId, int index, int paginationParameter, String orderBy, String order) {
        List<Test> list = new LinkedList<>();
        try {
            PreparedStatement preparedStatement;

                preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL ORDER BY " + orderBy + " " + order + " limit ?,?;");

            preparedStatement.setInt(1,userId);

            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,paginationParameter);
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
    public List<Test> findAndPaginateAndSortUnpassedTests(int userId, int index, int paginationParameter, String orderBy, String order, String discipline) {
        List<Test> list = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id  IS NULL AND tests.nameOfDiscipline=? ORDER BY " + orderBy +" " + order + " limit ?,?;");

            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,discipline);
            preparedStatement.setInt(3,index);
            preparedStatement.setInt(4,paginationParameter);
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
    public List<Test> findAndPaginatePassedTests(int userId, int index, int paginationParameter) {
        List<Test> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.`tests`, (SELECT mark, test_id FROM onlinetesting.`passedtests` WHERE user_id=?) as `passedtests` WHERE `tests`.id = `passedtests`.test_id limit ?,?;");
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,paginationParameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Test test = builtTest(resultSet);
                test.setMark(resultSet.getDouble("mark"));
                list.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public List<Test> findAndPaginateUnsurpassedTests(int userId, int index, int paginationParameter) {
        List<Test> list = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL limit ?,?;");
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,paginationParameter);
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
    public void addPassedTest(int userId, int testId, double mark) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.passedtests (user_id, test_id, mark) VALUES (?,?,?)");
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,testId);
            preparedStatement.setDouble(3,mark);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
    public List<Test> findUnPassedTests(int userId) {
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
    public List<Test> findPassedTests(int userId) {
        List<Test> list = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.`tests`, (SELECT mark, test_id FROM onlinetesting.`passedtests` WHERE user_id=?) as `passedtests` WHERE `tests`.id = `passedtests`.test_id;");
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Test test = builtTest(resultSet);
                test.setMark(resultSet.getDouble("mark"));
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
