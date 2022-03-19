package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class InfoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
       if(request.getRequestURI().contains("/student/info")){
           int userId = (int) request.getSession().getAttribute("id");
           DaoFactory factory = DaoFactory.getInstance();
           TestDao testDao = factory.createTestDao();

           List<Test> list = testDao.findPassedTests(userId);

           request.setAttribute("testList", list);
            return "/WEB-INF/view/student/info.jsp";
       }

        return "redirect:/web-application/testing/student/info";
    }
}
