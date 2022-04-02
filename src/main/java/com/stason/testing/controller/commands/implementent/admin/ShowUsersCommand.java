package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.controller.utils.Path;

import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;


import java.util.List;

public class ShowUsersCommand implements Command {
    final PaginationService paginationService = new PaginationService();
    @Override
    public String execute(HttpServletRequest request){
        if(request.getRequestURI().contains("admin/showUser")) {

            int paginationParameter = getPaginationParameter(request, "paginationParameter");
            int pageNumber = getPageNumber(request, "pageNumber");

            List<User> list =  paginationService.paginateAllUsers(paginationParameter,pageNumber);
            int countOfPageNumberButtons = paginationService.countButtonsForPaginationAllUsers(paginationParameter);

            if(list.isEmpty() && pageNumber>1){
                pageNumber--;
                list =  paginationService.paginateAllUsers(paginationParameter,pageNumber);
            }

            request.setAttribute("countOfPageNumberButtons",countOfPageNumberButtons);
            request.setAttribute("paginationParameter",paginationParameter);
            request.setAttribute("pageNumber",pageNumber);
            request.setAttribute("userList", list);

            if(request.getSession().getAttribute("error")!=null){
                request.setAttribute("error",request.getSession().getAttribute("error"));
                request.getSession().removeAttribute("error");
            }

            return Path.ADMIN_USERS;
        }else{
            return Path.REDIRECT_ADMIN_USERS;
        }
    }
    private int getPageNumber(HttpServletRequest request, String parameterName) {
        int pageNumber;
        if(request.getSession().getAttribute(parameterName)!=null){
            pageNumber = Integer.parseInt((String) request.getSession().getAttribute(parameterName));
            request.getSession().removeAttribute(parameterName);
            return pageNumber;

        }
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
        if(request.getSession().getAttribute(parameterName)!=null){
            paginationParameter = Integer.parseInt((String) request.getSession().getAttribute(parameterName));
            request.getSession().removeAttribute(parameterName);
            return paginationParameter;

        }
        if(request.getParameter(parameterName)!=null){
            paginationParameter = Integer.parseInt(request.getParameter(parameterName));
        }else{
            paginationParameter = 5;
        }
        return paginationParameter;
    }
}
