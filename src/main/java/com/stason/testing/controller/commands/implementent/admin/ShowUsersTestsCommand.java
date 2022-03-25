package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ShowUsersTestsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        int userId =-1;
        int thisPaginationParameter =getPaginationParameter(request,"thisPaginationParameter");
        int thisPageNumber = getPageNumber(request,"thisPageNumber");
        int countOfPageNumberButtons;
        if(request.getParameter("id")!=null) {
            userId = Integer.parseInt(request.getParameter("id"));
        }

        List<Test> testList = PaginationService.paginatePassedTests(userId,thisPaginationParameter,thisPageNumber);
        if(testList.isEmpty() && thisPageNumber>1){
            thisPageNumber--;
            testList = PaginationService.paginatePassedTests(userId,thisPaginationParameter,thisPageNumber);
        }
        countOfPageNumberButtons = PaginationService.countButtonsForPaginationPassedTests(userId,thisPaginationParameter);

        request.setAttribute("thisPaginationParameter",thisPaginationParameter);
        request.setAttribute("thisPageNumber",thisPageNumber);
        request.setAttribute("countOfPageNumberButtons",countOfPageNumberButtons);

        request.setAttribute("userId",userId);
        request.setAttribute("testList",testList);
        if(request.getSession().getAttribute("error")!=null){
            request.setAttribute("error",request.getSession().getAttribute("error"));
            request.getSession().removeAttribute("error");
        }
        if(request.getRequestURI().contains("/admin/userTests")){
            return "/WEB-INF/view/admin/showUsersTests.jsp";
        }
        return "redirect:/web-application/testing/admin/usersTests";
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
