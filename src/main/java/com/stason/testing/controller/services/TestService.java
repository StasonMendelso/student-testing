package com.stason.testing.controller.services;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import java.util.List;

public class TestService {
    private final DaoFactory factory = DaoFactory.getInstance();
    private final TestDao testDao = factory.createTestDao();
    public void addPassedTest(int userId,int testId, double mark){
        testDao.addPassedTest(userId,testId,0);
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

    public void create(Test test) {
        testDao.create(test);
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
}
