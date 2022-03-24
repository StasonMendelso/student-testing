package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationAndSortingService;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTestsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String uri = request.getRequestURI();
        if(uri.contains("/student/tests")){
            int userId = (int) request.getSession().getAttribute("id");


            int countOfPageNumberButtons1 =0;
            int countOfPageNumberButtons2 =0;
            List<Test> unsurpassedTests = new ArrayList<>();
            List<Test> testList = new ArrayList<>();
            int paginationParameter1 = getPaginationParameter(request, "paginationParameter1");
            int pageNumber1 = getPageNumber(request, "pageNumber1");
            int paginationParameter2 = getPaginationParameter(request, "paginationParameter2");
            int pageNumber2 = getPageNumber(request, "pageNumber2");

            if(request.getParameter("clear")!=null) {

                countOfPageNumberButtons1 = PaginationService.countButtonsForPaginationUnpassedTests(userId, paginationParameter1);
                unsurpassedTests = PaginationService.paginateUnpassedTests(userId, paginationParameter1, pageNumber1);
                countOfPageNumberButtons2 = PaginationService.countButtonsForPaginationAllTests( paginationParameter2);
                testList = PaginationService.paginateAllTests(paginationParameter2, pageNumber2);

            }else if(request.getParameter("orderBy")!=null &&
                    request.getParameter("order")!=null &&
                    request.getParameter("discipline")!=null &&
                    request.getParameter("clear")==null
            ){
                String orderBy = request.getParameter("orderBy"); //
                String order = request.getParameter("order"); // ASC DESC
                String discipline = EncodingConverter.convertFromISOtoUTF8(request.getParameter("discipline")); // ALL another

                unsurpassedTests= PaginationAndSortingService.paginateAndSortUnpassedTests(userId,paginationParameter1,pageNumber1,orderBy,order,discipline);
                countOfPageNumberButtons1 = PaginationAndSortingService.countButtonsForPaginatedAndSortedUnpassedTests(userId, paginationParameter1, discipline);

                testList= PaginationAndSortingService.paginateAndSortAllTests(paginationParameter2,pageNumber2,orderBy,order,discipline);
                countOfPageNumberButtons2 = PaginationAndSortingService.countButtonsForPaginatedAndSortedAllTests(paginationParameter2, discipline);


                Map<String,String> sortingOptions = new HashMap<>();
                sortingOptions.put("orderBy",orderBy);
                sortingOptions.put("order",order);
                sortingOptions.put("discipline",discipline);
                request.setAttribute("sortingOptions", sortingOptions);
            }else {
                if (request.getParameter("orderBy1") != null &&
                        request.getParameter("order1") != null &&
                        request.getParameter("discipline1") != null &&
                        request.getParameter("paginationParameter1") != null
                ) {
                    String orderBy = request.getParameter("orderBy1"); //
                    String order = request.getParameter("order1"); // ASC DESC
                    String discipline = EncodingConverter.convertFromISOtoUTF8(request.getParameter("discipline1")); // ALL another
                    if (orderBy.isEmpty()) {
                        // Не робимо сортування, сортування скинуте
                        unsurpassedTests = PaginationService.paginateUnpassedTests(userId, paginationParameter1, pageNumber1);
                        countOfPageNumberButtons1 = PaginationService.countButtonsForPaginationUnpassedTests(userId, paginationParameter1);
                    } else {
                        //Робимо сортування
                        unsurpassedTests = PaginationAndSortingService.paginateAndSortUnpassedTests(userId, paginationParameter1, pageNumber1, orderBy, order, discipline);
                        countOfPageNumberButtons1 = PaginationAndSortingService.countButtonsForPaginatedAndSortedUnpassedTests(userId, paginationParameter1, discipline);
                    }
                    Map<String, String> sortingOptions = new HashMap<>();
                    sortingOptions.put("orderBy", orderBy);
                    sortingOptions.put("order", order);
                    sortingOptions.put("discipline", discipline);
                    request.setAttribute("sortingOptions", sortingOptions);
                }
                if (request.getParameter("orderBy2") != null &&
                        request.getParameter("order2") != null &&
                        request.getParameter("discipline2") != null &&
                        request.getParameter("paginationParameter2") != null
                ) {
                    String orderBy = request.getParameter("orderBy2"); //
                    String order = request.getParameter("order2"); // ASC DESC
                    String discipline = EncodingConverter.convertFromISOtoUTF8(request.getParameter("discipline2")); // ALL another
                    if (orderBy.isEmpty()) {
                        // Не робимо сортування, сортування скинуте
                        testList = PaginationService.paginateAllTests(paginationParameter2, pageNumber2);
                        countOfPageNumberButtons2 = PaginationService.countButtonsForPaginationAllTests(paginationParameter2);
                    } else {
                        //Робимо сортування
                        testList = PaginationAndSortingService.paginateAndSortAllTests( paginationParameter2, pageNumber2, orderBy, order, discipline);
                        countOfPageNumberButtons2 = PaginationAndSortingService.countButtonsForPaginatedAndSortedAllTests( paginationParameter2, discipline);
                    }
                    Map<String, String> sortingOptions = new HashMap<>();
                    sortingOptions.put("orderBy", orderBy);
                    sortingOptions.put("order", order);
                    sortingOptions.put("discipline", discipline);
                    request.setAttribute("sortingOptions", sortingOptions);

                }
                if (request.getParameter("orderBy1") == null &&
                        request.getParameter("order1") == null &&
                        request.getParameter("discipline1") == null &&
                        request.getParameter("paginationParameter1") == null &&
                        request.getParameter("orderBy2") == null &&
                        request.getParameter("order2") == null &&
                        request.getParameter("discipline2") == null &&
                        request.getParameter("paginationParameter2") == null
                ){
                    unsurpassedTests = PaginationService.paginateUnpassedTests(userId, paginationParameter1, pageNumber1);
                    countOfPageNumberButtons1 = PaginationService.countButtonsForPaginationUnpassedTests(userId, paginationParameter1);
                    testList = PaginationService.paginateAllTests( paginationParameter2, pageNumber2);
                    countOfPageNumberButtons2 = PaginationService.countButtonsForPaginationAllTests( paginationParameter2);
                }
            }
            DaoFactory factory = DaoFactory.getInstance();
            TestDao testDao = factory.createTestDao();
            UserDao userDao = factory.createUserDao();
            List<String> disciplinesList = testDao.findAllDisciplines();
            request.getSession().setAttribute("idOfPassedTests",userDao.findIdPassedTestsByUserId(userId));
            request.setAttribute("disciplinesList", disciplinesList);

            request.setAttribute("unsurpassedTestList", unsurpassedTests);
            request.setAttribute("countOfPageNumberButtons1",countOfPageNumberButtons1);
            request.setAttribute("paginationParameter1",paginationParameter1);
            request.setAttribute("pageNumber1",pageNumber1);

            request.setAttribute("countOfPageNumberButtons2",countOfPageNumberButtons2);
            request.setAttribute("paginationParameter2",paginationParameter2);
            request.setAttribute("pageNumber2",pageNumber2);
            request.setAttribute("allTestList", testList);

            return "/WEB-INF/view/student/showTests.jsp";
        }else{
            return "redirect:/web-application/testing/student/tests";
        }

    }
    private int getPageNumber(HttpServletRequest request, String parameterName) {
        int pageNumber;
        if(request.getParameter(parameterName)!=null){
            if(Integer.parseInt(request.getParameter(parameterName))<=0) {
                pageNumber=1;
            }else{
                pageNumber = Integer.parseInt(request.getParameter(parameterName));
            }
        }else{
            pageNumber = 1;
        }
        return pageNumber;
    }

    private int getPaginationParameter(HttpServletRequest request, String parameterName) {
        int paginationParameter;
        if(request.getParameter(parameterName)!=null){
            paginationParameter = Integer.parseInt(request.getParameter(parameterName));
        }else{
            paginationParameter = 5;
        }
        return paginationParameter;
    }
}
