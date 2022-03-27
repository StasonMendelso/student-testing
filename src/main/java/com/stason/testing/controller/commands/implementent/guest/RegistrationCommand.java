package com.stason.testing.controller.commands.implementent.guest;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.utils.*;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Role;
import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(request.getParameter("login")!=null){

        //1.Validate
        //2.CheckUser
        //3.Register

        List<String> errors = new ArrayList<>();
        //Convert to UTF-8
        String email = EncodingConverter.convertFromISOtoUTF8(request.getParameter("login"));
        String username = EncodingConverter.convertFromISOtoUTF8(request.getParameter("name"));
        String surname = EncodingConverter.convertFromISOtoUTF8(request.getParameter("surname"));
        String password = EncodingConverter.convertFromISOtoUTF8(request.getParameter("password"));
        String repeatedPassword = EncodingConverter.convertFromISOtoUTF8(request.getParameter("repeated-password"));

        System.out.println("Register command{\n"+email+"\n" + username +"\n"+ surname +"\n"+ password+"\n" + repeatedPassword+"\n}");

        //Validate
        if(!ValidatorService.validateEmail(email)){
            errors.add("Введено невірний формат почти");
        }
        if(!ValidatorService.validateUsername(username)){
            errors.add("Ім'я повинно починатися з великої літери та має бути тільки з букв");
        }
        if(!ValidatorService.validateSurname(surname)){
            errors.add("The surname must be started with Uppercase and contains only alphabet symbols");
        }
        if(!ValidatorService.validatePassword(password)){
            errors.add("Пароль повинен містити одну цифру, одну верхню та нижню букви, один спец символ, та мати длинну від 8 до 20 символів");
        }
        if(!ValidatorService.isPasswordRepeated(password,repeatedPassword)){
            errors.add("Паролі не співпадають");
        }
            //CAPTCHA
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
                errors.add("Captcha invalid!");
            }
        if(errors.size()!=0){
            System.out.println(errors);
            request.setAttribute("errorsList", errors);
            request.getServletContext().setAttribute("errorsList", errors);
            return Path.GUEST_REGISTER;
        }
        //CheckUser
        //Создаем юзера
        User user = new User();
        user.setLogin(email);
        user.setName(username);
        user.setSurname(surname);
        user.setRole(Role.STUDENT);
        user.setBlocked(false);

        //Encrypting password
            String salt = EncryptionPassword.generateSalt();
            String hashedPassword = EncryptionPassword.hash(password,salt);

        user.setPassword(hashedPassword);
        user.setSalt(salt);
            UserService userService = new UserService();
        //Проверяем, нету ли такого юзера за логином
        if(userService.checkUser(user)){
            errors.add("Уже есть пользователь с данным логином. Введите другую почту!");
            System.out.println(errors);
            request.setAttribute("errorsList", errors);
            return Path.GUEST_REGISTER;
        }else{
            //заносим юзера в базу
            userService.createNewUser(user);

        }

            return Path.GUEST_SUCCESSFUL_REGISTER;
        }else {
            return Path.GUEST_REGISTER;
        }
    }
}
