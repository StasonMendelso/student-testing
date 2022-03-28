package com.stason.testing.controller.services;


import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.dao.implement.JDBCTestDao;
import com.stason.testing.model.dao.implement.JDBCUserDao;
import com.stason.testing.model.entity.Test;
import com.stason.testing.model.entity.User;

import java.util.List;

public class PaginationService {

private final TestDao testDao = new JDBCTestDao();
    private final UserDao userDao = new JDBCUserDao();
    public  List<Test> paginatePassedTests(int userId, int paginationParameter, int pageNumber){

        int index = paginationParameter*(pageNumber-1);

        return testDao.findAndPaginatePassedTests(userId,index,paginationParameter);
    }
    public  int countButtonsForPaginationPassedTests(int userId, int paginationParameter){

        int countPassedTest = testDao.countPassedTestByUser(userId);
        double countOfPageNumberButtons=(double)countPassedTest/paginationParameter;
        if(countOfPageNumberButtons<=1){
            countOfPageNumberButtons=0;
        }else {
            countOfPageNumberButtons=Math.ceil(countOfPageNumberButtons);
        }
        return (int) countOfPageNumberButtons;
    }
    public  List<Test> paginateUnpassedTests(int userId, int paginationParameter, int pageNumber){

        int index = paginationParameter*(pageNumber-1);

        return testDao.findAndPaginateUnsurpassedTests(userId,index,paginationParameter);
    }
    public  int countButtonsForPaginationUnpassedTests(int userId, int paginationParameter){

        int countPassedTest = testDao.countUnsurpassedTestByUser(userId);
        double countOfPageNumberButtons=(double)countPassedTest/paginationParameter;
        if(countOfPageNumberButtons<=1){
            countOfPageNumberButtons=0;
        }else {
            countOfPageNumberButtons=Math.ceil(countOfPageNumberButtons);
        }
        return (int) countOfPageNumberButtons;
    }

    public  int countButtonsForPaginationAllTests( int paginationParameter) {

        int countPassedTest = testDao.countAllTest();
        double countOfPageNumberButtons=(double)countPassedTest/paginationParameter;
        if(countOfPageNumberButtons<=1){
            countOfPageNumberButtons=0;
        }else {
            countOfPageNumberButtons=Math.ceil(countOfPageNumberButtons);
        }
        return (int) countOfPageNumberButtons;
    }

    public  List<Test> paginateAllTests(int paginationParameter, int pageNumber) {

        int index = paginationParameter*(pageNumber-1);

        return testDao.findAndPaginateAllTests(index,paginationParameter);
    }
    public  List<User> paginateAllUsers(int paginationParameter, int pageNumber) {


        int index = paginationParameter*(pageNumber-1);

        return userDao.findAndPaginateAllUsers(index,paginationParameter);
    }
    public  int countButtonsForPaginationAllUsers( int paginationParameter) {

        int countPassedTest = userDao.countAllUsers();
        double countOfPageNumberButtons=(double)countPassedTest/paginationParameter;
        if(countOfPageNumberButtons<=1){
            countOfPageNumberButtons=0;
        }else {
            countOfPageNumberButtons=Math.ceil(countOfPageNumberButtons);
        }
        return (int) countOfPageNumberButtons;
    }

}
