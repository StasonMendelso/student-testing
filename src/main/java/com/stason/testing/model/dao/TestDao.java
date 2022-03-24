package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Test;

import java.util.List;

public interface TestDao extends GenericDao<Test>{
    int findIdByName(String testName);

    void addPassedTest(int userId, int testId, double mark);
    List<Test> findAndPaginateUnsurpassedTests(int userId, int index, int paginationParameter);
    int countUnpassedTestByUser(int userId);
    List<Test> findPassedTests(int userId);
    List<Test> findAndPaginatePassedTests(int userId, int index,int paginationParameter);
    int countPassedTestByUser(int userId);
    List<Test> findUnPassedTests(int userId);

    List<String> findAllDisciplines();


    List<Test> findAndPaginateAndSortUnpassedTests(int userId, int index, int paginationParameter, String orderBy, String order);

    List<Test> findAndPaginateAndSortUnpassedTests(int userId, int index, int paginationParameter, String orderBy, String order, String discipline);

    int countPaginateAndSortUnpassedTests(int userId);

    int countPaginateAndSortUnpassedTests(int userId, String discipline);

    int countAllTest();

    List<Test> findAndPaginateAllTests( int index, int paginationParameter);

    List<Test> findAndPaginateAndSortUnpassedTests(int index, int paginationParameter, String orderBy, String order);
    List<Test> findAndPaginateAndSortUnpassedTests(int index, int paginationParameter, String orderBy, String order,String discipline);

    int countTestByDiscipline(String discipline);

    void updatePassedTest(int userId, int id, double mark);
}
