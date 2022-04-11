package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.utils.Constants;
import com.stason.testing.controller.utils.Path;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 * It is a Command, which deletes a test
 * @author Stanislav Hlova
 * @version 1.0
 */
public class DeleteTestCommand implements com.stason.testing.controller.commands.Command {
    private static final Logger logger = Logger.getLogger(DeleteTestCommand.class.getName());
    private final TestService testService = new TestService();

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("secretPassword") == null) {
            return Path.REDIRECT_ADMIN_USERS;
        } else {
            String secretPassword = request.getParameter("secretPassword");
            if (secretPassword.equals(Constants.PASSWORD_DELETE_TEST)) {
                int id = -1;
                if (request.getParameter("id") != null) {
                    id = Integer.parseInt(request.getParameter("id"));
                }
                testService.delete(id);
                logger.info("Admin deleted test[" + id + "]");
                setAttributes(request);
            } else {
                setAttributes(request);
                request.getSession().setAttribute("error", "Секретний код не співпадає");
                return Path.REDIRECT_ADMIN_TESTS;
            }
        }
        return Path.REDIRECT_ADMIN_TESTS;
    }

    private void setAttributes(HttpServletRequest request) {
        request.getSession().setAttribute("pageNumber", request.getParameter("pageNumber"));
        request.getSession().setAttribute("paginationParameter", request.getParameter("paginationParameter"));
        request.getSession().setAttribute("orderBy", request.getParameter("orderBy"));
        request.getSession().setAttribute("order", request.getParameter("order"));
        request.getSession().setAttribute("discipline", request.getParameter("discipline"));
    }
}
