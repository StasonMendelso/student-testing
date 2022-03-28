package com.stason.testing.model.dao.implement;

import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JDBCTestDao implements TestDao {
    private static class Query{
        static final String deletePassedTestById = "DELETE FROM onlinetesting.passedtests WHERE test_id=?";
        static final String countTestByDiscipline = "SELECT COUNT(1) FROM onlinetesting.tests WHERE tests.nameOfDiscipline=?";
        static final String findAndPaginateAndSortUnsurpassedTests = "";
        static final String findAndPaginateAndSortByDisciplineUnsurpassedTests = "";
        static final String updatePassedTest = "update onlinetesting.passedtests SET mark=? WHERE user_id=? AND test_id=?;";
        static final String countAllTest = "SELECT COUNT(1) FROM onlinetesting.tests";
        static final String findAndPaginateAllTests = "SELECT * FROM onlinetesting.`tests` limit ?,?;";
        static final String countPaginateAndSortUnsurpassedTests = " SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL;";
        static final String countPaginateAndSortByDisciplineUnsurpassedTests = " SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id AND tests.nameOfDiscipline=? IS NULL;";
        static final String findAllDisciplines = "SELECT DISTINCT onlinetesting.tests.nameOfDiscipline FROM onlinetesting.tests;";
        static final String countUnsurpassedTestByUser = "SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL;";
        static final String countPassedTestByUser = "SELECT COUNT(1) FROM onlinetesting.`passedtests` WHERE user_id=?";
        static final String findAndPaginateAndSortUnsurpassedTestsByUserId = "";
        static final String findAndPaginateAndSortByDisciplineUnsurpassedTestsByUserId = "SELECT * FROM onlinetesting.`tests`, (SELECT mark, test_id FROM onlinetesting.`passedtests` WHERE user_id=?) as `passedtests` WHERE `tests`.id = `passedtests`.test_id limit ?,?;";
        static final String findAndPaginateUnsurpassedTests = " SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL limit ?,?;";
        static final String addPassedTest = "INSERT INTO onlinetesting.passedtests (user_id, test_id, mark) VALUES (?,?,?)";
        static final String findIdByName = "SELECT id FROM onlinetesting.tests where name=?";
        static final String create = "INSERT INTO onlinetesting.tests (name, nameOfDiscipline, difficulty, time_minutes, countOfQuestions) VALUES (?,?,?,?,?)";
        static final String findById = "SELECT * FROM onlinetesting.tests WHERE id=?";
        static final String findAll = "SELECT * FROM onlinetesting.tests";
        static final String deletePassedTest = "DELETE FROM onlinetesting.passedtests WHERE test_id=?";
        static final String delete = "DELETE FROM onlinetesting.tests WHERE id=?";

    }

    @Override
    public void deletePassedTestById(int testId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement =connection.prepareStatement(Query.deletePassedTestById)){
            preparedStatement.setInt(1,testId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int countTestByDiscipline(String discipline) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countTestByDiscipline)){
            preparedStatement.setString(1,discipline);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt("COUNT(1)");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnsurpassedTests(int index, int paginationParameter, String orderBy, String order) {
        List<Test> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests ORDER BY " + orderBy + " " + order + " limit ?,?;")){
            preparedStatement.setInt(1,index);
            preparedStatement.setInt(2,paginationParameter);
            try( ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnsurpassedTests(int index, int paginationParameter, String orderBy, String order, String discipline) {
        List<Test> list = new LinkedList<>();
        try  (Connection connection = ConnectionPool.getInstance().getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests WHERE tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;")){
            preparedStatement.setString(1,discipline);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,paginationParameter);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updatePassedTest(int userId, int testId, double mark) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.updatePassedTest)){
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.countAllTest)){

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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findAndPaginateAllTests)){
            preparedStatement.setInt(1,index);
            preparedStatement.setInt(2,paginationParameter);
            try(  ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countPaginateAndSortUnsurpassedTests(int userId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countPaginateAndSortUnsurpassedTests)){
            preparedStatement.setInt(1,userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt("COUNT(1)");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int countPaginateAndSortUnsurpassedTests(int userId, String discipline) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countPaginateAndSortByDisciplineUnsurpassedTests)){
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,discipline);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt("COUNT(1)");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> findAllDisciplines(){
        List<String> disciplinesList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.findAllDisciplines)){

            while(resultSet.next()){
                disciplinesList.add(resultSet.getString("nameOfDiscipline"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplinesList;
    }

    @Override
    public int countUnsurpassedTestByUser(int userId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countUnsurpassedTestByUser)){
            preparedStatement.setInt(1,userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt("COUNT(1)");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int countPassedTestByUser(int userId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countPassedTestByUser)){
            preparedStatement.setInt(1,userId);
            try( ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt("COUNT(1)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnsurpassedTests(int userId, int index, int paginationParameter, String orderBy, String order) {
        List<Test> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL ORDER BY " + orderBy + " " + order + " limit ?,?;")){

            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,paginationParameter);
            try(ResultSet resultSet = preparedStatement.executeQuery()){

                while(resultSet.next()){
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnsurpassedTests(int userId, int index, int paginationParameter, String orderBy, String order, String discipline) {
        List<Test> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id  IS NULL AND tests.nameOfDiscipline=? ORDER BY " + orderBy +" " + order + " limit ?,?;")){

            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,discipline);
            preparedStatement.setInt(3,index);
            preparedStatement.setInt(4,paginationParameter);
            try( ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Test> findAndPaginatePassedTests(int userId, int index, int paginationParameter) {
        List<Test> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findAndPaginateAndSortByDisciplineUnsurpassedTestsByUserId)){
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,paginationParameter);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Test test = builtTest(resultSet);
                    test.setMark(resultSet.getDouble("mark"));
                    list.add(test);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public List<Test> findAndPaginateUnsurpassedTests(int userId, int index, int paginationParameter) {
        List<Test> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findAndPaginateUnsurpassedTests)){
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,paginationParameter);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public void addPassedTest(int userId, int testId, double mark) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.addPassedTest)){
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findIdByName)){
            preparedStatement.setString(1,testName);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    return resultSet.getInt("id");
                }else{
                    throw new SQLException("Result set  is NULL");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public void create(Test test) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.create)){
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findById)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {

                return builtTest(resultSet);
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.findAll)){


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
    public void deletePassedTest(int id){
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.deletePassedTest)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.delete)){
            deletePassedTest(id);
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
