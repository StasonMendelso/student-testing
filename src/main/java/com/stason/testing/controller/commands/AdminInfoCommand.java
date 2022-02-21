package com.stason.testing.controller.commands;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class AdminInfoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        return "/WEB-INF/view/admin/info.jsp";
    }
}
