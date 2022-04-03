package com.stason.testing.controller.commands.implementent.guest;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.*;
import com.stason.testing.controller.utils.ErrorForUser;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.User;


import javax.servlet.http.HttpServletRequest;


public class RecoveryPasswordCommand implements Command {
    @Override
    public String execute(HttpServletRequest request){
        if(request.getParameterMap().isEmpty()){
            return Path.RECOVERY_EMAIL;
        }
        if(request.getParameter("email")!=null){
            //reCaptcha
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

            System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);

            // Verify CAPTCHA.
            boolean valid = false;
            valid = VerifyRecaptcha.verify(gRecaptchaResponse);
            if (!valid) {
                request.setAttribute("error", ErrorForUser.INVALID_CAPTCHA);
                return Path.RECOVERY_EMAIL;
            }
            String email = request.getParameter("email");
            //Validate email
            if(!ValidatorService.validateEmail(email)){
                request.setAttribute("error",ErrorForUser.INVALID_LOGIN);
                return Path.RECOVERY_EMAIL;
            }
            //Check email

            UserService userService = new UserService();
            if(!userService.checkLogin(email)){
                request.setAttribute("error",ErrorForUser.ACCOUNT_NOT_FOUND);
                return Path.RECOVERY_EMAIL;
            }
            StringBuffer activationCode = new StringBuffer();
            for(int i=1;i<=8;i++){
                int a = (int)(Math.random() *10);
                activationCode.append(a);
            }
            EmailSenderService sender = new EmailSenderService();
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
            if(!ValidatorService.validateActivationCode(activationCodeFromUser)){
                request.setAttribute("error",ErrorForUser.INVALID_ACTIVATION_CODE);
                return Path.RECOVERY_ACTIVATION_CODE;
            }
            //Check activation code
            if(!activationCode.equals(activationCodeFromUser)){
                request.setAttribute("error",ErrorForUser.INCORRECT_ACTIVATION_CODE);
                return Path.RECOVERY_ACTIVATION_CODE;
            }
            request.getSession().removeAttribute("activationCode");
            return "redirect:/web-application/testing/recovery?changePassword";
        }

        if(request.getParameter("password")!=null && request.getParameter("repeatedPassword")!=null){
            String password = request.getParameter("password");
            String repeatedPassword = request.getParameter("repeatedPassword");
            //Validate
            if(!ValidatorService.validatePassword(password) && !ValidatorService.validatePassword(repeatedPassword)){
                request.setAttribute("error",ErrorForUser.INVALID_PASSWORD);
                return Path.RECOVERY_CREATE_NEW_PASSWORD;
            }
            //Check password
            if(!ValidatorService.isPasswordRepeated(password,repeatedPassword)){
                request.setAttribute("error",ErrorForUser.PASSWORD_NOT_MATCH);
                return Path.RECOVERY_CREATE_NEW_PASSWORD;
            }
            UserService userService = new UserService();
            String email = (String) request.getSession().getAttribute("login");
            userService.updatePassword(email,password);
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
