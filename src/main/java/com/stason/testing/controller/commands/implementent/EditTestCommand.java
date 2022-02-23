package com.stason.testing.controller.commands.implementent;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class EditTestCommand implements com.stason.testing.controller.commands.Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {

        if(request.getParameter("id")!=null && !request.getParameter("id").isEmpty()){
            DaoFactory factory = DaoFactory.getInstance();

        }

        if(request.getRequestURI().contains("admin/editTest")){
            if(request.getParameter("id")==null || request.getParameter("id").isEmpty()) return "redirect:/web-application/testing/admin/showTest";
            return "/WEB-INF/view/admin/editTest.jsp";
        }else {
            return "redirect:/web-application/testing/admin/editTest";
        }
    }
}
