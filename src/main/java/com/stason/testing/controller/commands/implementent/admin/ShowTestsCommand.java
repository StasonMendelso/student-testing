package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationAndSortingService;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.utils.CommandsHelper;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.Path;


import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTestsCommand implements Command {
    private final PaginationService paginationService = new PaginationService();

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getRequestURI().contains("admin/showTests")) {
            int countOfPageNumberButtons;
            List<Test> testList;
            int paginationParameter = CommandsHelper.getPaginationParameter(request, "paginationParameter");
            int pageNumber = CommandsHelper.getPageNumber(request, "pageNumber");

            if (request.getParameter("clear") != null) {
                countOfPageNumberButtons = paginationService.countButtonsForPaginationAllTests(paginationParameter);
                testList = paginationService.paginateAllTests(paginationParameter, pageNumber);

            } else if ((request.getParameter("orderBy") != null || request.getSession().getAttribute("orderBy") != null) &&
                    (request.getParameter("order") != null || request.getSession().getAttribute("order") != null) &&
                    (request.getParameter("discipline") != null || request.getSession().getAttribute("discipline") != null) &&
                    request.getParameter("clear") == null
            ) {

                String orderBy = getParameterFromRequestOrSession(request, "orderBy"); //
                String order = getParameterFromRequestOrSession(request, "order"); // ASC DESC
                String discipline = EncodingConverter.convertFromISOtoUTF8(getParameterFromRequestOrSession(request, "discipline")); // ALL another
                if (orderBy.isEmpty()) {
                    testList = paginationService.paginateAllTests(paginationParameter, pageNumber);
                    countOfPageNumberButtons = paginationService.countButtonsForPaginationAllTests(paginationParameter);
                    if (testList.isEmpty() && pageNumber > 1) {
                        pageNumber--;
                        testList = paginationService.paginateAllTests(paginationParameter, pageNumber);
                    }
                } else {
                    PaginationAndSortingService paginationAndSortingService = new PaginationAndSortingService();
                    testList = paginationAndSortingService.paginateAndSortAllTests(paginationParameter, pageNumber, orderBy, order, discipline);
                    countOfPageNumberButtons = paginationAndSortingService.countButtonsForPaginatedAndSortedAllTests(paginationParameter, discipline);
                    if (testList.isEmpty() && pageNumber > 1) {
                        pageNumber--;
                        testList = paginationAndSortingService.paginateAndSortAllTests(paginationParameter, pageNumber, orderBy, order, discipline);
                    }
                }
                Map<String, String> sortingOptions = new HashMap<>();
                sortingOptions.put("orderBy", orderBy);
                sortingOptions.put("order", order);
                sortingOptions.put("discipline", discipline);
                request.setAttribute("sortingOptions", sortingOptions);
            } else {
                testList = paginationService.paginateAllTests(paginationParameter, pageNumber);
                countOfPageNumberButtons = paginationService.countButtonsForPaginationAllTests(paginationParameter);
                if (testList.isEmpty() && pageNumber > 1) {
                    pageNumber--;
                    testList = paginationService.paginateAllTests(paginationParameter, pageNumber);
                }
            }


            final TestService testService = new TestService();
            List<String> disciplinesList = testService.findAllDisciplines();
            request.setAttribute("disciplinesList", disciplinesList);

            request.setAttribute("countOfPageNumberButtons", countOfPageNumberButtons);
            request.setAttribute("paginationParameter", paginationParameter);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("testList", testList);

            if (request.getSession().getAttribute("error") != null) {
                request.setAttribute("error", request.getSession().getAttribute("error"));
                request.getSession().removeAttribute("error");
            }
            return Path.ADMIN_TESTS;
        } else {
            return Path.REDIRECT_ADMIN_TESTS;
        }
    }

    private String getParameterFromRequestOrSession(HttpServletRequest request, String parameterName) {
        if (request.getParameter(parameterName) != null) {
            return request.getParameter(parameterName);
        }
        if (request.getSession().getAttribute(parameterName) != null) {
            String result = (String) request.getSession().getAttribute(parameterName);
            request.getSession().removeAttribute(parameterName);
            return result;
        }
        return "";
    }
}
