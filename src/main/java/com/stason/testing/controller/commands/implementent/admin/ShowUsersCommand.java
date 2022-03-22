package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Test;
import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ShowUsersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String showUsersUrl = "redirect:/web-application/testing/admin/showUsers";
        if(request.getRequestURI().contains("admin/showUser")) {

            int countOfPageNumberButtons =0;
            int paginationParameter = getPaginationParameter(request, "paginationParameter");
            int pageNumber = getPageNumber(request, "pageNumber");
            List<User> list = PaginationService.paginateAllUsers(paginationParameter,pageNumber);
            countOfPageNumberButtons = PaginationService.countButtonsForPaginationAllUsers(paginationParameter);

            request.setAttribute("countOfPageNumberButtons",countOfPageNumberButtons);
            request.setAttribute("paginationParameter",paginationParameter);
            request.setAttribute("pageNumber",pageNumber);
            request.setAttribute("userList", list);
            return "/WEB-INF/view/admin/showUsers.jsp";
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
