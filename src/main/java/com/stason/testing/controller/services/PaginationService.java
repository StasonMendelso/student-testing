package com.stason.testing.controller.services;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import java.util.List;

public class PaginationService {
    public static List<Test> paginatePassedTests(int userId, int paginationParameter, int pageNumber){

        DaoFactory factory = DaoFactory.getInstance();
        TestDao testDao = factory.createTestDao();
        int index = paginationParameter*(pageNumber-1);

        return testDao.findAndPaginatePassedTests(userId,index,paginationParameter);
    }
    public static int countButtonsForPaginationPassedTests(int userId, int paginationParameter){
        DaoFactory factory = DaoFactory.getInstance();
        TestDao testDao = factory.createTestDao();
        int countPassedTest = testDao.countPassedTestByUser(userId);
        double countOfPageNumberButtons=(double)countPassedTest/paginationParameter;
        if(countOfPageNumberButtons<=1){
            countOfPageNumberButtons=0;
        }else {
            countOfPageNumberButtons=Math.ceil(countOfPageNumberButtons);
        }
        return (int) countOfPageNumberButtons;
    }
}
