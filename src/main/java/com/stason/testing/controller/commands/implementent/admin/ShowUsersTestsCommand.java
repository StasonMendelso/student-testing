package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.controller.utils.CommandsHelper;
import com.stason.testing.controller.utils.Path;

import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
/**
 * It is a Command, which shows user's passed tests for admin
 * @author Stanislav Hlova
 * @version 1.0
 */
public class ShowUsersTestsCommand implements Command {
    private final PaginationService paginationService = new PaginationService();

    @Override
    public String execute(HttpServletRequest request) {
        int userId = -1;
        int thisPaginationParameter = CommandsHelper.getPaginationParameter(request, "thisPaginationParameter");
        int thisPageNumber = CommandsHelper.getPageNumber(request, "thisPageNumber");
        int countOfPageNumberButtons;
        if (request.getParameter("id") != null) {
            userId = Integer.parseInt(request.getParameter("id"));
        }

        List<Test> testList = paginationService.paginatePassedTests(userId, thisPaginationParameter, thisPageNumber);
        if (testList.isEmpty() && thisPageNumber > 1) {
            thisPageNumber--;
            testList = paginationService.paginatePassedTests(userId, thisPaginationParameter, thisPageNumber);
        }
        countOfPageNumberButtons = paginationService.countButtonsForPaginationPassedTests(userId, thisPaginationParameter);

        request.setAttribute("thisPaginationParameter", thisPaginationParameter);
        request.setAttribute("thisPageNumber", thisPageNumber);
        request.setAttribute("countOfPageNumberButtons", countOfPageNumberButtons);

        request.setAttribute("userId", userId);
        request.setAttribute("testList", testList);
        if (request.getSession().getAttribute("error") != null) {
            request.setAttribute("error", request.getSession().getAttribute("error"));
            request.getSession().removeAttribute("error");
        }
        if (request.getRequestURI().contains("/admin/userTests")) {
            return Path.ADMIN_USER_TESTS;
        }
        return Path.REDIRECT_ADMIN_USER_TESTS;
    }

}
