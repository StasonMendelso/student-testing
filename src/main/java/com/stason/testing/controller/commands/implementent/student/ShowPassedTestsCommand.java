package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ShowPassedTestsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String uri = request.getRequestURI();
        if(uri.contains("/student/passedTests")){
            int userId = (int) request.getSession().getAttribute("id");
            DaoFactory factory = DaoFactory.getInstance();
            TestDao testDao = factory.createTestDao();

            List<Test> list = testDao.findPassedTests(userId);

            request.setAttribute("testList", list);
            return "/WEB-INF/view/student/showPassedTests.jsp";
        }else{
            return "redirect:/web-application/testing/student/passedTests";
        }
    }
}
