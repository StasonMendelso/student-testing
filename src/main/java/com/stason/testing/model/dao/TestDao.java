package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Test;

import java.util.List;

public interface TestDao extends GenericDao<Test>{
    int findIdByName(String testName);

    boolean addPassedTest(int userId, int testId, double mark);
    List<Test> findAndPaginateUnsurpassedTests(int userId, int index, int paginationParameter);
    int countUnsurpassedTestByUser(int userId);
    List<Test> findAndPaginatePassedTests(int userId, int index,int paginationParameter);
    int countPassedTestByUser(int userId);

    List<String> findAllDisciplines();


    List<Test> findAndPaginateAndSortUnsurpassedTests(int userId, int index, int paginationParameter, String orderBy, String order);

    List<Test> findAndPaginateAndSortUnsurpassedTests(int userId, int index, int paginationParameter, String orderBy, String order, String discipline);

    int countPaginateAndSortUnsurpassedTests(int userId);

    int countPaginateAndSortUnsurpassedTests(int userId, String discipline);

    int countAllTest();

    List<Test> findAndPaginateAllTests( int index, int paginationParameter);

    List<Test> findAndPaginateAndSortUnsurpassedTests(int index, int paginationParameter, String orderBy, String order);
    List<Test> findAndPaginateAndSortUnsurpassedTests(int index, int paginationParameter, String orderBy, String order, String discipline);

    int countTestByDiscipline(String discipline);

    boolean updatePassedTest(int userId, int id, double mark);
    boolean deletePassedTestById(int testId);
}
