package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Test;

import java.util.List;

/**
 * Interface for interaction in the database with the Test.
 * @author Stanislav Hlova
 * @version 1.0
 */
public interface TestDao extends GenericDao<Test>{
    /**
     * Finds test's id in the database by name of the test
     * @param testName - a name of the test
     * @return return test's id in the database
     */
    int findIdByName(String testName);

    /**
     * Adds test as passed by the user in database
     * @param userId a user's id
     * @param testId a test's id
     * @param mark a user's mark for this test
     * @return if test was added as passed, then return true, else - false
     */
    boolean addPassedTest(int userId, int testId, double mark);

    /**
     * Finds all unsurpassed tests by the user and then paginates them
     * @param userId a user's id
     * @param index this number indicates which element you need to start reading records in the database.
     * @param paginationParameter this number indicates how many records will be read from the database.
     * @return return a List of unsurpassed Tests for pagination.
     */
    List<Test> findAndPaginateUnsurpassedTests(int userId, int index, int paginationParameter);

    /**
     * Counts user's unsurpassed tests
     * @param userId a user's id
     * @return a number of user's unsurpassed tests
     */
    int countUnsurpassedTestsByUser(int userId);

    /**
     * Finds all passed tests by the user and then paginates them
     * @param userId a user's id
     * @param index this number indicates which element you need to start reading records in the database.
     * @param paginationParameter this number indicates how many records will be read from the database.
     * @return return a List of passed Tests for pagination.
     */
    List<Test> findAndPaginatePassedTests(int userId, int index,int paginationParameter);
    /**
     * Counts user's passed tests
     * @param userId a user's id
     * @return a number of user's passed tests
     */
    int countPassedTestByUser(int userId);

    /**
     * Finds all discipline in the database
     * @return return a List of disciplines' names
     */
    List<String> findAllDisciplines();

    /**
     * Finds, sorts and paginates user's unsurpassed tests
     * @param userId a user's id
     * @param index this number indicates which element you need to start reading records in the database.
     * @param paginationParameter this number indicates how many records will be read from the database.
     * @param orderBy means which parameter to sort by
     * @param order means in what order to sort (ASCENDING or DESCENDING)
     * @return return a List of sorted and paginated user's unsurpassed tests
     */
    List<Test> findAndPaginateAndSortUnsurpassedTests(int userId, int index, int paginationParameter, String orderBy, String order);

    /**
     * Finds, sorts and paginates user's unsurpassed tests
     * @param userId a user's id
     * @param index this number indicates which element you need to start reading records in the database.
     * @param paginationParameter this number indicates how many records will be read from the database.
     * @param orderBy means which parameter to sort by
     * @param order means in what order to sort (ASCENDING or DESCENDING)
     * @param discipline means from which disciplines to sort
     * @return return a List of sorted and paginated user's unsurpassed tests
     */
    List<Test> findAndPaginateAndSortUnsurpassedTests(int userId, int index, int paginationParameter, String orderBy, String order, String discipline);

    /**
     * Counts user's unsurpassed tests
     * @param userId a user's id
     * @return a number of user's unsurpassed tests
     */
    int countPaginateAndSortUnsurpassedTests(int userId);
    /**
     * Counts user's unsurpassed tests from the chosen discipline
     * @param userId a user's id
     * @param discipline a name of discipline
     * @return a number of user's unsurpassed tests from the chosen discipline
     */
    int countPaginateAndSortUnsurpassedTests(int userId, String discipline);
    /**
     * Counts all tests in the database
     * @return a number of the tests
     */
    int countAllTest();

    /**
     * Finds and paginates all tests
     * @param index this number indicates which element you need to start reading records in the database.
     * @param paginationParameter this number indicates how many records will be read from the database.
     * @return return a List of tests
     */
    List<Test> findAndPaginateAllTests( int index, int paginationParameter);

    /**
     * Finds, sorts and paginates Tests
     * @param index this number indicates which element you need to start reading records in the database.
     * @param paginationParameter this number indicates how many records will be read from the database.
     * @param orderBy means which parameter to sort by
     * @param order means in what order to sort (ASCENDING or DESCENDING)
     * @return return a List of Tests
     */
    List<Test> findAndPaginateAndSortAllTests(int index, int paginationParameter, String orderBy, String order);
    /**
     * Finds, sorts and paginates Tests
     * @param index this number indicates which element you need to start reading records in the database.
     * @param paginationParameter this number indicates how many records will be read from the database.
     * @param orderBy means which parameter to sort by
     * @param order means in what order to sort (ASCENDING or DESCENDING)
     * @param discipline means from which disciplines to sort
     * @return return a List of Tests
     */
    List<Test> findAndPaginateAndSortAllTests(int index, int paginationParameter, String orderBy, String order, String discipline);

    /**
     * Counts tests from the chosen discipline
     * @param discipline a name of discipline
     * @return a number of tests from the chosen discipline
     */
    int countTestByDiscipline(String discipline);

    /**
     * Update mark for user's passed test
     * @param userId a user's id
     * @param id a test's id
     * @param mark a mark for the test
     * @return if the passed test was updated, then return true, else - false
     */
    boolean updatePassedTest(int userId, int id, double mark);

    /**
     * Delete user's passed test
     * @param testId a test's id
     * @param userId a user's id
     */
    void deletePassedTestForUser(int testId, int userId);
}
