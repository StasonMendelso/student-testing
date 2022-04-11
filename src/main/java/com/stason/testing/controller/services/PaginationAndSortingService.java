package com.stason.testing.controller.services;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.implement.JDBCTestDao;
import com.stason.testing.model.entity.Test;


import java.util.List;
/**
 * It is a pagination- and sorting-service class
 * @author Stanislav Hlova
 * @version 1.0
 */
public class PaginationAndSortingService extends PaginationService{

private final TestDao testDao = new JDBCTestDao();

    public  List<Test> paginateAndSortUnsurpassedTests(int userId, int paginationParameter, int pageNumber, String orderBy, String order, String discipline){
        int index = paginationParameter*(pageNumber-1);
        if(discipline.equals("all")){
            return testDao.findAndPaginateAndSortUnsurpassedTests(userId,index,paginationParameter,orderBy,order);
        }else{
            return testDao.findAndPaginateAndSortUnsurpassedTests(userId,index,paginationParameter,orderBy,order,discipline);
        }
    }

    public int countButtonsForPaginatedAndSortedUnsurpassedTests(int userId, int paginationParameter, String discipline) {
        int countUnPassedTest;
        if(discipline.equals("all")){
            countUnPassedTest = testDao.countPaginateAndSortUnsurpassedTests(userId);
        }else{
            countUnPassedTest = testDao.countPaginateAndSortUnsurpassedTests(userId,discipline);
        }
        double countOfPageNumberButtons=(double)countUnPassedTest/paginationParameter;
        return (int) countOfPageNumberButton(countOfPageNumberButtons);
    }

    public  List<Test> paginateAndSortAllTests( int paginationParameter, int pageNumber, String orderBy, String order, String discipline) {
        int index = paginationParameter*(pageNumber-1);
        if(discipline.equals("all")){
            return testDao.findAndPaginateAndSortAllTests(index,paginationParameter,orderBy,order);
        }else{
            return testDao.findAndPaginateAndSortAllTests(index,paginationParameter,orderBy,order,discipline);
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
        return (int) countOfPageNumberButton(countOfPageNumberButtons);
    }
}
