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
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.stason.testing.controller.utils.ErrorForUser.*;
/**
 * It is a Command, which login in the user
 * @author Stanislav Hlova
 * @version 1.0
 */
public class LoginCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class.getName());
    private final UserService userService = new UserService();
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("login") != null && request.getParameter("g-recaptcha-response") != null) {
            String login = EncodingConverter.convertFromISOtoUTF8(request.getParameter("login"));
            String password = EncodingConverter.convertFromISOtoUTF8(request.getParameter("password"));
            List<ErrorForUser> errors = new ArrayList<>();
            if (!ValidatorService.validateEmail(login)) {
                errors.add(INVALID_LOGIN);
            }
            if (!ValidatorService.validatePassword(password)) {
                errors.add(INVALID_PASSWORD);
            }
            //CAPTCHA
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            // Verify CAPTCHA
            if (!VerifyRecaptcha.verify(gRecaptchaResponse)) {
                errors.add(INVALID_CAPTCHA);
            }
            if (!errors.isEmpty()) {
                request.setAttribute("errorsList", errors);
                return Path.GUEST_LOGIN;
            }
            if (userService.checkLogin(login)) {
                Set<String> loggedUsers = new HashSet<>();

                if (request.getServletContext().getAttribute("loggedUsers") != null) {
                    loggedUsers = (HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
                    if (loggedUsers.stream().anyMatch(login::equals)) {
                        errors.add(ACCOUNT_IS_LOGGED);
                        request.setAttribute("errorsList", errors);
                        return Path.GUEST_LOGIN;
                    }
                }
                User user = userService.findByLogin(login);
                if (user.isBlocked()) {
                    errors.add(ACCOUNT_IS_BLOCKED);
                    request.setAttribute("errorsList", errors);
                    return Path.GUEST_LOGIN;
                }
                if (Objects.equals(EncryptionPassword.hash(password, user.getSalt()), user.getPassword())) {
                    loggedUsers.add(login);
                    request.getServletContext().setAttribute("loggedUsers", loggedUsers);
                    logger.info("Logged users are " + Arrays.toString(loggedUsers.toArray()));
                    HttpSession session = request.getSession();
                    session.setAttribute("role", user.getRole());
                    session.setAttribute("login", user.getLogin());
                    session.setAttribute("name", user.getName());
                    session.setAttribute("surname", user.getSurname());
                    session.setAttribute("id", user.getId());
                    session.setAttribute("idOfPassedTests", userService.findIdPassedTestsByUserId(user.getId()));

                    if (user.getRole().equals(Role.ADMIN.name())) return Path.REDIRECT_ADMIN_INFO;
                    if (user.getRole().equals(Role.STUDENT.name())) return Path.REDIRECT_STUDENT_INFO;
                }
            }
            errors.add(ACCOUNT_NOT_FOUND);
            request.setAttribute("errorsList", errors);
        }
        return Path.GUEST_LOGIN;
    }
}
