package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class UnblockUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        DaoFactory factory = DaoFactory.getInstance();
        UserDao userDao = factory.createUserDao();
        userDao.unblock(Integer.parseInt(request.getParameter("id")));
        request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
        request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
        List<Integer> blockedList = (List<Integer>)request.getServletContext().getAttribute("blockedUsers");
        Integer id = Integer.valueOf(request.getParameter("id"));
        blockedList.remove(id);
        request.getServletContext().setAttribute("blockedUsers",blockedList);
        return "redirect:/web-application/testing/admin/showUsers";
    }
}
