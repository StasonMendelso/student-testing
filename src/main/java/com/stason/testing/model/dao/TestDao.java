package com.stason.testing.model.dao;

import com.stason.testing.model.entity.Test;

import java.util.List;

public interface TestDao extends GenericDao<Test>{
    public int findIdByName(String testName);


    List<Test> findUnsurpassedTests(int userId);
}
