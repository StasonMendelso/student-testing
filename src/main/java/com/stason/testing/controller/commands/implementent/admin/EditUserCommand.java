package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.Path;
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
                boolean flag = false;

                if(request.getParameter("secretPassword")==null){
                    return Path.ADMIN_EDIT_USER_INFO;
                }else{
                    String secretPassword =request.getParameter("secretPassword");
                    if(secretPassword.equals("save")){ // todo добавити константний клас з паролями
                        flag=true;
                    }
                }
                DaoFactory factory = DaoFactory.getInstance();
                UserDao userDao = factory.createUserDao();
                if(flag) {

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

                    return Path.REDIRECT_ADMIN_USERS;
                }else{
                    User user = userDao.findById(Integer.parseInt(request.getParameter("id")));
                    request.setAttribute("user",user);
                    request.setAttribute("error", "Секретний код не співпадає");
                    return Path.ADMIN_EDIT_USER_INFO;
                }
            }
        }
        if(request.getRequestURI().contains("/admin/editUser") && !request.getRequestURI().contains("?")){
            DaoFactory factory = DaoFactory.getInstance();
            UserDao userDao = factory.createUserDao();
            User user = userDao.findById(Integer.parseInt((String)request.getSession().getAttribute("idUser")));
            request.getSession().removeAttribute("idUser");

            request.setAttribute("user",user);
            if(request.getParameter("pageNumber")!=null)
            request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
            if(request.getParameter("paginationParameter")!=null)
            request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
            return Path.ADMIN_EDIT_USER_INFO;
        }else{
            return Path.ADMIN_EDIT_USER;
        }
    }
}
