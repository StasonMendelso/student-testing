package com.stason.testing.controller.services;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import java.util.ArrayList;
import java.util.List;

public class PaginationAndSortingService {
    public static List<Test> paginateAndSortUnpassedTests(int userId, int paginationParameter, int pageNumber, String orderBy, String order, String discipline){
        DaoFactory factory = DaoFactory.getInstance();
        TestDao testDao = factory.createTestDao();
        int index = paginationParameter*(pageNumber-1);
        if(discipline.equals("all")){
            return testDao.findAndPaginateAndSortUnpassedTests(userId,index,paginationParameter,orderBy,order);
        }else{
            return testDao.findAndPaginateAndSortUnpassedTests(userId,index,paginationParameter,orderBy,order,discipline);
        }
    }

    public static int countButtonsForPaginatedAndSortedUnpassedTests(int userId, int paginationParameter, String discipline) {
        DaoFactory factory = DaoFactory.getInstance();
        TestDao testDao = factory.createTestDao();
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

    public static List<Test> paginateAndSortAllTests( int paginationParameter, int pageNumber, String orderBy, String order, String discipline) {
        DaoFactory factory = DaoFactory.getInstance();
        TestDao testDao = factory.createTestDao();
        int index = paginationParameter*(pageNumber-1);
        if(discipline.equals("all")){
            return testDao.findAndPaginateAndSortUnpassedTests(index,paginationParameter,orderBy,order);
        }else{
            return testDao.findAndPaginateAndSortUnpassedTests(index,paginationParameter,orderBy,order,discipline);
        }
    }

    public static int countButtonsForPaginatedAndSortedAllTests(int paginationParameter, String discipline) {
        DaoFactory factory = DaoFactory.getInstance();
        TestDao testDao = factory.createTestDao();
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
