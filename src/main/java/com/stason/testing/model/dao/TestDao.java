package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Test;

import java.util.List;

public interface TestDao extends GenericDao<Test>{
    int findIdByName(String testName);

    void addPassedTest(int userId, int testId, double mark);
    List<Test> findUnsurpassedTests(int userId);

    List<Test> findPassedTests(int userId);
}
