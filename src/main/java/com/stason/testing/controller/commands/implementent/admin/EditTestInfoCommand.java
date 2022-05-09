package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.services.ValidatorService;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.ErrorForUser;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * It is a Command, which edits test's information
 * @author Stanislav Hlova
 * @version 1.0
 */
public class EditTestInfoCommand implements com.stason.testing.controller.commands.Command {
    private List<ErrorForUser> errorForUserList = new ArrayList<>();

    @Override
    public String execute(HttpServletRequest request) {
        if (checkParameters(request)) {
            errorForUserList.clear();
            validate(request);
            if (!errorForUserList.isEmpty()){
                request.setAttribute("errorsList", errorForUserList);
                return Path.ADMIN_EDIT_TEST_INFO;
            }
            String testName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("testName"));
            TestService testService = new TestService();
            Test test = (Test) request.getSession().getAttribute("editedTest");
            if (testService.checkTestName(testName)) {
                errorForUserList.add(ErrorForUser.SUCH_TEST_NAME_ALREADY_EXISTS);
                request.setAttribute("errorsList", errorForUserList);
                return Path.ADMIN_EDIT_TEST_INFO;
            }
            saveEditedTest(request);
            return Path.REDIRECT_ADMIN_EDIT_TEST + "?id=" + test.getId();
        }

        if (request.getRequestURI().contains("/admin/editTestInfo")) {
            return Path.ADMIN_EDIT_TEST_INFO;
        } else {
            return Path.REDIRECT_ADMIN_EDIT_TEST_INFO;
        }
    }

    private boolean checkParameters(HttpServletRequest request) {
        if (request.getParameter("testName") != null && request.getParameter("disciplineName") != null && request.getParameter("difficulty") != null && request.getParameter("duration") != null)
            return true;
        return false;
    }

    private void saveEditedTest(HttpServletRequest request) {
        Test test = (Test) request.getSession().getAttribute("editedTest");
        if (!request.getParameter("testName").isEmpty()) {
            String testName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("testName"));
            test.setName(testName);
        }
        if (!request.getParameter("disciplineName").isEmpty()) {
            String disciplineName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("disciplineName"));
            test.setNameOfDiscipline(disciplineName);
        }
        if (!request.getParameter("duration").isEmpty()) {
            test.setTimeMinutes(Integer.parseInt(request.getParameter("duration")));
        }
        test.setDifficulty(Integer.parseInt(request.getParameter("difficulty")));
        request.getSession().setAttribute("editedTest", test);
    }

    private void validate(HttpServletRequest request) {
        String testName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("testName"));
        String disciplineName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("disciplineName"));
        if (!request.getParameter("duration").isEmpty()) {
            int duration = Integer.parseInt(request.getParameter("duration"));
            if (!ValidatorService.validateTestTime(duration)) errorForUserList.add(ErrorForUser.INVALID_TEST_DURATION);
        }
        int difficulty = Integer.parseInt(request.getParameter("difficulty"));
        if (!testName.isEmpty() && !ValidatorService.validateTestName(testName))
            errorForUserList.add(ErrorForUser.INVALID_TEST_NAME);
        if (!disciplineName.isEmpty() && !ValidatorService.validateTestDisciplineName(disciplineName))
            errorForUserList.add(ErrorForUser.INVALID_DISCIPLINE_NAME);
        if (!ValidatorService.validateTestDifficulty(difficulty))
            errorForUserList.add(ErrorForUser.INVALID_TEST_DIFFICULTY);

    }
}
