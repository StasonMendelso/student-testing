package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class BlockUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        DaoFactory factory = DaoFactory.getInstance();
        UserDao userDao = factory.createUserDao();
        userDao.block(Integer.parseInt(request.getParameter("id")));
        return "redirect:/web-application/testing/admin/showUsers";
    }
}
