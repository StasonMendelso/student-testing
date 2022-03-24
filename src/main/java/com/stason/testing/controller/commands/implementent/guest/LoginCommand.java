package com.stason.testing.controller.commands.implementent.guest;

import com.mysql.cj.Session;
import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.ValidatorService;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;
import com.stason.testing.model.entity.Role;
import com.stason.testing.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LoginCommand implements Command {


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

                if (request.getServletContext().getAttribute("loggedUsers") != null) {
                    loggedUsers = (HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
                    System.out.println(loggedUsers);
                    if (loggedUsers.stream().anyMatch(login::equals)) {
                        errors.add("Хтось інший вже увійшов під цим записом");//Todo сделать локализацию

                        request.setAttribute("errorsList", errors);
                        return "/WEB-INF/view/guest/login.jsp";
                    }
                }

                    //беремо з базы юзера та встановлюємо відповідні атрибути
                    user = userDao.findByLogin(login);

                if(user.isBlocked()){
                    errors.add("Ти заблокований! Звернися до адміністрації");//Todo сделать локализацию

                    request.setAttribute("errorsList", errors);
                    return "/WEB-INF/view/guest/login.jsp";
                }

                loggedUsers.add(login);
                request.getServletContext().setAttribute("loggedUsers", loggedUsers);

                 HttpSession session=request.getSession();
                    session.setAttribute("role", user.getRole());
                    session.setAttribute("login", user.getLogin());
                    session.setAttribute("name", user.getName());
                    session.setAttribute("surname", user.getSurname());
                    session.setAttribute("id", user.getId());
                    List<Integer> idPassedTestsList = userDao.findIdPassedTestsByUserId(user.getId());
                    user.setIdPassedTestList(idPassedTestsList);
                    session.setAttribute("idOfPassedTests", user.getIdPassedTestList());



                    if (user.getRole().equals(Role.ADMIN.name())) return adminUrl;
                    if (user.getRole().equals(Role.STUDENT.name())) return studentUrl;


            } else {
                errors.add("Користувача не знайдено. Перевірте логін та пароль");//Todo сделать локализацию
                request.setAttribute("errorsList", errors);

                return "/WEB-INF/view/guest/login.jsp";
            }
            return "redirect:/web-application/testing";
        }else{
            return "/WEB-INF/view/guest/login.jsp";
        }
    }
}
