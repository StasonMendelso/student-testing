package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationAndSortingService;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Test;
import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTestsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String showUsersUrl = "redirect:/web-application/testing/admin/showTests";
        if(request.getRequestURI().contains("admin/showTests")) {
            int countOfPageNumberButtons =0;
            List<Test> testList = new ArrayList<>();
            int paginationParameter = getPaginationParameter(request, "paginationParameter");
            int pageNumber = getPageNumber(request, "pageNumber");

            if(request.getParameter("clear")!=null) {

                countOfPageNumberButtons = PaginationService.countButtonsForPaginationAllTests( paginationParameter);
                testList = PaginationService.paginateAllTests(paginationParameter, pageNumber);

            }else if(request.getParameter("orderBy")!=null &&
                    request.getParameter("order")!=null &&
                    request.getParameter("discipline")!=null &&
                    request.getParameter("clear")==null
            ){
                String orderBy = request.getParameter("orderBy"); //
                String order = request.getParameter("order"); // ASC DESC
                String discipline = EncodingConverter.convertFromISOtoUTF8(request.getParameter("discipline")); // ALL another
                if(orderBy.isEmpty()){
                    testList = PaginationService.paginateAllTests(paginationParameter,pageNumber);
                    countOfPageNumberButtons=PaginationService.countButtonsForPaginationAllTests(paginationParameter);
                }else {
                    testList = PaginationAndSortingService.paginateAndSortAllTests(paginationParameter, pageNumber, orderBy, order, discipline);
                    countOfPageNumberButtons = PaginationAndSortingService.countButtonsForPaginatedAndSortedAllTests(paginationParameter, discipline);
                }
                Map<String,String> sortingOptions = new HashMap<>();
                sortingOptions.put("orderBy",orderBy);
                sortingOptions.put("order",order);
                sortingOptions.put("discipline",discipline);
                request.setAttribute("sortingOptions", sortingOptions);
            }else{
                testList = PaginationService.paginateAllTests(paginationParameter,pageNumber);
                countOfPageNumberButtons=PaginationService.countButtonsForPaginationAllTests(paginationParameter);
            }

            DaoFactory factory = DaoFactory.getInstance();
            TestDao testDao = factory.createTestDao();
            List<String> disciplinesList = testDao.findAllDisciplines();
            request.setAttribute("disciplinesList", disciplinesList);

            request.setAttribute("countOfPageNumberButtons",countOfPageNumberButtons);
            request.setAttribute("paginationParameter",paginationParameter);
            request.setAttribute("pageNumber",pageNumber);
            request.setAttribute("testList", testList);
            return "/WEB-INF/view/admin/showTests.jsp";
        }else{
            return showUsersUrl;
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
