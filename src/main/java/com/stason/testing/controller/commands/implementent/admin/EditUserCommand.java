package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class EditUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(request.getParameter("id")!=null){
            request.getSession().setAttribute("idUser",request.getParameter("id"));
        }

        if(request.getRequestURI().contains("/admin/editUser") && !request.getRequestURI().contains("?")){
            DaoFactory factory = DaoFactory.getInstance();
            UserDao userDao = factory.createUserDao();
            User user = userDao.findById(Integer.parseInt((String)request.getSession().getAttribute("idUser")));
            request.getSession().removeAttribute("idUser");

            request.setAttribute("user",user);

            return "/WEB-INF/view/admin/editUser.jsp";
        }else{
            return "redirect:/web-application/testing/admin/editUser";
        }
    }
}
