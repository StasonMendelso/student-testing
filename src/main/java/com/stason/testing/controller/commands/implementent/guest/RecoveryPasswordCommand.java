package com.stason.testing.controller.commands.implementent.guest;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EmailSender;
import com.stason.testing.controller.utils.EncryptionPassword;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.User;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


public class RecoveryPasswordCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(request.getParameterMap().isEmpty()){
            return Path.RECOVERY_EMAIL;
        }
        if(request.getParameter("email")!=null){
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
            EmailSender.sendActivationCode(email,activationCode.toString());
            request.getSession().setAttribute("login", email);
            request.getSession().setAttribute("activationCode", activationCode.toString());
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
            return Path.RECOVERY_SUCCESSFUL;
        }

        return Path.RECOVERY_EMAIL;
    }
}
