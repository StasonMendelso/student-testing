package com.stason.testing.controller.commands;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class StudentInfoCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
       if(request.getRequestURI().contains("/student/info")){
            return "/WEB-INF/view/student/info.jsp";
       }

        return "redirect:/web-application/testing/student/info";
    }
}
