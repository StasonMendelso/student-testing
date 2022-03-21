package com.stason.testing.controller.services;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import java.util.ArrayList;
import java.util.List;

public class PaginationAndSortingService {
    public static List<Test> paginateAndSortTests(int userId, int paginationParameter, int pageNumber, String orderBy, String order, String discipline){
        List<Test> testList = new ArrayList<>();

        DaoFactory factory = DaoFactory.getInstance();
        TestDao testDao = factory.createTestDao();
        int index = paginationParameter*(pageNumber-1);

        List<Test> list = testDao.findPassedTests(userId);

        return testList;
    }
}
