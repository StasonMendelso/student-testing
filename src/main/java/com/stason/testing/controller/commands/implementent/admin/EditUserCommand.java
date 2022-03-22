package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EncodingConverter;
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

        if(request.getParameter("surname")!=null && request.getParameter("name")!=null) {
            if (request.getRequestURI().contains("/admin/editUser") && (!request.getParameter("surname").isEmpty() || !request.getParameter("name").isEmpty())) {
                DaoFactory factory = DaoFactory.getInstance();
                UserDao userDao = factory.createUserDao();
                User user = userDao.findById(Integer.parseInt(request.getParameter("id")));
                if (!request.getParameter("surname").isEmpty()) {
                    String surname = EncodingConverter.convertFromISOtoUTF8(request.getParameter("surname"));
                    user.setSurname(surname);
                }
                if (!request.getParameter("name").isEmpty()) {
                    String name = EncodingConverter.convertFromISOtoUTF8(request.getParameter("name"));
                    user.setName(name);
                }
                userDao.update(user);

                return "redirect:/web-application/testing/admin/showUsers";
            }
        }
        if(request.getRequestURI().contains("/admin/editUser") && !request.getRequestURI().contains("?")){
            DaoFactory factory = DaoFactory.getInstance();
            UserDao userDao = factory.createUserDao();
            User user = userDao.findById(Integer.parseInt((String)request.getSession().getAttribute("idUser")));
            request.getSession().removeAttribute("idUser");

            request.setAttribute("user",user);
            request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
            request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
            return "/WEB-INF/view/admin/editUserInfo.jsp";
        }else{
            return "redirect:/web-application/testing/admin/editUser";
        }
    }
}
