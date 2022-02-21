package com.stason.testing.controller.commands;

import com.stason.testing.controller.services.EncodingConverter;
import com.stason.testing.controller.services.ValidatorService;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Role;
import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LoginCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {

        String studentUrl = "redirect:/web-application/testing/student/info";
        String adminUrl = "redirect:/web-application/testing/admin/info"; // Todo исправить на страничку с тестами?
        if(request.getParameter("login")==null){
            System.out.println("It is LoginCommand and forward /WEB-INF/view/guest/login.jsp");
            return "/WEB-INF/view/guest/login.jsp";
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
            if (errors.size() > 0) {
                request.setAttribute("errorsList", errors);
                return "/WEB-INF/view/guest/login.jsp";
            }

            User user = new User();
            user.setLogin(login);
            user.setPassword(password);

            DaoFactory factory = DaoFactory.getInstance();
            UserDao userDao = factory.createUserDao();

            if (userDao.checkLogin(user)) {
                HashSet<String> loggedUsers = new HashSet<>();
                boolean flag = true;
                if (request.getServletContext().getAttribute("loggedUsers") != null) {
                    loggedUsers = (HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
                    if (loggedUsers.stream().anyMatch(login::equals)) {
                        errors.add("Хтось інший вже увійшов під цим записом");//Todo сделать локализацию

                        request.setAttribute("errorsList", errors);
                        return "/WEB-INF/view/guest/login.jsp";
                    }
                }
                if (flag) {
                    loggedUsers.add(login);
                    request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
                    //беремо з базы юзера та встановлюємо відповідні атрибути
                    user = userDao.findByLogin(login);

                    request.getSession().setAttribute("role", user.getRole());
                    request.getSession().setAttribute("login", user.getLogin());
                    request.getSession().setAttribute("name", user.getName());
                    request.getSession().setAttribute("surname", user.getSurname());
                    request.getSession().setAttribute("id", user.getId());
                    //Временно
                    request.getSession().setAttribute("password", password);

                    if (user.getRole().equals(Role.ADMIN.name())) return adminUrl;
                    if (user.getRole().equals(Role.STUDENT.name())) return studentUrl;
                }

            } else {
                errors.add("Користувача не знайдено. Перевірте логін та пароль");//Todo сделать локализацию
                request.setAttribute("errorsList", errors);

                return "/WEB-INF/view/guest/login.jsp";
            }
            return "redirect:/testing";
        }else{
            return "/WEB-INF/view/guest/login.jsp";
        }
    }
}
