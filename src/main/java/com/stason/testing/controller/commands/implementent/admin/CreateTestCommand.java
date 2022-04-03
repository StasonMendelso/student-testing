package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.services.ValidatorService;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.ErrorForUser;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Test;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


public class CreateTestCommand implements Command {
    private final static Logger logger = Logger.getLogger(CreateTestCommand.class.getName());
    private List<ErrorForUser> errorForUserList = new ArrayList();

    @Override
    public String execute(HttpServletRequest request) {

        errorForUserList.clear();
        if (request.getParameter("testName") != null) {
            String name = EncodingConverter.convertFromISOtoUTF8(request.getParameter("testName"));
            String disciplineName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("disciplineName"));
            int difficulty = Integer.parseInt(request.getParameter("difficulty"));
            int duration = Integer.parseInt(request.getParameter("duration"));
            //Validation
            String url = validate(name, disciplineName, difficulty, duration);
            if (url.contains(Path.ADMIN_CREATE_TEST)) {

                request.setAttribute("errorsList", errorForUserList);
                return url;
            }
            //check in dao testname
            TestService testService = new TestService();
            if (testService.checkTestName(name)) {
                errorForUserList.add(ErrorForUser.SUCH_TEST_NAME_ALREADY_EXISTS);
                request.setAttribute("errorsList", errorForUserList);
                return Path.ADMIN_CREATE_TEST;
            }
            //todo constructors?
            Test test = new Test();
            test.setName(name);
            test.setNameOfDiscipline(disciplineName);
            test.setDifficulty(difficulty);
            test.setTimeMinutes(duration);
            request.getSession().setAttribute("test", test);
            logger.info("Admin creates new test");
            return Path.REDIRECT_ADMIN_CREATE_QUESTION;
        }

        if (request.getRequestURI().contains("/createTest")) {
            return Path.ADMIN_CREATE_TEST;
        } else {
            return Path.REDIRECT_ADMIN_CREATE_TEST;
        }
    }

    private String validate(String name, String disciplineName, int difficulty, int duration) {
        if (!ValidatorService.validateTestName(name)) errorForUserList.add(ErrorForUser.INVALID_TEST_NAME);
        if (!ValidatorService.validateTestDisciplineName(disciplineName))
            errorForUserList.add(ErrorForUser.INVALID_DISCIPLINE_NAME);
        if (!ValidatorService.validateTestDifficulty(difficulty))
            errorForUserList.add(ErrorForUser.INVALID_TEST_DIFFICULTY);
        if (!ValidatorService.validateTestTime(duration)) errorForUserList.add(ErrorForUser.INVALID_TEST_DURATION);
        if (errorForUserList.size() != 0) return Path.ADMIN_CREATE_TEST;
        return "";
    }
}
