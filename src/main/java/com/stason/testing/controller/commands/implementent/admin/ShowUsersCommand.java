package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.controller.utils.CommandsHelper;
import com.stason.testing.controller.utils.Path;

import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;


import java.util.List;

public class ShowUsersCommand implements Command {
    final PaginationService paginationService = new PaginationService();

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getRequestURI().contains("admin/showUser")) {

            int paginationParameter = CommandsHelper.getPaginationParameter(request, "paginationParameter");
            int pageNumber = CommandsHelper.getPageNumber(request, "pageNumber");

            List<User> list = paginationService.paginateAllUsers(paginationParameter, pageNumber);
            int countOfPageNumberButtons = paginationService.countButtonsForPaginationAllUsers(paginationParameter);

            if (list.isEmpty() && pageNumber > 1) {
                pageNumber--;
                list = paginationService.paginateAllUsers(paginationParameter, pageNumber);
            }

            request.setAttribute("countOfPageNumberButtons", countOfPageNumberButtons);
            request.setAttribute("paginationParameter", paginationParameter);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("userList", list);

            if (request.getSession().getAttribute("error") != null) {
                request.setAttribute("error", request.getSession().getAttribute("error"));
                request.getSession().removeAttribute("error");
            }

            return Path.ADMIN_USERS;
        } else {
            return Path.REDIRECT_ADMIN_USERS;
        }
    }

}
