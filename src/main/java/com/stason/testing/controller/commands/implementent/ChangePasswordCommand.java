package com.stason.testing.controller.commands.implementent;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EmailSender;
import com.stason.testing.controller.utils.EncryptionLink;
import com.stason.testing.controller.utils.EncryptionPassword;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class ChangePasswordCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(request.getParameterMap().isEmpty()) {
            String activationLink = "http://localhost:8080/web-application/testing/role/changePassword";
            System.out.println(request.getSession().getAttribute("role"));
            System.out.println(Role.ADMIN);
            System.out.println(request.getSession().getAttribute("role").equals(Role.ADMIN.name()));
            if (request.getSession().getAttribute("role").equals(Role.ADMIN.name())) activationLink = activationLink.replaceAll("role", "admin");
            if (request.getSession().getAttribute("role").equals(Role.STUDENT.name())) activationLink = activationLink.replaceAll("role", "student");
            String login = (String) request.getSession().getAttribute("login");
            //send message
            EmailSender sender = new EmailSender();
            String salt = EncryptionLink.generateSalt();
            String hashedLink = EncryptionLink.hash(login,salt);
            activationLink+="?identification="+hashedLink;
            sender.sendActivationPasswordLink(login,activationLink);
            request.getSession().setAttribute("identification",hashedLink);
            return Path.CHANGE_PASSWORD;
        }
        if(request.getParameter("identification")!=null){
            String identificationFromUser = request.getParameter("identification");
            String identification = (String) request.getSession().getAttribute("identification");
            System.out.println(identificationFromUser);
            System.out.println(identification);
            if(!identification.equals(identificationFromUser)) return Path.CHANGE_PASSWORD_ERROR_IDENTIFICATION;
            return Path.RECOVERY_CREATE_NEW_PASSWORD;
        }
        if(request.getParameter("password")!=null && request.getParameter("repeatedPassword")!=null){
            String password = request.getParameter("password");
            String repeatedPassword = request.getParameter("repeatedPassword");
            //Validate
            //Check password
            if(!password.equals(repeatedPassword)){
                request.setAttribute("error","Паролі не співпадають");
                return Path.RECOVERY_CREATE_NEW_PASSWORD;
            }
            DaoFactory daoFactory = DaoFactory.getInstance();
            UserDao userDao = daoFactory.createUserDao();
            String email = (String) request.getSession().getAttribute("login");
            String salt = EncryptionPassword.generateSalt();
            String hashedPassword = EncryptionPassword.hash(password,salt);
            userDao.updatePassword(email,hashedPassword,salt);
            request.getSession().removeAttribute("login");
            return Path.CHANGE_PASSWORD_SUCCESS;
        }
        return Path.GUEST_LOGIN;
    }
}
