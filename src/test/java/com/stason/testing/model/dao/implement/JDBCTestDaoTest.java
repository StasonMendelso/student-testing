package com.stason.testing.model.dao.implement;

import com.stason.testing.controller.exceptions.DataBaseException;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JDBCTestDaoTest {
    private final JDBCTestDao testDao = new JDBCTestDao();
    MockedStatic<ConnectionPool> cp;
    @Mock
    ConnectionPool connectionPool;
    Connection mockConnection = mock(Connection.class);
    PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    @BeforeEach
    public void setUp() throws SQLException {
        cp = mockStatic(ConnectionPool.class);
        cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
        when(connectionPool.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        Connection testConnection = ConnectionPool.getInstance().getConnection();
        Assertions.assertNotNull(testConnection);
    }

    @AfterEach
    public void tearDown() {
        cp.close();
    }

    private static class Query {
        static final String DELETE_PASSED_TEST_FOR_USER = "DELETE FROM onlinetesting.passedtests WHERE test_id=? AND user_id=?;";
        static final String COUNT_TEST_BY_DISCIPLINE = "SELECT COUNT(1) FROM onlinetesting.tests WHERE tests.nameOfDiscipline=?";
        static final String UPDATE_PASSED_TEST = "update onlinetesting.passedtests SET mark=? WHERE user_id=? AND test_id=?;";
        static final String COUNT_ALL_TEST = "SELECT COUNT(1) FROM onlinetesting.tests";
        static final String FIND_AND_PAGINATE_ALL_TESTS = "SELECT * FROM onlinetesting.`tests` limit ?,?;";
        static final String COUNT_PAGINATE_AND_SORT_UNSURPASSED_TESTS = " SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL;";
        static final String COUNT_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS = " SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id AND tests.nameOfDiscipline=? IS NULL;";
        static final String FIND_ALL_DISCIPLINES = "SELECT DISTINCT onlinetesting.tests.nameOfDiscipline FROM onlinetesting.tests;";
        static final String COUNT_UNSURPASSED_TEST_BY_USER = "SELECT COUNT(1) FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL;";
        static final String COUNT_PASSED_TEST_BY_USER = "SELECT COUNT(1) FROM onlinetesting.`passedtests` WHERE user_id=?";
        static final String FIND_AND_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS_BY_USER_ID = "SELECT * FROM onlinetesting.`tests`, (SELECT mark, test_id FROM onlinetesting.`passedtests` WHERE user_id=?) as `passedtests` WHERE `tests`.id = `passedtests`.test_id limit ?,?;";
        static final String FIND_AND_PAGINATE_UNSURPASSED_TESTS = " SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL limit ?,?;";
        static final String ADD_PASSED_TEST = "INSERT INTO onlinetesting.passedtests (user_id, test_id, mark) VALUES (?,?,?)";
        static final String FIND_ID_BY_NAME = "SELECT id FROM onlinetesting.tests where name=?";
        static final String CREATE = "INSERT INTO onlinetesting.tests (name, nameOfDiscipline, difficulty, time_minutes, countOfQuestions) VALUES (?,?,?,?,?)";
        static final String FIND_BY_ID = "SELECT * FROM onlinetesting.tests WHERE id=?";
        static final String CREATE_QUESTION = "INSERT INTO onlinetesting.questions (tests_id, questionNumber, question) VALUES (?,?,?)";
        static final String FIND_QUESTION_ID = "SELECT id FROM onlinetesting.questions WHERE tests_id=? AND questionNumber=?";
        static final String CREATE_ANSWER = "INSERT INTO onlinetesting.answers (answer, isRightAnswer, questions_id) VALUES (?,?,?)";
        static final String DELETE_PASSED_TEST = "DELETE FROM onlinetesting.passedtests WHERE test_id=?";
        static final String UPDATE = "UPDATE onlinetesting.tests SET name=?, nameOfDiscipline=?,difficulty=?,time_minutes=?,countOfQuestions=? WHERE id=?";
        static final String FIND_ALL_QUESTIONS_ID_FOR_TEST = "SELECT id FROM onlinetesting.questions WHERE tests_id=?";
        static final String DELETE_ANSWERS = "DELETE FROM onlinetesting.answers WHERE questions_id=?";
        static final String DELETE_QUESTIONS = "DELETE FROM onlinetesting.questions WHERE tests_id=?";
        static final String DELETE = "DELETE FROM onlinetesting.tests WHERE id=?";

    }

    @Test
    void testDeletePassedTestForUser() throws SQLException {
        testDao.deletePassedTestForUser(1, 1);
        verify(mockConnection, times(1)).prepareStatement(Query.DELETE_PASSED_TEST_FOR_USER);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeQuery();
    }

    @Test
    void testDeletePassedTestForUserError() throws SQLException {
        when(mockConnection.prepareStatement(Query.DELETE_PASSED_TEST_FOR_USER)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.deletePassedTestForUser(1, 1));
        assertEquals("Can't delete passed test for user", thrown.getMessage());
    }

    @Test
    void testCountTestByDisciplineZero() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(0, testDao.countTestByDiscipline("discipline"));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_TEST_BY_DISCIPLINE);
        verify(mockPreparedStatement, times(1)).setString(1, "discipline");
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    void testCountTestByDisciplineNotZero() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("COUNT(1)")).thenReturn(1);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(1, testDao.countTestByDiscipline("discipline"));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_TEST_BY_DISCIPLINE);
        verify(mockPreparedStatement, times(1)).setString(1, "discipline");
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(1)).getInt("COUNT(1)");
    }

    @Test
    void testCountTestByDisciplineError() throws SQLException {
        when(mockConnection.prepareStatement(Query.COUNT_TEST_BY_DISCIPLINE)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.countTestByDiscipline("Math"));
        assertEquals("Can't count tests by discipline=Math", thrown.getMessage());
    }

    @Test
    void testFindAndPaginateAndSortAllTestsEmpty() throws SQLException {
        String orderBy = "orderby", order = "asc";
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.EMPTY_LIST, testDao.findAndPaginateAndSortAllTests(1, 1, orderBy, order));
        verify(mockConnection, times(1)).prepareStatement("SELECT * FROM onlinetesting.tests ORDER BY " + orderBy + " " + order + " limit ?,?;");
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    void testFindAndPaginateAndSortAllTestsNotEmpty() throws SQLException {
        String orderBy = "orderby", order = "asc";
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(test.getId());
        when(mockResultSet.getInt("difficulty")).thenReturn(test.getDifficulty());
        when(mockResultSet.getInt("time_minutes")).thenReturn(test.getTimeMinutes());
        when(mockResultSet.getInt("countOfQuestions")).thenReturn(test.getCountOfQuestions());
        when(mockResultSet.getString("name")).thenReturn(test.getName());
        when(mockResultSet.getString("nameOfDiscipline")).thenReturn(test.getNameOfDiscipline());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList(test), testDao.findAndPaginateAndSortAllTests(1, 1, orderBy, order));
        verify(mockConnection, times(1)).prepareStatement("SELECT * FROM onlinetesting.tests ORDER BY " + orderBy + " " + order + " limit ?,?;");
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
    }

    @Test
    void testFindAndPaginateAndSortAllTestsError() throws SQLException {
        String orderBy = "orderby", order = "asc";
        when(mockConnection.prepareStatement("SELECT * FROM onlinetesting.tests ORDER BY " + orderBy + " " + order + " limit ?,?;")).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.findAndPaginateAndSortAllTests(1, 1, orderBy, order));
        assertEquals("Can't find or paginate or sort unsurpassed tests", thrown.getMessage());
    }

    @Test
    void testFindAndPaginateAndSortAllTestsWithDisciplineEmpty() throws SQLException {
        String orderBy = "orderby", order = "asc", discipline = "MAth";
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.EMPTY_LIST, testDao.findAndPaginateAndSortAllTests(1, 1, orderBy, order, discipline));
        verify(mockConnection, times(1)).prepareStatement("SELECT * FROM onlinetesting.tests WHERE tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;");
        verify(mockPreparedStatement, times(1)).setString(1, discipline);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    void testFindAndPaginateAndSortAllTestsWithDisciplineNotEmpty() throws SQLException {
        String orderBy = "orderby", order = "asc", discipline = "MAth";
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(test.getId());
        when(mockResultSet.getInt("difficulty")).thenReturn(test.getDifficulty());
        when(mockResultSet.getInt("time_minutes")).thenReturn(test.getTimeMinutes());
        when(mockResultSet.getInt("countOfQuestions")).thenReturn(test.getCountOfQuestions());
        when(mockResultSet.getString("name")).thenReturn(test.getName());
        when(mockResultSet.getString("nameOfDiscipline")).thenReturn(test.getNameOfDiscipline());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList(test), testDao.findAndPaginateAndSortAllTests(1, 1, orderBy, order, discipline));
        verify(mockConnection, times(1)).prepareStatement("SELECT * FROM onlinetesting.tests WHERE tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;");
        verify(mockPreparedStatement, times(1)).setString(1, discipline);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
    }

    @Test
    void testFindAndPaginateAndSortAllTestsWithDisciplineError() throws SQLException {
        String orderBy = "orderby", order = "asc", discipline = "math";
        when(mockConnection.prepareStatement("SELECT * FROM onlinetesting.tests WHERE tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;")).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.findAndPaginateAndSortAllTests(1, 1, orderBy, order, discipline));
        assertEquals("Can't find or paginate or sort unsurpassed tests by discipline", thrown.getMessage());
    }

    @Test
    void testUpdatePassedTest() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(testDao.updatePassedTest(1, 1, 1));
        verify(mockConnection, times(1)).prepareStatement(Query.UPDATE_PASSED_TEST);
        verify(mockPreparedStatement, times(1)).setDouble(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeQuery();
    }

    @Test
    void testUpdatePassedTestError() throws SQLException {
        when(mockConnection.prepareStatement(Query.UPDATE_PASSED_TEST)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.updatePassedTest(1, 1, 1));
        assertEquals("Can't update passed test", thrown.getMessage());
    }

    @Test
    void testCountAllTestZero() throws SQLException {
        when(mockStatement.executeQuery(Query.COUNT_ALL_TEST)).thenReturn(mockResultSet);
        assertEquals(0, testDao.countAllTest());
        verify(mockConnection, times(1)).createStatement();
        verify(mockStatement, times(1)).executeQuery(Query.COUNT_ALL_TEST);
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testCountAllTestNotZero() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("COUNT(1)")).thenReturn(1);
        when(mockStatement.executeQuery(Query.COUNT_ALL_TEST)).thenReturn(mockResultSet);
        assertEquals(1, testDao.countAllTest());
        verify(mockConnection, times(1)).createStatement();
        verify(mockStatement, times(1)).executeQuery(Query.COUNT_ALL_TEST);
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt("COUNT(1)");
    }

    @Test
    void testCountAllTestError() throws SQLException {
        when(mockConnection.createStatement()).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, testDao::countAllTest);
        assertEquals("Can't count all tests", thrown.getMessage());
    }

    @Test
    void testFindAndPaginateAllTestsEmpty() throws SQLException {
        assertEquals(Collections.EMPTY_LIST, testDao.findAndPaginateAllTests(1, 1));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_AND_PAGINATE_ALL_TESTS);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    void testFindAndPaginateAllTestsNotEmpty() throws SQLException {
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(test.getId());
        when(mockResultSet.getInt("difficulty")).thenReturn(test.getDifficulty());
        when(mockResultSet.getInt("time_minutes")).thenReturn(test.getTimeMinutes());
        when(mockResultSet.getInt("countOfQuestions")).thenReturn(test.getCountOfQuestions());
        when(mockResultSet.getString("name")).thenReturn(test.getName());
        when(mockResultSet.getString("nameOfDiscipline")).thenReturn(test.getNameOfDiscipline());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList(test), testDao.findAndPaginateAllTests(1, 1));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_AND_PAGINATE_ALL_TESTS);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    void testFindAndPaginateAllTestsError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_AND_PAGINATE_ALL_TESTS)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.findAndPaginateAllTests(1, 1));
        assertEquals("Can't find or paginate all tests", thrown.getMessage());
    }

    @Test
    void testCountPaginateAndSortUnsurpassedTestsZero() throws SQLException {
        when(mockPreparedStatement.executeQuery(Query.COUNT_PAGINATE_AND_SORT_UNSURPASSED_TESTS)).thenReturn(mockResultSet);
        assertEquals(0, testDao.countPaginateAndSortUnsurpassedTests(1));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_PAGINATE_AND_SORT_UNSURPASSED_TESTS);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testCountPaginateAndSortUnsurpassedTestsNotZero() throws SQLException {
        when(mockResultSet.getInt("COUNT(1)")).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockPreparedStatement.executeQuery(Query.COUNT_PAGINATE_AND_SORT_UNSURPASSED_TESTS)).thenReturn(mockResultSet);
        assertEquals(1, testDao.countPaginateAndSortUnsurpassedTests(1));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_PAGINATE_AND_SORT_UNSURPASSED_TESTS);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt("COUNT(1)");
    }

    @Test
    void testCountPaginateAndSortUnsurpassedTestsError() throws SQLException {
        when(mockConnection.prepareStatement(Query.COUNT_PAGINATE_AND_SORT_UNSURPASSED_TESTS)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.countPaginateAndSortUnsurpassedTests(1));
        assertEquals("Can't count for unsurpassed tests", thrown.getMessage());
    }

    @Test
    void testCountPaginateAndSortUnsurpassedTestsWithDisciplineZero() throws SQLException {
        when(mockPreparedStatement.executeQuery(Query.COUNT_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS)).thenReturn(mockResultSet);
        assertEquals(0, testDao.countPaginateAndSortUnsurpassedTests(1, "discipline"));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setString(2, "discipline");
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testCountPaginateAndSortUnsurpassedTestsWithDisciplineNotZero() throws SQLException {
        when(mockResultSet.getInt("COUNT(1)")).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockPreparedStatement.executeQuery(Query.COUNT_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS)).thenReturn(mockResultSet);
        assertEquals(1, testDao.countPaginateAndSortUnsurpassedTests(1, "discipline"));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setString(2, "discipline");
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt("COUNT(1)");
    }

    @Test
    void testCountPaginateAndSortUnsurpassedTestsWithDisciplineError() throws SQLException {
        when(mockConnection.prepareStatement(Query.COUNT_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.countPaginateAndSortUnsurpassedTests(1, "Math"));
        assertEquals("Can't count for unsurpassed tests (sorted by discipline=Math)", thrown.getMessage());
    }

    @Test
    void testFindAllDisciplinesEmpty() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(Query.FIND_ALL_DISCIPLINES)).thenReturn(mockResultSet);
        assertEquals(Collections.EMPTY_LIST, testDao.findAllDisciplines());
        verify(mockConnection, times(1)).createStatement();
        verify(mockStatement, times(1)).executeQuery(Query.FIND_ALL_DISCIPLINES);
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testFindAllDisciplinesNotEmpty() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("nameOfDiscipline")).thenReturn("discipline");
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(Query.FIND_ALL_DISCIPLINES)).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList("discipline"), testDao.findAllDisciplines());
        verify(mockConnection, times(1)).createStatement();
        verify(mockStatement, times(1)).executeQuery(Query.FIND_ALL_DISCIPLINES);
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(1)).getString("nameOfDiscipline");
    }

    @Test
    void testFindAllDisciplinesError() throws SQLException {
        when(mockConnection.createStatement()).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, testDao::findAllDisciplines);
        assertEquals("Can't find all disciplines", thrown.getMessage());
    }

    @Test
    void testCountUnsurpassedTestByUserZero() throws SQLException {
        when(mockPreparedStatement.executeQuery(Query.COUNT_UNSURPASSED_TEST_BY_USER)).thenReturn(mockResultSet);
        assertEquals(0, testDao.countUnsurpassedTestsByUser(1));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_UNSURPASSED_TEST_BY_USER);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testCountUnsurpassedTestByUserNotZero() throws SQLException {
        when(mockResultSet.getInt("COUNT(1)")).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockPreparedStatement.executeQuery(Query.COUNT_UNSURPASSED_TEST_BY_USER)).thenReturn(mockResultSet);
        assertEquals(1, testDao.countUnsurpassedTestsByUser(1));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_UNSURPASSED_TEST_BY_USER);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt("COUNT(1)");
    }

    @Test
    void testCountUnsurpassedTestByUserError() throws SQLException {
        when(mockConnection.prepareStatement(Query.COUNT_UNSURPASSED_TEST_BY_USER)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.countUnsurpassedTestsByUser(1));
        assertEquals("Can't count unsurpassed test by user", thrown.getMessage());
    }

    @Test
    void testCountPassedTestByUserZero() throws SQLException {
        when(mockPreparedStatement.executeQuery(Query.COUNT_PASSED_TEST_BY_USER)).thenReturn(mockResultSet);
        assertEquals(0, testDao.countPassedTestByUser(1));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_PASSED_TEST_BY_USER);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testCountPassedTestByUserNotZero() throws SQLException {
        when(mockResultSet.getInt("COUNT(1)")).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockPreparedStatement.executeQuery(Query.COUNT_PASSED_TEST_BY_USER)).thenReturn(mockResultSet);
        assertEquals(1, testDao.countPassedTestByUser(1));
        verify(mockConnection, times(1)).prepareStatement(Query.COUNT_PASSED_TEST_BY_USER);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt("COUNT(1)");
    }

    @Test
    void testCountPassedTestByUserError() throws SQLException {
        when(mockConnection.prepareStatement(Query.COUNT_PASSED_TEST_BY_USER)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.countPassedTestByUser(1));
        assertEquals("Can't count passed test by user", thrown.getMessage());
    }

    @Test
    void testFindAndPaginateAndSortUnsurpassedTestsEmpty() throws SQLException {
        String orderBy = "orderby", order = "asc";
        assertEquals(Collections.EMPTY_LIST, testDao.findAndPaginateAndSortUnsurpassedTests(1, 1, 1, orderBy, order));
        verify(mockConnection, times(1)).prepareStatement("SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL ORDER BY " + orderBy + " " + order + " limit ?,?;");
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testFindAndPaginateAndSortUnsurpassedTestsNotEmpty() throws SQLException {
        String orderBy = "orderby", order = "asc";
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(test.getId());
        when(mockResultSet.getInt("difficulty")).thenReturn(test.getDifficulty());
        when(mockResultSet.getInt("time_minutes")).thenReturn(test.getTimeMinutes());
        when(mockResultSet.getInt("countOfQuestions")).thenReturn(test.getCountOfQuestions());
        when(mockResultSet.getString("name")).thenReturn(test.getName());
        when(mockResultSet.getString("nameOfDiscipline")).thenReturn(test.getNameOfDiscipline());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList(test), testDao.findAndPaginateAndSortUnsurpassedTests(1, 1, 1, orderBy, order));
        verify(mockConnection, times(1)).prepareStatement("SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL ORDER BY " + orderBy + " " + order + " limit ?,?;");
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
    }

    @Test
    void testFindAndPaginateAndSortUnsurpassedTestsError() throws SQLException {
        String orderBy = "orderby", order = "asc";
        when(mockConnection.prepareStatement("SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id IS NULL ORDER BY " + orderBy + " " + order + " limit ?,?;")).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.findAndPaginateAndSortUnsurpassedTests(1, 1, 1, orderBy, order));
        assertEquals("Can't find, paginate or sort unsurpassed test by user", thrown.getMessage());
    }

    @Test
    void testFindAndPaginateAndSortUnsurpassedTestsWithDisciplineEmpty() throws SQLException {
        String orderBy = "orderby", order = "asc", discipline = "Math";
        assertEquals(Collections.EMPTY_LIST, testDao.findAndPaginateAndSortUnsurpassedTests(1, 1, 1, orderBy, order, discipline));
        verify(mockConnection, times(1)).prepareStatement("SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id  IS NULL AND tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;");
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setString(2, discipline);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).setInt(4, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testFindAndPaginateAndSortUnsurpassedTestsWithDisciplineNotEmpty() throws SQLException {
        String orderBy = "orderby", order = "asc", discipline = "Math";
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(test.getId());
        when(mockResultSet.getInt("difficulty")).thenReturn(test.getDifficulty());
        when(mockResultSet.getInt("time_minutes")).thenReturn(test.getTimeMinutes());
        when(mockResultSet.getInt("countOfQuestions")).thenReturn(test.getCountOfQuestions());
        when(mockResultSet.getString("name")).thenReturn(test.getName());
        when(mockResultSet.getString("nameOfDiscipline")).thenReturn(test.getNameOfDiscipline());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList(test), testDao.findAndPaginateAndSortUnsurpassedTests(1, 1, 1, orderBy, order, discipline));
        verify(mockConnection, times(1)).prepareStatement("SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id  IS NULL AND tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;");
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setString(2, discipline);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).setInt(4, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
    }

    @Test
    void testFindAndPaginateAndSortUnsurpassedTestsWithDisciplineError() throws SQLException {
        String orderBy = "orderby", order = "asc", discipline = "Math";
        when(mockConnection.prepareStatement("SELECT * FROM onlinetesting.tests LEFT JOIN onlinetesting.passedtests ON tests.id=passedtests.test_id && passedtests.user_id=? WHERE passedtests.test_id  IS NULL AND tests.nameOfDiscipline=? ORDER BY " + orderBy + " " + order + " limit ?,?;")).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.findAndPaginateAndSortUnsurpassedTests(1, 1, 1, orderBy, order, discipline));
        assertEquals("Can't find, paginate or sort(by discipline=" + discipline + ") unsurpassed test by user=" + 1, thrown.getMessage());
    }

    @Test
    void testFindAndPaginatePassedTestsEmpty() throws SQLException {
        assertEquals(Collections.EMPTY_LIST, testDao.findAndPaginatePassedTests(1, 1, 1));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_AND_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS_BY_USER_ID);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testFindAndPaginatePassedTestsNotEmpty() throws SQLException {
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(test.getId());
        when(mockResultSet.getInt("difficulty")).thenReturn(test.getDifficulty());
        when(mockResultSet.getInt("time_minutes")).thenReturn(test.getTimeMinutes());
        when(mockResultSet.getInt("countOfQuestions")).thenReturn(test.getCountOfQuestions());
        when(mockResultSet.getString("name")).thenReturn(test.getName());
        when(mockResultSet.getString("nameOfDiscipline")).thenReturn(test.getNameOfDiscipline());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList(test), testDao.findAndPaginatePassedTests(1, 1, 1));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_AND_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS_BY_USER_ID);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
        verify(mockResultSet, times(1)).getDouble(anyString());

    }

    @Test
    void testFindAndPaginatePassedTestsError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_AND_PAGINATE_AND_SORT_BY_DISCIPLINE_UNSURPASSED_TESTS_BY_USER_ID)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.findAndPaginatePassedTests(1, 1, 1));
        assertEquals("Can't find, paginate passed tests by user=1", thrown.getMessage());
    }

    @Test
    void testFindAndPaginateUnsurpassedTestsEmpty() throws SQLException {
        assertEquals(Collections.EMPTY_LIST, testDao.findAndPaginateUnsurpassedTests(1, 1, 1));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_AND_PAGINATE_UNSURPASSED_TESTS);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testFindAndPaginateUnsurpassedTestsNotEmpty() throws SQLException {
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(test.getId());
        when(mockResultSet.getInt("difficulty")).thenReturn(test.getDifficulty());
        when(mockResultSet.getInt("time_minutes")).thenReturn(test.getTimeMinutes());
        when(mockResultSet.getInt("countOfQuestions")).thenReturn(test.getCountOfQuestions());
        when(mockResultSet.getString("name")).thenReturn(test.getName());
        when(mockResultSet.getString("nameOfDiscipline")).thenReturn(test.getNameOfDiscipline());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(Collections.singletonList(test), testDao.findAndPaginateUnsurpassedTests(1, 1, 1));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_AND_PAGINATE_UNSURPASSED_TESTS);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setInt(3, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, never()).execute();
        verify(mockPreparedStatement, never()).executeUpdate();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
    }

    @Test
    void testFindAndPaginateUnsurpassedTestsError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_AND_PAGINATE_UNSURPASSED_TESTS)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.findAndPaginateUnsurpassedTests(1, 1, 1));
        assertEquals("Can't find, paginate unsurpassed tests by user=1", thrown.getMessage());
    }

    @Test
    void testAddPassedTest() throws SQLException {
        when(mockPreparedStatement.execute()).thenReturn(true);
        assertTrue(testDao.addPassedTest(1, 1, 1));
        verify(mockConnection, times(1)).prepareStatement(Query.ADD_PASSED_TEST);
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).setDouble(3, 1);
    }

    @Test
    void testAddPassedTestError() throws SQLException {
        when(mockConnection.prepareStatement(Query.ADD_PASSED_TEST)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.addPassedTest(1, 1, 1));
        assertEquals("Can't add paginate passed test=1 for user=1", thrown.getMessage());
    }

    @Test
    void testFindIdByNameZero() throws SQLException {
        assertEquals(0, testDao.findIdByName("testname"));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_ID_BY_NAME);
        verify(mockPreparedStatement, times(1)).setString(1, "testname");
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testFindIdByNameNotZero() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(1, testDao.findIdByName("testname"));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_ID_BY_NAME);
        verify(mockPreparedStatement, times(1)).setString(1, "testname");
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt("id");
    }

    @Test
    void testFindIdByNameError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_ID_BY_NAME)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.findIdByName("Testname"));
        assertEquals("Can't find id for test=Testname", thrown.getMessage());
    }

    @Test
    void testFindByIdNull() throws SQLException {
        assertNull(testDao.findById(1));
        verify(mockConnection,times(1)).prepareStatement(Query.FIND_BY_ID);
        verify(mockPreparedStatement,times(1)).setInt(1,1);
        verify(mockPreparedStatement,times(1)).executeQuery();
        verify(mockResultSet,times(1)).next();
    }
    @Test
    void testFindByIdNotNull() throws SQLException {
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 1);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(test.getId());
        when(mockResultSet.getInt("difficulty")).thenReturn(test.getDifficulty());
        when(mockResultSet.getInt("time_minutes")).thenReturn(test.getTimeMinutes());
        when(mockResultSet.getInt("countOfQuestions")).thenReturn(test.getCountOfQuestions());
        when(mockResultSet.getString("name")).thenReturn(test.getName());
        when(mockResultSet.getString("nameOfDiscipline")).thenReturn(test.getNameOfDiscipline());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertEquals(test,testDao.findById(1));
        verify(mockConnection,times(1)).prepareStatement(Query.FIND_BY_ID);
        verify(mockPreparedStatement,times(1)).setInt(1,1);
        verify(mockPreparedStatement,times(1)).executeQuery();
        verify(mockResultSet,times(1)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
    }
    @Test
    void testFindByIdError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_BY_ID)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.findById(1));
        assertEquals("Can't find test by id=1", thrown.getMessage());
    }

    @Test
    void testCreate() throws SQLException {
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 0);
        Question question1 = new Question(1,1,"Question 1");
        Question question2 = new Question(1,2,"Question 2");
        Answer answer1 = new Answer("Answer 1",true,1);
        Answer answer2 = new Answer("Answer 2",true,1);
        question1.setAnswers(Arrays.asList(answer1,answer2));
        question2.setAnswers(Arrays.asList(answer1,answer2));
        test.setQuestions(Arrays.asList(question1,question2));
        int countAnswers = (int)test.getQuestions().stream()
                .map(Question::getAnswers)
                .mapToLong(Collection::size)
                .sum();
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertTrue(testDao.create(test));
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection, times(1)).prepareStatement(Query.CREATE);
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_ID_BY_NAME);
        verify(mockConnection, times(test.getCountOfQuestions())).prepareStatement(Query.CREATE_QUESTION);
        verify(mockConnection, times(test.getCountOfQuestions())).prepareStatement(Query.FIND_QUESTION_ID);
        verify(mockConnection, times(test.getCountOfQuestions())).prepareStatement(Query.CREATE_ANSWER);
        verify(mockPreparedStatement,times(3+test.getCountOfQuestions()+countAnswers)).setString(anyInt(),anyString());
        verify(mockPreparedStatement,times(3+test.getCountOfQuestions()*4+countAnswers)).setInt(anyInt(),anyInt());
        verify(mockPreparedStatement,times(1+test.getCountOfQuestions()+countAnswers)).execute();
        verify(mockConnection, times(1)).commit();
    }

    @Test
    void testCreateError() throws SQLException {
        when(mockConnection.prepareStatement(Query.CREATE)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.create(new com.stason.testing.model.entity.Test()));
        assertEquals("Failed to add new test in DB =null", thrown.getMessage());
    }

    @Test
    void testUpdate() throws SQLException {
        com.stason.testing.model.entity.Test test = new com.stason.testing.model.entity.Test(1, "name", "difficulty", 1, 1, 0);
        Question question1 = new Question(1,1,"Question 1");
        Question question2 = new Question(1,2,"Question 2");
        Answer answer1 = new Answer("Answer 1",true,1);
        Answer answer2 = new Answer("Answer 2",true,1);
        question1.setAnswers(Arrays.asList(answer1,answer2));
        question2.setAnswers(Arrays.asList(answer1,answer2));
        test.setQuestions(Arrays.asList(question1,question2));
        int countAnswers = (int)test.getQuestions().stream()
                .map(Question::getAnswers)
                .mapToLong(Collection::size)
                .sum();
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2).thenReturn(1);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertTrue(testDao.update(test));
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection, times(1)).prepareStatement(Query.UPDATE);
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_ALL_QUESTIONS_ID_FOR_TEST);
        verify(mockConnection, times(2)).prepareStatement(Query.DELETE_ANSWERS);
        verify(mockConnection, times(1)).prepareStatement(Query.DELETE_QUESTIONS);
        verify(mockConnection, times(test.getCountOfQuestions())).prepareStatement(Query.CREATE_QUESTION);
        verify(mockConnection, times(test.getCountOfQuestions())).prepareStatement(Query.FIND_QUESTION_ID);
        verify(mockConnection, times(test.getCountOfQuestions())).prepareStatement(Query.CREATE_ANSWER);
        verify(mockConnection, times(1)).prepareStatement(Query.DELETE_PASSED_TEST);
        verify(mockPreparedStatement,times(2+test.getCountOfQuestions()+countAnswers)).setString(anyInt(),anyString());
        verify(mockPreparedStatement,times(7+2+test.getCountOfQuestions()*4+countAnswers)).setInt(anyInt(),anyInt());
        verify(mockPreparedStatement,times(test.getCountOfQuestions()+countAnswers)).execute();
        verify(mockConnection, times(1)).commit();
    }

    @Test
    void testUpdateError() throws SQLException {
        when(mockConnection.prepareStatement(Query.UPDATE)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.update(new com.stason.testing.model.entity.Test()));
        assertEquals("Can't update the Test", thrown.getMessage());
    }

    @Test
    void testDelete() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        assertTrue(testDao.delete(1));
        verify(mockConnection, times(1)).prepareStatement(Query.FIND_ALL_QUESTIONS_ID_FOR_TEST);
        verify(mockConnection, times(2)).prepareStatement(Query.DELETE_ANSWERS);
        verify(mockConnection, times(1)).prepareStatement(Query.DELETE_QUESTIONS);
        verify(mockConnection, times(1)).prepareStatement(Query.DELETE_PASSED_TEST);
        verify(mockConnection, times(1)).prepareStatement(Query.DELETE);
        verify(mockPreparedStatement, times(6)).setInt(anyInt(),anyInt());
    }

    @Test
    void testDeleteError() throws SQLException {
        when(mockConnection.prepareStatement(Query.FIND_ALL_QUESTIONS_ID_FOR_TEST)).thenThrow(SQLException.class);
        DataBaseException thrown = assertThrows(DataBaseException.class, () -> testDao.delete(1));
        assertEquals("Can't delete test id=1", thrown.getMessage());
    }
}