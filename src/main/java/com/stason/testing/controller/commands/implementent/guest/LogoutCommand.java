package com.stason.testing.controller.commands.implementent.guest;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String login = (String)request.getSession().getAttribute("login");
        HashSet<String> loggedUsers = new HashSet<>();
        loggedUsers =(HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
        System.out.println(loggedUsers);
        loggedUsers.remove(login);
        System.out.println(loggedUsers);
        request.getSession().getServletContext().setAttribute("loggedUsers",loggedUsers);

        //Сохраняем язык чтобы не сменился когда сессия станет невалидной.
        String lang = (String) request.getSession().getAttribute("lang");
        request.getSession().invalidate();
        request.getSession().setAttribute("role", Role.GUEST.name());
        request.getSession().setAttribute("lang",lang);

        return Path.REDIRECT_GUEST_LOGOUT;
    }
}
