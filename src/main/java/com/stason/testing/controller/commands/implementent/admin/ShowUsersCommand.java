package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ShowUsersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String showUsersUrl = "redirect:/web-application/testing/admin/showUsers";
        if(request.getRequestURI().contains("admin/showUser")) {
            //1.Запрос к БД
            DaoFactory factory = DaoFactory.getInstance();
            UserDao userDao = factory.createUserDao();

            List<User> list = userDao.findAll();
            //2.Ставим список юзеров как атрибут сессии. После в фильтре будет удалятся атрибут.
            request.setAttribute("userList", list);
            return "/WEB-INF/view/admin/showUsers.jsp";
        }else{
            return showUsersUrl;
        }
    }
}
