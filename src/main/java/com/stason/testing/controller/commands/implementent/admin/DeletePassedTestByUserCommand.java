package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.utils.Path;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class DeletePassedTestByUserCommand implements Command {
    private final TestService testService = new TestService();
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String secretPassword = request.getParameter("secretPassword");
        int userId = Integer.parseInt(request.getParameter("userId"));
        if (secretPassword.equals("delete")) {

            int testId = Integer.parseInt(request.getParameter("testId"));

            testService.deletePassedTestById(testId);
        } else {
            request.getSession().setAttribute("error", "Секретний код не співпадає");
        }
        request.getSession().setAttribute("thisPaginationParameter", request.getParameter("thisPaginationParameter"));
        request.getSession().setAttribute("thisPageNumber", request.getParameter("thisPageNumber"));
        return Path.REDIRECT_ADMIN_USER_TESTS + "?id=" + userId;
    }
}
