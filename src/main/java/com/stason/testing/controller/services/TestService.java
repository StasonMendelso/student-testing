package com.stason.testing.controller.services;


import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.implement.JDBCTestDao;
import com.stason.testing.model.entity.Test;

import java.util.List;

public class TestService {


    private final TestDao testDao = new JDBCTestDao();
    public void addPassedTest(int userId,int testId, double mark){
        testDao.addPassedTest(userId,testId,mark);
    }
    public Test findById(int testId){
      return testDao.findById(testId);
    }
    public void updatePassedTest(int userId, int testId,double mark){
        testDao.updatePassedTest(userId,testId,mark);
    }
    public List<String> findAllDisciplines(){
        return testDao.findAllDisciplines();
    }

    public boolean create(Test test) {
        return testDao.create(test);
    }

    public int findIdByName(String name) {
        return testDao.findIdByName(name);
    }

    public void deletePassedTestById(int testId) {
        testDao.deletePassedTestById(testId);
    }

    public void delete(int id) {
        testDao.delete(id);
    }

    public void update(Test test) {
        testDao.update(test);
    }
}
