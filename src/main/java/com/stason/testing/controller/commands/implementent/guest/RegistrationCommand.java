package com.stason.testing.controller.commands.implementent.guest;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.servlets.ControllerServlet;
import com.stason.testing.controller.utils.*;
import com.stason.testing.model.entity.Role;
import com.stason.testing.model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class RegistrationCommand implements Command {
    private final  static Logger logger = Logger.getLogger(RegistrationCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request){
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

        //Validate
        if(!ValidatorService.validateEmail(email)){
            errors.add("Введено невірний формат почти");//Todo локалізацію
        }
        if(!ValidatorService.validateUsername(username)){
            errors.add("Ім'я повинно починатися з великої літери та має бути тільки з букв");//Todo локалізацію
        }
        if(!ValidatorService.validateSurname(surname)){
            errors.add("The surname must be started with Uppercase and contains only alphabet symbols");//Todo локалізацію
        }
        if(!ValidatorService.validatePassword(password)){
            errors.add("Пароль повинен містити одну цифру, одну верхню та нижню букви, один спец символ, та мати длинну від 8 до 20 символів");//Todo локалізацію
        }
        if(!ValidatorService.isPasswordRepeated(password,repeatedPassword)){
            errors.add("Паролі не співпадають");//Todo локалізацію
        }
            //CAPTCHA
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

            // Verify CAPTCHA.
            boolean valid = false;
            valid = VerifyRecaptcha.verify(gRecaptchaResponse);
            if (!valid) {
                errors.add("Captcha invalid!");//Todo локалізацію
            }
        if(errors.size()!=0){
            request.setAttribute("errorsList", errors);
            request.getServletContext().setAttribute("errorsList", errors);
            return Path.GUEST_REGISTER;
        }
        //CheckUser
            UserService userService = new UserService();
            if(userService.checkLogin(email)){
                errors.add("Уже есть пользователь с данным логином. Введите другую почту!");//Todo локалізацію
                request.setAttribute("errorsList", errors);
                return Path.GUEST_REGISTER;
            }
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

            //заносим юзера в базу
            if( !userService.createNewUser(user)){
                return Path.GUEST_UNSUCCESSFUL_REGISTER;
            }
        logger.info("Registered new user: "+surname+" "+username+" "+email);
            return Path.GUEST_SUCCESSFUL_REGISTER;
        }else {
            return Path.GUEST_REGISTER;
        }
    }
}
