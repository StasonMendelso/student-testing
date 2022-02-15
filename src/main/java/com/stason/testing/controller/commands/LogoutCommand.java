package com.stason.testing.controller.commands;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        String login = (String)request.getSession().getAttribute("login");
        HashSet<String> loggedUsers = new HashSet<>();
        loggedUsers =(HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
        System.out.println(loggedUsers);
        loggedUsers.remove(login);
        System.out.println(loggedUsers);
        request.getSession().getServletContext().setAttribute("loggedUsers",loggedUsers);
        request.getSession().setAttribute("role","guest");
        //Сохраняем язык чтобы не сменился когда сессия станет невалидной.
        String lang = (String) request.getSession().getAttribute("lang");
        request.getSession().invalidate();
        request.getSession().setAttribute("lang",lang);

        return "redirect:/testing";
    }
}
