package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Test;

import java.util.List;

public interface TestDao extends GenericDao<Test>{
    int findIdByName(String testName);


    List<Test> findUnsurpassedTests(int userId);

    List<Test> findPassedTests(int userId);
}
