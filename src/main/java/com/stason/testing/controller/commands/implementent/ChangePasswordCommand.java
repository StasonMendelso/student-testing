package com.stason.testing.controller.commands.implementent;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.servlets.ControllerServlet;
import com.stason.testing.controller.utils.*;
import com.stason.testing.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangePasswordCommand implements Command {
    private final  static Logger logger = Logger.getLogger(ChangePasswordCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request){
        if(request.getParameterMap().isEmpty()) {
            String activationLink = Constants.ACTIVATION_LINK;
            if (request.getSession().getAttribute("role").equals(Role.ADMIN.name())) activationLink = activationLink.replaceAll("role", "admin");
            if (request.getSession().getAttribute("role").equals(Role.STUDENT.name())) activationLink = activationLink.replaceAll("role", "student");
            String login = (String) request.getSession().getAttribute("login");
            //send message
            if(request.getSession().getAttribute("identification")==null) {
                EmailSender sender = new EmailSender();
                //Generate identification
                String salt = EncryptionLink.generateSalt();
                String hashedLink = EncryptionLink.hash(login, salt);
                activationLink += "?identification=" + hashedLink;
                //Send Activation link to email for changing password
                sender.sendActivationPasswordLink(login, activationLink);
                request.getSession().setAttribute("identification", hashedLink);
                return Path.CHANGE_PASSWORD;
            }else{
                request.setAttribute("error", "The identification link was sent to your e-mail.");//Todo локалізацію
                return Path.CHANGE_PASSWORD;
            }
        }
        if(request.getParameter("password")!=null && request.getParameter("repeatedPassword")!=null){
            String password = request.getParameter("password");
            String repeatedPassword = request.getParameter("repeatedPassword");
            //Validate
            //Check password
            if(!password.equals(repeatedPassword)){
                request.setAttribute("error","Паролі не співпадають"); //Todo локалізацію
                return Path.RECOVERY_CREATE_NEW_PASSWORD;
            }
            String email = (String) request.getSession().getAttribute("login");
            String salt = EncryptionPassword.generateSalt();
            String hashedPassword = EncryptionPassword.hash(password,salt);

            UserService userService = new UserService();
            userService.updatePassword(email,hashedPassword,salt);

            String link = "redirect:/web-application/testing/role/changePassword";
            logger.info("User "+email+" changed password");
            if (request.getSession().getAttribute("role").equals(Role.ADMIN.name())) link = link.replaceAll("role", "admin");
            if (request.getSession().getAttribute("role").equals(Role.STUDENT.name())) link = link.replaceAll("role", "student");

            return link+"?success=success";
        }
        if(request.getParameter("success")!=null) return Path.CHANGE_PASSWORD_SUCCESS;
        if(request.getParameter("identification")!=null){
            String identificationFromUser = request.getParameter("identification");
            String identification = (String) request.getSession().getAttribute("identification");
            //Check identificationLink
            if(!identification.equals(identificationFromUser)){
                logger.info("Identification is not relevant");
                return Path.CHANGE_PASSWORD_ERROR_IDENTIFICATION;
            }
            return Path.RECOVERY_CREATE_NEW_PASSWORD;
        }

        return Path.GUEST_LOGIN;
    }
}
