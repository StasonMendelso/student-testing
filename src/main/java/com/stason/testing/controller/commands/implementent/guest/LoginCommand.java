package com.stason.testing.controller.commands.implementent.guest;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.utils.*;

import com.stason.testing.model.entity.Role;
import com.stason.testing.model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class LoginCommand implements Command {
    private final  static Logger logger = Logger.getLogger(LoginCommand.class.getName());


    @Override
    public String execute(HttpServletRequest request) {

         if(request.getParameter("login")==null){
            return Path.GUEST_LOGIN;
        }
        if(request.getParameter("login")!=null) {
            String login = EncodingConverter.convertFromISOtoUTF8(request.getParameter("login"));
            String password = EncodingConverter.convertFromISOtoUTF8(request.getParameter("password"));
            List<String> errors = new ArrayList<>();

            if (!ValidatorService.validateEmail(login)) {
                errors.add("Невалідний логін");//Todo сделать локализацию передавати key для fmt або сделать свой тег
            }
            if (!ValidatorService.validatePassword(password)) {
                errors.add("Невалідний пароль");//Todo сделать локализацию
            }
            //CAPTCHA
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

            // Verify CAPTCHA.
            boolean valid = false;
            valid = VerifyRecaptcha.verify(gRecaptchaResponse);
            if (!valid) {
                errors.add("Captcha invalid!");
            }
            if (errors.size() > 0) {
                request.setAttribute("errorsList", errors);
                return Path.GUEST_LOGIN;
            }

            User user = new User();
            user.setLogin(login);

            UserService userService = new UserService();

            if (userService.checkLogin(login)) {
                HashSet<String> loggedUsers = new HashSet<>();

                if (request.getServletContext().getAttribute("loggedUsers") != null) {
                    loggedUsers = (HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
                    if (loggedUsers.stream().anyMatch(login::equals)) {
                        errors.add("Хтось інший вже увійшов під цим записом");//Todo сделать локализацию

                        request.setAttribute("errorsList", errors);
                        return Path.GUEST_LOGIN;
                    }
                }
                    //беремо з базы юзера та встановлюємо відповідні атрибути
                    user = userService.findByLogin(login);

                if(user.isBlocked()){
                    errors.add("Ти заблокований! Звернися до адміністрації");//Todo сделать локализацию

                    request.setAttribute("errorsList", errors);
                    return  Path.GUEST_LOGIN;
                }
                String salt = user.getSalt();
                if(Objects.equals(EncryptionPassword.hash(password, salt), user.getPassword())) {

                    loggedUsers.add(login);
                    request.getServletContext().setAttribute("loggedUsers", loggedUsers);
                    logger.info("Logged users are "+ Arrays.toString(loggedUsers.toArray()));

                    HttpSession session = request.getSession();
                    session.setAttribute("role", user.getRole());
                    session.setAttribute("login", user.getLogin());
                    session.setAttribute("name", user.getName());
                    session.setAttribute("surname", user.getSurname());
                    session.setAttribute("id", user.getId());
                    List<Integer> idPassedTestsList = userService.findIdPassedTestsByUserId(user.getId());
                    user.setIdPassedTestList(idPassedTestsList);
                    session.setAttribute("idOfPassedTests", user.getIdPassedTestList());


                    if (user.getRole().equals(Role.ADMIN.name())) return Path.REDIRECT_ADMIN_INFO;
                    if (user.getRole().equals(Role.STUDENT.name())) return Path.REDIRECT_STUDENT_INFO;
                }else{
                    errors.add("Користувача не знайдено. Перевірте логін та пароль");//Todo сделать локализацию
                    request.setAttribute("errorsList", errors);

                    return  Path.GUEST_LOGIN;
                }

            } else {
                errors.add("Користувача не знайдено. Перевірте логін та пароль");//Todo сделать локализацию
                request.setAttribute("errorsList", errors);

                return  Path.GUEST_LOGIN;
            }
            return Path.REDIRECT_GUEST_LOGIN;
        }else{
            return  Path.GUEST_LOGIN;
        }
    }
}
