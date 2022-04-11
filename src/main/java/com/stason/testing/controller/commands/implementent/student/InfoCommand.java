package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.PaginationService;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Test;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
/**
 * It is a Command, which shows main information and passed test to student
 * @author Stanislav Hlova
 * @version 1.0
 */
public class InfoCommand implements Command {
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
        int pageNumber = 1;
        if(request.getParameter("pageNumber")!=null && Integer.parseInt(request.getParameter("pageNumber")) > 0){
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        return pageNumber;
    }

    private int getPaginationParameter(HttpServletRequest request) {
        int paginationParameter = 5;
        if(request.getParameter("paginationParameter")!=null){
            paginationParameter = Integer.parseInt(request.getParameter("paginationParameter"));
        }
        return paginationParameter;
    }

}
