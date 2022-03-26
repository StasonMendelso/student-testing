package com.stason.testing.controller.commands.implementent.guest;

import com.mysql.cj.Session;
import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.*;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Role;
import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class LoginCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {

         if(request.getParameter("login")==null){
            System.out.println("It is LoginCommand and forward /WEB-INF/view/guest/login.jsp");
            return Path.GUEST_LOGIN;
        }
        if(request.getParameter("login")!=null) {
            String login = EncodingConverter.convertFromISOtoUTF8(request.getParameter("login"));
            String password = EncodingConverter.convertFromISOtoUTF8(request.getParameter("password"));
            List<String> errors = new ArrayList<>();

            if (!ValidatorService.validateEmail(login)) {
                errors.add("Невалідний логін");//Todo сделать локализацию
            }
            if (!ValidatorService.validatePassword(password)) {
                errors.add("Невалідний пароль");//Todo сделать локализацию
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
            if (errors.size() > 0) {
                request.setAttribute("errorsList", errors);
                return Path.GUEST_LOGIN;
            }

            User user = new User();
            user.setLogin(login);

            DaoFactory factory = DaoFactory.getInstance();
            UserDao userDao = factory.createUserDao();

            if (userDao.checkLogin(user)) {
                HashSet<String> loggedUsers = new HashSet<>();

                if (request.getServletContext().getAttribute("loggedUsers") != null) {
                    loggedUsers = (HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
                    System.out.println(loggedUsers);
                    if (loggedUsers.stream().anyMatch(login::equals)) {
                        errors.add("Хтось інший вже увійшов під цим записом");//Todo сделать локализацию

                        request.setAttribute("errorsList", errors);
                        return Path.GUEST_LOGIN;
                    }
                }

                    //беремо з базы юзера та встановлюємо відповідні атрибути
                    user = userDao.findByLogin(login);

                if(user.isBlocked()){
                    errors.add("Ти заблокований! Звернися до адміністрації");//Todo сделать локализацию

                    request.setAttribute("errorsList", errors);
                    return  Path.GUEST_LOGIN;
                }

                String salt = user.getSalt();
                System.out.println(salt);
                System.out.println(EncryptionPassword.hash(password, salt));
                System.out.println(user.getPassword());
                if(Objects.equals(EncryptionPassword.hash(password, salt), user.getPassword())) {

                    loggedUsers.add(login);
                    request.getServletContext().setAttribute("loggedUsers", loggedUsers);

                    HttpSession session = request.getSession();
                    session.setAttribute("role", user.getRole());
                    session.setAttribute("login", user.getLogin());
                    session.setAttribute("name", user.getName());
                    session.setAttribute("surname", user.getSurname());
                    session.setAttribute("id", user.getId());
                    List<Integer> idPassedTestsList = userDao.findIdPassedTestsByUserId(user.getId());
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
