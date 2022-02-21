package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Test;
import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ShowTestsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String showUsersUrl = "redirect:/web-application/testing/admin/showTests";
        if(request.getRequestURI().contains("admin/showTests")) {
            //1.Запрос к БД
            DaoFactory factory = DaoFactory.getInstance();
            TestDao testDao = factory.createTestDao();

            List<Test> list = testDao.findAll();
            //2.Ставим список юзеров как атрибут сессии. После в фильтре будет удалятся атрибут.
            request.setAttribute("testList", list);
            return "/WEB-INF/view/admin/showTests.jsp";
        }else{
            return showUsersUrl;
        }
    }
}
