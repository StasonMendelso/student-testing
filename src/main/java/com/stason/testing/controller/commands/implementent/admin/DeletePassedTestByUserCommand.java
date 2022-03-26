package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class DeletePassedTestByUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String secretPassword = request.getParameter("secretPassword");
        int userId = Integer.parseInt(request.getParameter("userId"));
        if (secretPassword.equals("delete")) {

            int testId = Integer.parseInt(request.getParameter("testId"));
            DaoFactory daoFactory = DaoFactory.getInstance();
            TestDao testDao = daoFactory.createTestDao();
            testDao.deletePassedTestById(testId);
        } else {
            request.getSession().setAttribute("error", "Секретний код не співпадає");
        }
        request.getSession().setAttribute("thisPaginationParameter", request.getParameter("thisPaginationParameter"));
        request.getSession().setAttribute("thisPageNumber", request.getParameter("thisPageNumber"));
        return Path.REDIRECT_ADMIN_USER_TESTS + "?id=" + userId;
    }
}
