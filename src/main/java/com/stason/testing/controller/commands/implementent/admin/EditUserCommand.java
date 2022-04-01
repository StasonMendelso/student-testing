package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.utils.Constants;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.Path;

import com.stason.testing.model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EditUserCommand implements Command {
    private final static Logger logger = Logger.getLogger(EditUserCommand.class.getName());
    private final UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("id") != null) {
            request.getSession().setAttribute("idUser", request.getParameter("id"));
        }

        if (request.getParameter("surname") != null && request.getParameter("name") != null) {
            if (request.getRequestURI().contains("/admin/editUser") && (!request.getParameter("surname").isEmpty() || !request.getParameter("name").isEmpty())) {
                boolean flag = false;

                if (request.getParameter("secretPassword") == null) {
                    return Path.ADMIN_EDIT_USER_INFO;
                } else {
                    String secretPassword = request.getParameter("secretPassword");
                    if (secretPassword.equals(Constants.PASSWORD_SAVE_CHANGES_FOR_USER)) {
                        flag = true;
                    }
                }
                int userId = Integer.parseInt(request.getParameter("id"));
                User user = userService.findById(userId);
                if (flag) {

                    if (!request.getParameter("surname").isEmpty()) {
                        String surname = EncodingConverter.convertFromISOtoUTF8(request.getParameter("surname"));
                        user.setSurname(surname);
                    }
                    if (!request.getParameter("name").isEmpty()) {
                        String name = EncodingConverter.convertFromISOtoUTF8(request.getParameter("name"));
                        user.setName(name);
                    }

                    userService.update(user);
                    logger.info("Admin updated information about user");
                    return Path.REDIRECT_ADMIN_USERS;
                } else {
                    request.setAttribute("user", user);
                    request.setAttribute("error", "Секретний код не співпадає");
                    return Path.ADMIN_EDIT_USER_INFO;
                }
            }
        }
        if (request.getRequestURI().contains("/admin/editUser") && !request.getRequestURI().contains("?")) {
            int idUser = Integer.parseInt((String) request.getSession().getAttribute("idUser"));
            User user = userService.findById(idUser);
            request.getSession().removeAttribute("idUser");

            request.setAttribute("user", user);
            if (request.getParameter("pageNumber") != null)
                request.getSession().setAttribute("pageNumber", request.getParameter("pageNumber"));
            if (request.getParameter("paginationParameter") != null)
                request.getSession().setAttribute("paginationParameter", request.getParameter("paginationParameter"));
            return Path.ADMIN_EDIT_USER_INFO;
        } else {
            return Path.ADMIN_EDIT_USER;
        }
    }
}
