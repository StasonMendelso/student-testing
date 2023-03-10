package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationAndSortingService;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.utils.CommandsHelper;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.Path;

import com.stason.testing.model.entity.Test;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * It is a Command, which shows test for student
 * @author Stanislav Hlova
 * @version 1.0
 */
public class ShowTestsCommand implements Command {
    private static final Logger logger = Logger.getLogger(ShowTestsCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.contains("/student/tests")) {
            int userId = (int) request.getSession().getAttribute("id");
            int countOfPageNumberButtons1 = 0;
            int countOfPageNumberButtons2 = 0;
            List<Test> unsurpassedTests = new ArrayList<>();
            List<Test> testList = new ArrayList<>();
            int paginationParameter1 = CommandsHelper.getPaginationParameter(request, "paginationParameter1");
            int pageNumber1 = CommandsHelper.getPageNumber(request, "pageNumber1");
            int paginationParameter2 = CommandsHelper.getPaginationParameter(request, "paginationParameter2");
            int pageNumber2 = CommandsHelper.getPageNumber(request, "pageNumber2");

            final PaginationService paginationService = new PaginationService();
            final PaginationAndSortingService paginationAndSortingService = new PaginationAndSortingService();

            if (request.getParameter("clear") != null) {
                // ???????????????? ????????????????????
                countOfPageNumberButtons1 = paginationService.countButtonsForPaginationUnsurpassedTests(userId, paginationParameter1);
                unsurpassedTests = paginationService.paginateUnsurpassedTests(userId, paginationParameter1, pageNumber1);
                countOfPageNumberButtons2 = paginationService.countButtonsForPaginationAllTests(paginationParameter2);
                testList = paginationService.paginateAllTests(paginationParameter2, pageNumber2);

            } else if (checkParametersSorting(request) && request.getParameter("clear") == null) {
                //???????????? ????????????????????
                String orderBy = request.getParameter("orderBy"); //
                String order = request.getParameter("order"); // ASC DESC
                String discipline = EncodingConverter.convertFromISOtoUTF8(request.getParameter("discipline")); // ALL another

                unsurpassedTests = paginationAndSortingService.paginateAndSortUnsurpassedTests(userId, paginationParameter1, pageNumber1, orderBy, order, discipline);
                countOfPageNumberButtons1 = paginationAndSortingService.countButtonsForPaginatedAndSortedUnsurpassedTests(userId, paginationParameter1, discipline);

                testList = paginationAndSortingService.paginateAndSortAllTests(paginationParameter2, pageNumber2, orderBy, order, discipline);
                countOfPageNumberButtons2 = paginationAndSortingService.countButtonsForPaginatedAndSortedAllTests(paginationParameter2, discipline);


                Map<String, String> sortingOptions = new HashMap<>();
                sortingOptions.put("orderBy", orderBy);
                sortingOptions.put("order", order);
                sortingOptions.put("discipline", discipline);
                request.setAttribute("sortingOptions", sortingOptions);
            } else {
                // ?????????????????? ??????????????????1
                if (checkParametersSorting1(request)) {
                    String orderBy = request.getParameter("orderBy1"); //
                    String order = request.getParameter("order1"); // ASC DESC
                    String discipline = EncodingConverter.convertFromISOtoUTF8(request.getParameter("discipline1")); // ALL another
                    if (orderBy.isEmpty()) {
                        // ???? ???????????? ????????????????????, ???????????????????? ??????????????
                        unsurpassedTests = paginationService.paginateUnsurpassedTests(userId, paginationParameter1, pageNumber1);
                        countOfPageNumberButtons1 = paginationService.countButtonsForPaginationUnsurpassedTests(userId, paginationParameter1);
                    } else {
                        //???????????? ????????????????????
                        unsurpassedTests = paginationAndSortingService.paginateAndSortUnsurpassedTests(userId, paginationParameter1, pageNumber1, orderBy, order, discipline);
                        countOfPageNumberButtons1 = paginationAndSortingService.countButtonsForPaginatedAndSortedUnsurpassedTests(userId, paginationParameter1, discipline);
                    }
                    Map<String, String> sortingOptions = new HashMap<>();
                    sortingOptions.put("orderBy", orderBy);
                    sortingOptions.put("order", order);
                    sortingOptions.put("discipline", discipline);
                    request.setAttribute("sortingOptions", sortingOptions);
                }
                // ?????????????????? ??????????????????2
                if (checkParametersSorting2(request)) {
                    String orderBy = request.getParameter("orderBy2"); //
                    String order = request.getParameter("order2"); // ASC DESC
                    String discipline = EncodingConverter.convertFromISOtoUTF8(request.getParameter("discipline2")); // ALL another
                    if (orderBy.isEmpty()) {
                        // ???? ???????????? ????????????????????, ???????????????????? ??????????????
                        testList = paginationService.paginateAllTests(paginationParameter2, pageNumber2);
                        countOfPageNumberButtons2 = paginationService.countButtonsForPaginationAllTests(paginationParameter2);
                    } else {
                        //???????????? ????????????????????
                        testList = paginationAndSortingService.paginateAndSortAllTests(paginationParameter2, pageNumber2, orderBy, order, discipline);
                        countOfPageNumberButtons2 = paginationAndSortingService.countButtonsForPaginatedAndSortedAllTests(paginationParameter2, discipline);
                    }
                    Map<String, String> sortingOptions = new HashMap<>();
                    sortingOptions.put("orderBy", orderBy);
                    sortingOptions.put("order", order);
                    sortingOptions.put("discipline", discipline);
                    request.setAttribute("sortingOptions", sortingOptions);

                }
                //???????????????? ???????????????? ????????????
                if (!checkParametersSorting1(request) && !checkParametersSorting2(request)) {
                    unsurpassedTests = paginationService.paginateUnsurpassedTests(userId, paginationParameter1, pageNumber1);
                    countOfPageNumberButtons1 = paginationService.countButtonsForPaginationUnsurpassedTests(userId, paginationParameter1);
                    testList = paginationService.paginateAllTests(paginationParameter2, pageNumber2);
                    countOfPageNumberButtons2 = paginationService.countButtonsForPaginationAllTests(paginationParameter2);
                }
            }
            TestService testService = new TestService();
            UserService userService = new UserService();
            List<String> disciplinesList = testService.findAllDisciplines();

            request.getSession().setAttribute("idOfPassedTests", userService.findIdPassedTestsByUserId(userId));
            request.setAttribute("disciplinesList", disciplinesList);

            request.setAttribute("unsurpassedTestList", unsurpassedTests);
            request.setAttribute("countOfPageNumberButtons1", countOfPageNumberButtons1);
            request.setAttribute("paginationParameter1", paginationParameter1);
            request.setAttribute("pageNumber1", pageNumber1);

            request.setAttribute("countOfPageNumberButtons2", countOfPageNumberButtons2);
            request.setAttribute("paginationParameter2", paginationParameter2);
            request.setAttribute("pageNumber2", pageNumber2);
            request.setAttribute("allTestList", testList);

            return Path.STUDENT_TESTS;
        } else {
            return Path.REDIRECT_STUDENT_TESTS;
        }

    }

    private boolean checkParametersSorting(HttpServletRequest request){
        return (request.getParameter("orderBy") != null &&
                request.getParameter("order") != null &&
                request.getParameter("discipline") != null);
    }
    private boolean checkParametersSorting1(HttpServletRequest request){
        return (request.getParameter("orderBy1") != null &&
                request.getParameter("order1") != null &&
                request.getParameter("discipline1") != null &&
                request.getParameter("paginationParameter1") != null);
    }
    private boolean checkParametersSorting2(HttpServletRequest request){
        return (request.getParameter("orderBy2") != null &&
                request.getParameter("order2") != null &&
                request.getParameter("discipline2") != null &&
                request.getParameter("paginationParameter2") != null);
    }
}
