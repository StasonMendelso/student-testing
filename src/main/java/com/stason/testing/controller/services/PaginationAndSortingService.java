package com.stason.testing.controller.services;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Test;

import java.util.ArrayList;
import java.util.List;

public class PaginationAndSortingService {
    private final DaoFactory factory = DaoFactory.getInstance();
    private final TestDao testDao = factory.createTestDao();
    public  List<Test> paginateAndSortUnpassedTests(int userId, int paginationParameter, int pageNumber, String orderBy, String order, String discipline){
        int index = paginationParameter*(pageNumber-1);
        if(discipline.equals("all")){
            return testDao.findAndPaginateAndSortUnpassedTests(userId,index,paginationParameter,orderBy,order);
        }else{
            return testDao.findAndPaginateAndSortUnpassedTests(userId,index,paginationParameter,orderBy,order,discipline);
        }
    }

    public int countButtonsForPaginatedAndSortedUnpassedTests(int userId, int paginationParameter, String discipline) {
        int countUnPassedTest;
        if(discipline.equals("all")){
            countUnPassedTest = testDao.countPaginateAndSortUnpassedTests(userId);
        }else{
            countUnPassedTest = testDao.countPaginateAndSortUnpassedTests(userId,discipline);
        }
        double countOfPageNumberButtons=(double)countUnPassedTest/paginationParameter;

        if(countOfPageNumberButtons<=1){
            countOfPageNumberButtons=0;
        }else {
            countOfPageNumberButtons=Math.ceil(countOfPageNumberButtons);
        }
        return (int) countOfPageNumberButtons;
    }

    public  List<Test> paginateAndSortAllTests( int paginationParameter, int pageNumber, String orderBy, String order, String discipline) {
        int index = paginationParameter*(pageNumber-1);
        if(discipline.equals("all")){
            return testDao.findAndPaginateAndSortUnpassedTests(index,paginationParameter,orderBy,order);
        }else{
            return testDao.findAndPaginateAndSortUnpassedTests(index,paginationParameter,orderBy,order,discipline);
        }
    }

    public  int countButtonsForPaginatedAndSortedAllTests(int paginationParameter, String discipline) {
        int countAllTest;
        if(discipline.equals("all")){
            countAllTest = testDao.countAllTest();
        }else{
            countAllTest = testDao.countTestByDiscipline(discipline);
        }
        double countOfPageNumberButtons=(double)countAllTest/paginationParameter;

        if(countOfPageNumberButtons<=1){
            countOfPageNumberButtons=0;
        }else {
            countOfPageNumberButtons=Math.ceil(countOfPageNumberButtons);
        }
        return (int) countOfPageNumberButtons;
    }
}
