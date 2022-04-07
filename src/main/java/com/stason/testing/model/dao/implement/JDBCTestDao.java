package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.DataBaseException;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JDBCTestDao implements TestDao {
    private static final Logger logger = Logger.getLogger(JDBCTestDao.class.getName());

    private static class Query {
        static final String deletePassedTestById = "DELETE FROM onlinetesting.passedtests WHERE test_id=?";
        static final String countTestByDiscipline = "SELECT COUNT(1) FROM onlinetesting.tests WHERE tests.nameOfDiscipline=?";
        static final String updatePassedTest = "update onlinetesting.passedtests SET mark=? WHERE user_id=? AND test_id=?;";
        static final String countAllTest = "SELECT COUNT(1) FROM onlinetesting.tests";
        static final String findAndPaginateAllTests = "SELECT * FROM onlinetesting.`tests` limit ?,?;";
        static final String countPaginateAndSortUnsurpassedTests = " SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL;";
        static final String countPaginateAndSortByDisciplineUnsurpassedTests = " SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id AND tests.nameOfDiscipline=? IS NULL;";
        static final String findAllDisciplines = "SELECT DISTINCT onlinetesting.tests.nameOfDiscipline FROM onlinetesting.tests;";
        static final String countUnsurpassedTestByUser = "SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL;";
        static final String countPassedTestByUser = "SELECT COUNT(1) FROM onlinetesting.`passedtests` WHERE user_id=?";
        static final String findAndPaginateAndSortByDisciplineUnsurpassedTestsByUserId = "SELECT * FROM onlinetesting.`tests`, (SELECT mark, test_id FROM onlinetesting.`passedtests` WHERE user_id=?) as `passedtests` WHERE `tests`.id = `passedtests`.test_id limit ?,?;";
        static final String findAndPaginateUnsurpassedTests = " SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL limit ?,?;";
        static final String addPassedTest = "INSERT INTO onlinetesting.passedtests (user_id, test_id, mark) VALUES (?,?,?)";
        static final String findIdByName = "SELECT id FROM onlinetesting.tests where name=?";
        static final String create = "INSERT INTO onlinetesting.tests (name, nameOfDiscipline, difficulty, time_minutes, countOfQuestions) VALUES (?,?,?,?,?)";
        static final String findById = "SELECT * FROM onlinetesting.tests WHERE id=?";
        static final String deletePassedTest = "DELETE FROM onlinetesting.passedtests WHERE test_id=?";
        static final String delete = "DELETE FROM onlinetesting.tests WHERE id=?";

    }

    @Override
    public void deletePassedTestForUser(int testId, int userId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.passedtests WHERE test_id=? AND user_id=?;")) {
            preparedStatement.setInt(1, testId);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Can't delete passed test (test_id=" + testId + ") for user (user_id=" + userId + "), because", ex);
            throw new DataBaseException("Can't delete passed test for user");
        }
    }

    @Override
    public boolean deletePassedTestById(int testId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.deletePassedTestById)) {
            preparedStatement.setInt(1, testId);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Can't create new user" + testId + ", because", e);
            throw new DataBaseException("Can't create new user");
        }

    }

    @Override
    public int countTestByDiscipline(String discipline) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countTestByDiscipline)) {
            preparedStatement.setString(1, discipline);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("COUNT(1)");
                }
            }

        } catch (SQLException e) {
            logger.error("Can't count tests by discipline=" + discipline + ", because", e);
            throw new DataBaseException("Can't count tests by discipline=" + discipline);
        }
        return 0;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnsurpassedTests(int index, int paginationParameter, String orderBy, String order) {
        List<Test> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests ORDER BY " + orderBy + " " + order + " limit ?,?;")) {
            preparedStatement.setInt(1, index);
            preparedStatement.setInt(2, paginationParameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }
        } catch (SQLException e) {
            logger.error("Can't find or paginate or sort unsurpassed tests, because", e);
            throw new DataBaseException("Can't find or paginate or sort unsurpassed tests");
        }
        return list;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnsurpassedTests(int index, int paginationParameter, String orderBy, String order, String discipline) {
        List<Test> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests WHERE tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;")) {
            preparedStatement.setString(1, discipline);
            preparedStatement.setInt(2, index);
            preparedStatement.setInt(3, paginationParameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }

        } catch (SQLException e) {
            logger.error("Can't find or paginate or sort unsurpassed tests by discipline, because", e);
            throw new DataBaseException("Can't find or paginate or sort unsurpassed tests by discipline");
        }
        return list;
    }

    @Override
    public boolean updatePassedTest(int userId, int testId, double mark) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.updatePassedTest)) {
            preparedStatement.setDouble(1, mark);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, testId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can't update passed test" + testId + " for user" + userId + " with mark" + mark + ", because", e);
            throw new DataBaseException("Can't update passed test");
        }
        return false;

    }

    @Override
    public int countAllTest() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.countAllTest)) {

            if (resultSet.next()) {
                return resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException e) {
            logger.error("Can't count all tests, because", e);
            throw new DataBaseException("Can't count all tests");
        }
        return 0;
    }

    @Override
    public List<Test> findAndPaginateAllTests(int index, int paginationParameter) {
        List<Test> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findAndPaginateAllTests)) {
            preparedStatement.setInt(1, index);
            preparedStatement.setInt(2, paginationParameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }


        } catch (SQLException e) {
            logger.error("Can't find or paginate all tests, because", e);
            throw new DataBaseException("Can't find or paginate all tests");
        }
        return list;
    }

    @Override
    public int countPaginateAndSortUnsurpassedTests(int userId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countPaginateAndSortUnsurpassedTests)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("COUNT(1)");
                }
            }

        } catch (SQLException e) {
            logger.error("Can't count for unsurpassed tests, because", e);
            throw new DataBaseException("Can't count for unsurpassed tests");
        }
        return 0;
    }

    @Override
    public int countPaginateAndSortUnsurpassedTests(int userId, String discipline) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countPaginateAndSortByDisciplineUnsurpassedTests)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, discipline);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("COUNT(1)");
                }
            }

        } catch (SQLException e) {
            logger.error("Can't count for unsurpassed tests (sorted by discipline=" + discipline + "), because", e);
            throw new DataBaseException("Can't count for unsurpassed tests (sorted by discipline=" + discipline);
        }
        return 0;
    }

    public List<String> findAllDisciplines() {
        List<String> disciplinesList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.findAllDisciplines)) {

            while (resultSet.next()) {
                disciplinesList.add(resultSet.getString("nameOfDiscipline"));
            }
        } catch (SQLException e) {
            logger.error("Can't find all disciplines, because", e);
            throw new DataBaseException("Can't find all disciplines");
        }
        return disciplinesList;
    }

    @Override
    public int countUnsurpassedTestByUser(int userId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countUnsurpassedTestByUser)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("COUNT(1)");
                }
            }


        } catch (SQLException e) {
            logger.error("Can't count unsurpassed test by user=" + userId + ", because", e);
            throw new DataBaseException("Can't count unsurpassed test by user");
        }
        return 0;
    }

    @Override
    public int countPassedTestByUser(int userId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.countPassedTestByUser)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("COUNT(1)");
                }
            }
        } catch (SQLException e) {
            logger.error("Can't count passed test by user=" + userId + ", because", e);
            throw new DataBaseException("Can't count passed test by user");
        }
        return 0;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnsurpassedTests(int userId, int index, int paginationParameter, String orderBy, String order) {
        List<Test> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL ORDER BY " + orderBy + " " + order + " limit ?,?;")) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, index);
            preparedStatement.setInt(3, paginationParameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }

        } catch (SQLException e) {
            logger.error("Can't find, paginate or sort unsurpassed test by user=" + userId + ", because", e);
            throw new DataBaseException("Can't find, paginate or sort unsurpassed test by user");
        }
        return list;
    }

    @Override
    public List<Test> findAndPaginateAndSortUnsurpassedTests(int userId, int index, int paginationParameter, String orderBy, String order, String discipline) {
        List<Test> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id  IS NULL AND tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;")) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, discipline);
            preparedStatement.setInt(3, index);
            preparedStatement.setInt(4, paginationParameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }
        } catch (SQLException e) {
            logger.error("Can't find, paginate or sort(by discipline=" + discipline + ") unsurpassed test by user=" + userId + ", because", e);
            throw new DataBaseException("Can't find, paginate or sort(by discipline=" + discipline + ") unsurpassed test by user=" + userId);
        }
        return list;
    }

    @Override
    public List<Test> findAndPaginatePassedTests(int userId, int index, int paginationParameter) {
        List<Test> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findAndPaginateAndSortByDisciplineUnsurpassedTestsByUserId)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, index);
            preparedStatement.setInt(3, paginationParameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Test test = builtTest(resultSet);
                    test.setMark(resultSet.getDouble("mark"));
                    list.add(test);
                }
            }

        } catch (SQLException e) {
            logger.error("Can't find, paginate passed tests by user=" + userId + ", because", e);
            throw new DataBaseException("Can't find, paginate passed tests by user=" + userId);
        }
        return list;
    }

    @Override
    public List<Test> findAndPaginateUnsurpassedTests(int userId, int index, int paginationParameter) {
        List<Test> list = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findAndPaginateUnsurpassedTests)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, index);
            preparedStatement.setInt(3, paginationParameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Test test = builtTest(resultSet);
                    list.add(test);
                }
            }
        } catch (SQLException e) {
            logger.error("Can't find, paginate unsurpassed tests by user=" + userId + ", because", e);
            throw new DataBaseException("Can't find, paginate unsurpassed tests by user=" + userId);
        }
        return list;
    }

    @Override
    public boolean addPassedTest(int userId, int testId, double mark) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.addPassedTest)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, testId);
            preparedStatement.setDouble(3, mark);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can't add passed test=" + testId + " for user=" + userId + ", because", e);
            throw new DataBaseException("Can't add paginate passed test=" + testId + " for user=" + userId);
        }
        return false;

    }

    @Override
    public int findIdByName(String testName) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findIdByName)) {
            preparedStatement.setString(1, testName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Can't find id for test=" + testName + ", because", e);
            throw new DataBaseException("Can't find id for test=" + testName);
        }
    }


    @Override
    public boolean create(Test test) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(Query.create);
            connection.setAutoCommit(false);
            preparedStatement.setString(1, test.getName());
            preparedStatement.setString(2, test.getNameOfDiscipline());
            preparedStatement.setInt(3, test.getDifficulty());
            preparedStatement.setInt(4, test.getTimeMinutes());
            preparedStatement.setInt(5, test.getCountOfQuestions());
            preparedStatement.execute();
            int testId = -1;
            preparedStatement = connection.prepareStatement(Query.findIdByName);
            preparedStatement.setString(1, test.getName());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    testId = resultSet.getInt("id");
                }
            }
            //
            int i = 1;
            for (Question question : test.getQuestions()) {
                preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.questions (tests_id, questionNumber, question) VALUES (?,?,?)");
                preparedStatement.setInt(1, testId);
                preparedStatement.setInt(2, i);
                preparedStatement.setString(3, question.getTextQuestion());
                preparedStatement.execute();
                //добавляємо до кожного вопроса відповіді в БД
                preparedStatement = connection.prepareStatement("SELECT id FROM onlinetesting.questions WHERE tests_id=? AND questionNumber=?");
                preparedStatement.setInt(1, testId);
                preparedStatement.setInt(2, i++);
                int questionId = -1;
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) questionId = resultSet.getInt("id");
                }
                preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.answers (answer, isRightAnswer, questions_id) VALUES (?,?,?)");
                for (Answer answer : question.getAnswers()) {
                    answer.setQuestionId(questionId);
                    preparedStatement.setString(1, answer.getAnswer());
                    preparedStatement.setBoolean(2, answer.isRightAnswer());
                    preparedStatement.setInt(3, answer.getQuestionId());
                    preparedStatement.execute();
                }
            }

            preparedStatement.close();
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            logger.error("Failed to add new test in DB ", e);
            throw new DataBaseException("Failed to add new test in DB =" + test.getName());
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public Test findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.findById)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                return builtTest(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Can't find test by id=" + id, e);
            throw new DataBaseException("Can't find test by id=" + id);
        }

        return null;
    }

    private Test builtTest(ResultSet resultSet) throws SQLException {
        Test test = new Test();
        test.setId(resultSet.getInt("id"));
        test.setName(resultSet.getString("name"));
        test.setNameOfDiscipline(resultSet.getString("nameOfDiscipline"));
        test.setDifficulty(resultSet.getInt("difficulty"));
        test.setTimeMinutes(resultSet.getInt("time_minutes"));
        test.setCountOfQuestions(resultSet.getInt("countOfQuestions"));
        return test;
    }


    @Override
    public boolean update(Test test) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            //UPDATE TEST INFORMATION
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE onlinetesting.tests SET name=?, nameOfDiscipline=?,difficulty=?,time_minutes=?,countOfQuestions=? WHERE id=?");
            preparedStatement.setString(1, test.getName());
            preparedStatement.setString(2, test.getNameOfDiscipline());
            preparedStatement.setInt(3, test.getDifficulty());
            preparedStatement.setInt(4, test.getTimeMinutes());
            preparedStatement.setInt(5, test.getCountOfQuestions());
            preparedStatement.setInt(6, test.getId());
            preparedStatement.executeUpdate();
            //DELETE ANSWERS FOR EVERY QUESTION IN THIS TEST
            preparedStatement = connection.prepareStatement("SELECT id FROM onlinetesting.questions WHERE tests_id=?");
            preparedStatement.setInt(1, test.getId());
            List<Integer> questionsIdFromDBForThisTest = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    questionsIdFromDBForThisTest.add(resultSet.getInt("id"));
                }
            }

            for (Integer questionId : questionsIdFromDBForThisTest) {
                preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.answers WHERE questions_id=?");
                preparedStatement.setInt(1, questionId);
                preparedStatement.executeUpdate();

            }
            //DELETE QUESTIONS FOR THIS TEST
            preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.questions WHERE tests_id=?");
            preparedStatement.setInt(1, test.getId());
            preparedStatement.executeUpdate();
            //INSERT NEW QUESTION FOR THIS TEST
            List<Question> questionList = test.getQuestions();
            for (Question question : questionList) {
                preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.questions (tests_id, question, questionNumber) VALUES (?,?,?)");
                preparedStatement.setInt(1, test.getId());
                preparedStatement.setString(2, question.getTextQuestion());
                preparedStatement.setInt(3, question.getQuestionNumber());
                preparedStatement.execute();
                //INSERT ANSWERS FOR THIS QUESTION
                preparedStatement = connection.prepareStatement("SELECT id FROM onlinetesting.questions WHERE questionNumber=? AND tests_id=?");
                preparedStatement.setInt(1, question.getQuestionNumber());
                preparedStatement.setInt(2, test.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                int questionId = -1;
                if (resultSet.next()) {
                    questionId = resultSet.getInt("id");
                }
                List<Answer> answerList = question.getAnswers();
                for (Answer answer : answerList) {
                    preparedStatement = connection.prepareStatement("INSERT INTO onlinetesting.answers (answer, isRightAnswer, questions_id) VALUES (?,?,?)");
                    preparedStatement.setString(1, answer.getAnswer());
                    preparedStatement.setBoolean(2, answer.isRightAnswer());
                    preparedStatement.setInt(3, questionId);
                    preparedStatement.execute();
                }
            }
            //DELETE PASSED TEST FOR EVERY USER, BECAUSE TEST WAS EDITED
            preparedStatement = connection.prepareStatement(Query.deletePassedTest);
            preparedStatement.setInt(1, test.getId());
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();

            } catch (SQLException ex) {
                logger.error("Can't rollback in update()");
            }
            logger.error("Error update()");
            throw new DataBaseException("Error update()", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error close connection");
                }
            }
        }
        return false;
    }


    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            //DELETE ANSWERS FOR EVERY QUESTION IN THIS TEST
            preparedStatement = connection.prepareStatement("SELECT id FROM onlinetesting.questions WHERE tests_id=?");
            preparedStatement.setInt(1, id);
            List<Integer> questionsIdFromDBForThisTest = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    questionsIdFromDBForThisTest.add(resultSet.getInt("id"));
                }
            }

            for (Integer questionId : questionsIdFromDBForThisTest) {
                preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.answers WHERE questions_id=?");
                preparedStatement.setInt(1, questionId);
                preparedStatement.executeUpdate();

            }
            //DELETE QUESTIONS FOR THIS TEST
            preparedStatement = connection.prepareStatement("DELETE FROM onlinetesting.questions WHERE tests_id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            //DELETE PASSED TEST FOR EVERY USER, BECAUSE TEST WAS EDITED
            preparedStatement = connection.prepareStatement(Query.deletePassedTest);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            //DELETE TEST
            preparedStatement = connection.prepareStatement(Query.delete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Can't rollback in delete()", ex);
            }
            logger.error("Can't delete test id=" + id + ", because", e);
            throw new DataBaseException("Can't delete test id=" + id);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("Can't close connection or preparedStatement in delete()", e);
            }
        }
        return false;

    }

    @Override
    public void close() throws Exception {

    }
}
