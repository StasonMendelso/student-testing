package com.stason.testing.controller.commands;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LoginCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {

        String url = "redirect:student/info";
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        HashSet<String> loggedUsers = new HashSet<>();
        if(request.getServletContext().getAttribute("loggedUsers")!=null){
            loggedUsers =(HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
        }
        if(loggedUsers.stream().anyMatch(login::equals)){
            return "redirect:/testing";
        }else{
            loggedUsers.add(login);
            request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
            request.getSession().setAttribute("role","student");
            request.getSession().setAttribute("login", login);
            //Временно
            request.getSession().setAttribute("password",password);
            return url;
        }

    }
}
