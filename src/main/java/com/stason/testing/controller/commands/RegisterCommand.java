package com.stason.testing.controller.commands;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class RegisterCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        //1.Validate
        //2.CheckUser
        //3.Register
        //1.Validate
        request.setCharacterEncoding("UTF-8");


        String email = request.getParameter("login");
        String username = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeated-password");
        System.out.println("Register command{\n"+email+"\n" + username +"\n"+ surname +"\n"+ password+"\n" + repeatedPassword+"\n}");
        System.out.println(request.getCharacterEncoding());

        return null;
    }
}
