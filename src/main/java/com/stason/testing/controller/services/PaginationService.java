package com.stason.testing.controller.services;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.dao.implement.JDBCTestDao;
import com.stason.testing.model.dao.implement.JDBCUserDao;
import com.stason.testing.model.entity.Test;
import com.stason.testing.model.entity.User;

import java.util.List;
/**
 * It is a pagination-service class
 * @author Stanislav Hlova
 * @version 1.0
 */
public class PaginationService {

    private final TestDao testDao = new JDBCTestDao();
    private final UserDao userDao = new JDBCUserDao();

    public List<Test> paginatePassedTests(int userId, int paginationParameter, int pageNumber) {
        return testDao.findAndPaginatePassedTests(userId, paginationParameter * (pageNumber - 1), paginationParameter);
    }

    public int countButtonsForPaginationPassedTests(int userId, int paginationParameter) {
        double countOfPageNumberButtons = (double) testDao.countPassedTestByUser(userId) / paginationParameter;
        return (int) countOfPageNumberButton(countOfPageNumberButtons);
    }

    public List<Test> paginateUnsurpassedTests(int userId, int paginationParameter, int pageNumber) {
        return testDao.findAndPaginateUnsurpassedTests(userId, paginationParameter * (pageNumber - 1), paginationParameter);
    }

    public int countButtonsForPaginationUnsurpassedTests(int userId, int paginationParameter) {
        double countOfPageNumberButtons = (double) testDao.countUnsurpassedTestsByUser(userId) / paginationParameter;
        return (int) countOfPageNumberButton(countOfPageNumberButtons);
    }

    public int countButtonsForPaginationAllTests(int paginationParameter) {
        double countOfPageNumberButtons = (double) testDao.countAllTest() / paginationParameter;
        return (int) countOfPageNumberButton(countOfPageNumberButtons);
    }

    public List<Test> paginateAllTests(int paginationParameter, int pageNumber) {
        return testDao.findAndPaginateAllTests(paginationParameter * (pageNumber - 1), paginationParameter);
    }

    public List<User> paginateAllUsers(int paginationParameter, int pageNumber) {
        return userDao.findAndPaginateAllUsers(paginationParameter * (pageNumber - 1), paginationParameter);
    }

    public int countButtonsForPaginationAllUsers(int paginationParameter) {
        double countOfPageNumberButtons = (double) userDao.countAllUsers() / paginationParameter;
        return (int) countOfPageNumberButton(countOfPageNumberButtons);
    }
    double countOfPageNumberButton(double countOfPageNumberButtons){
        if (countOfPageNumberButtons <= 1) {
            countOfPageNumberButtons = 0;
        } else {
            countOfPageNumberButtons = Math.ceil(countOfPageNumberButtons);
        }
        return countOfPageNumberButtons;
    }
}
