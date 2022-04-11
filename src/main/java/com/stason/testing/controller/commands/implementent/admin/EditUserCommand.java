package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.services.ValidatorService;
import com.stason.testing.controller.utils.Constants;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.ErrorForUser;
import com.stason.testing.controller.utils.Path;

import com.stason.testing.model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * It is a Command, which edits user's information
 * @author Stanislav Hlova
 * @version 1.0
 */
public class EditUserCommand implements Command {
    private static final Logger logger = Logger.getLogger(EditUserCommand.class.getName());
    private final UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("id") != null) {
            request.getSession().setAttribute("idUser", request.getParameter("id"));
        }

        if (request.getParameter("surname") != null && request.getParameter("name") != null) {
            String surname = EncodingConverter.convertFromISOtoUTF8(request.getParameter("surname"));
            String name = EncodingConverter.convertFromISOtoUTF8(request.getParameter("name"));
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userService.findById(userId);
            //Validate
            List<ErrorForUser> errors = new ArrayList<>();
            if(!surname.isEmpty() && !ValidatorService.validateSurname(surname)){
                errors.add(ErrorForUser.INVALID_SURNAME);
            }
            if(!name.isEmpty() &&!ValidatorService.validateUsername(name)){
                errors.add(ErrorForUser.INVALID_NAME);
            }
            if(!errors.isEmpty()){
                request.setAttribute("errorsList",errors);
                request.setAttribute("user", user);
                return Path.ADMIN_EDIT_USER_INFO;
            }

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

                if (flag) {

                    if (!request.getParameter("surname").isEmpty()) {
                        user.setSurname(surname);
                    }
                    if (!request.getParameter("name").isEmpty()) {
                        user.setName(name);
                    }

                    userService.update(user);
                    logger.info("Admin updated information about user");
                    return Path.REDIRECT_ADMIN_USERS;
                } else {
                    request.setAttribute("user", user);
                    errors.add(ErrorForUser.SECRET_CODE_NOT_MATCH);
                    request.setAttribute("errorsList",errors);
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
