package com.stason.testing.controller.commands.implementent.guest;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.EncryptionPassword;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.services.ValidatorService;
import com.stason.testing.controller.services.VerifyRecaptcha;
import com.stason.testing.controller.utils.*;
import com.stason.testing.model.entity.Role;
import com.stason.testing.model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
/**
 * It is a Command for registration a new user
 * @author Stanislav Hlova
 * @version 1.0
 */
public class RegistrationCommand implements Command {
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("login") != null) {
            //1.Validate
            //2.CheckUser
            //3.Register
            List<ErrorForUser> errors = new ArrayList<>();
            //Convert to UTF-8
            String email = EncodingConverter.convertFromISOtoUTF8(request.getParameter("login"));
            String username = EncodingConverter.convertFromISOtoUTF8(request.getParameter("name"));
            String surname = EncodingConverter.convertFromISOtoUTF8(request.getParameter("surname"));
            String password = EncodingConverter.convertFromISOtoUTF8(request.getParameter("password"));
            String repeatedPassword = EncodingConverter.convertFromISOtoUTF8(request.getParameter("repeated-password"));

            //Validate
            if (!ValidatorService.validateEmail(email))
                errors.add(ErrorForUser.INVALID_LOGIN);
            if (!ValidatorService.validateUsername(username))
                errors.add(ErrorForUser.INVALID_NAME);
            if (!ValidatorService.validateSurname(surname))
                errors.add(ErrorForUser.INVALID_SURNAME);
            if (!ValidatorService.validatePassword(password) && !ValidatorService.validatePassword(repeatedPassword))
                errors.add(ErrorForUser.INVALID_PASSWORD);
            if (!ValidatorService.isPasswordRepeated(password, repeatedPassword))
                errors.add(ErrorForUser.PASSWORD_NOT_MATCH);
            //CAPTCHA
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

            // Verify CAPTCHA.
            if (!VerifyRecaptcha.verify(gRecaptchaResponse))
                errors.add(ErrorForUser.INVALID_CAPTCHA);
            if (!errors.isEmpty()) {
                request.setAttribute("errorsList", errors);
                return Path.GUEST_REGISTER;
            }
            //CheckUser
            UserService userService = new UserService();
            if (userService.checkLogin(email)) {
                errors.add(ErrorForUser.ACCOUNT_IS_BUSY);
                request.setAttribute("errorsList", errors);
                return Path.GUEST_REGISTER;
            }
            //Encrypting password
            String salt = EncryptionPassword.generateSalt();
            String hashedPassword = EncryptionPassword.hash(password, salt);
            //Создаем юзера
            User user = new User(email, hashedPassword, salt, username, surname, Role.STUDENT.getId(), false);

            //заносим юзера в базу
            if (!userService.createNewUser(user)) return Path.GUEST_UNSUCCESSFUL_REGISTER;

            logger.info("Registered new user: " + surname + " " + username + " " + email);
            return Path.GUEST_SUCCESSFUL_REGISTER;
        }
        return Path.GUEST_REGISTER;

    }
}
