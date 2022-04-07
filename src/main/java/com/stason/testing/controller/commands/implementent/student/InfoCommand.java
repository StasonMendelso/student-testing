package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Test;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

public class InfoCommand implements Command {
    private static final Logger logger = Logger.getLogger(InfoCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request) {
        if(request.getRequestURI().contains("/student/info")){
            int userId = (int) request.getSession().getAttribute("id");
            //pagination
            int paginationParameter = getPaginationParameter(request);
            int pageNumber = getPageNumber(request);
            PaginationService paginationService = new PaginationService();
            List<Test> list = paginationService.paginatePassedTests(userId,paginationParameter,pageNumber);
            int countOfPageNumberButtons = paginationService.countButtonsForPaginationPassedTests(userId,paginationParameter);

            //end pagination
            request.setAttribute("testList", list);
            request.setAttribute("countOfPageNumberButtons",countOfPageNumberButtons);
            request.setAttribute("paginationParameter",paginationParameter);
            request.setAttribute("pageNumber",pageNumber);

            return Path.STUDENT_INFO;
        }

        return Path.REDIRECT_STUDENT_INFO;
    }

    private int getPageNumber(HttpServletRequest request) {
        int pageNumber;
        if(request.getParameter("pageNumber")!=null){
            if(Integer.parseInt(request.getParameter("pageNumber"))<=0) {
                pageNumber=1;
            }else{
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            }
        }else{
            pageNumber = 1;
        }
        return pageNumber;
    }

    private int getPaginationParameter(HttpServletRequest request) {
        int paginationParameter;
        if(request.getParameter("paginationParameter")!=null){
            paginationParameter = Integer.parseInt(request.getParameter("paginationParameter"));
        }else{
            paginationParameter = 5;
        }
        return paginationParameter;
    }

}
