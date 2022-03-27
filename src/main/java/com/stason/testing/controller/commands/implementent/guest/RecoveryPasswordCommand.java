package com.stason.testing.controller.commands.implementent.guest;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EmailSender;
import com.stason.testing.controller.utils.EncryptionPassword;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.controller.utils.VerifyRecaptcha;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.User;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class RecoveryPasswordCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(request.getParameterMap().isEmpty()){
            return Path.RECOVERY_EMAIL;
        }
        if(request.getParameter("email")!=null){
            //reCaptcha
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

            System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);

            // Verify CAPTCHA.
            boolean valid = false;
            try {
                valid = VerifyRecaptcha.verify(gRecaptchaResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!valid) {
                request.setAttribute("error","ReCaptcha is invalid");
                return Path.RECOVERY_EMAIL;
            }

            //Validate email
            //Check email
            //
            String email = request.getParameter("email");
            DaoFactory daoFactory = DaoFactory.getInstance();
            UserDao userDao = daoFactory.createUserDao();
            User user = new User();
            user.setLogin(email);
            if(!userDao.checkLogin(user)){
                request.setAttribute("error","Не знайдено такого користувача");
                return Path.RECOVERY_EMAIL;
            }
            StringBuffer activationCode = new StringBuffer();
            for(int i=1;i<=8;i++){
                int a = (int)(Math.random() *10);
                activationCode.append(a);
            }
            EmailSender sender = new EmailSender();
            sender.sendActivationCode(email,activationCode.toString());
            request.getSession().setAttribute("login", email);
            request.getSession().setAttribute("activationCode", activationCode.toString());
            return "redirect:/web-application/testing/recovery?activateCode=activate";
        }
        if(request.getParameter("activateCode")!=null){
            return Path.RECOVERY_ACTIVATION_CODE;
        }
        if(request.getParameter("activationCode")!=null){
            String activationCode= String.valueOf(request.getSession().getAttribute("activationCode"));
            String activationCodeFromUser = request.getParameter("activationCode");
            //Validate
            //Check activation code
            if(!activationCode.equals(activationCodeFromUser)){
                request.setAttribute("error","Невірний код активації");
                return Path.RECOVERY_ACTIVATION_CODE;
            }
            request.getSession().removeAttribute("activationCode");
            return "redirect:/web-application/testing/recovery?changePassword";
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
            return "redirect:/web-application/testing/recovery?successful=successful";
        }
        if(request.getParameter("successful")!=null) return Path.RECOVERY_SUCCESSFUL;
        if(request.getParameter("changePassword")!=null){
            return Path.RECOVERY_CREATE_NEW_PASSWORD;
        }
        return Path.RECOVERY_EMAIL;
    }
}
