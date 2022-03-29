package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.utils.Path;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;

public class DeletePassedTestByUserCommand implements Command {
    private final  static Logger logger = Logger.getLogger(DeletePassedTestByUserCommand.class.getName());
    private final TestService testService = new TestService();
    @Override
    public String execute(HttpServletRequest request){
        String secretPassword = request.getParameter("secretPassword");
        int userId = Integer.parseInt(request.getParameter("userId"));
        if (secretPassword.equals("delete")) {

            int testId = Integer.parseInt(request.getParameter("testId"));

            testService.deletePassedTestById(testId);
            logger.info("Admin deleted passed test["+testId+"] in user ["+userId+"]");
        } else {
            request.getSession().setAttribute("error", "Секретний код не співпадає");//todo локалізація
        }
        request.getSession().setAttribute("thisPaginationParameter", request.getParameter("thisPaginationParameter"));
        request.getSession().setAttribute("thisPageNumber", request.getParameter("thisPageNumber"));
        return Path.REDIRECT_ADMIN_USER_TESTS + "?id=" + userId;
    }
}
