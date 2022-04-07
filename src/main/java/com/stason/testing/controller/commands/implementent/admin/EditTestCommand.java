package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.utils.Constants;
import com.stason.testing.controller.utils.ErrorForUser;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;


public class EditTestCommand implements com.stason.testing.controller.commands.Command {
    private final TestService testService = new TestService();

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("Save") != null) {
            if (request.getParameter("secretPassword") == null) {
                return Path.ADMIN_EDIT_TEST;
            } else {
                String secretPassword = request.getParameter("secretPassword");
                if (secretPassword.equals(Constants.PASSWORD_SAVE_EDITED_TEST)) {
                    Test test = (Test) request.getSession().getAttribute("editedTest");
                    //Удаляєм тест повністю
                    testService.update(test);
                    return Path.REDIRECT_ADMIN_TESTS;
                } else {
                    request.setAttribute("error", ErrorForUser.SECRET_CODE_NOT_MATCH);
                    int currentQuestionNumber = getQuestionNumber(request);
                    if (request.getSession().getAttribute("editedTest") != null) {
                        request.setAttribute("currentQuestion", ((Test) request.getSession().getAttribute("editedTest")).getQuestion(currentQuestionNumber));
                        request.setAttribute("questionPageNumber", currentQuestionNumber);
                    }
                    return Path.ADMIN_EDIT_TEST;
                }
            }
        }
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            int testId = Integer.parseInt(request.getParameter("id"));
            Test test = testService.findTestWithQuestionsAndAnswers(testId);
            if (request.getSession().getAttribute("editedTest") == null) {
                request.getSession().setAttribute("editedTest", test);
            } else {
                if (((Test) request.getSession().getAttribute("editedTest")).getId() != testId) {
                    request.getSession().setAttribute("editedTest", test);
                }
            }
        }
        int currentQuestionNumber = getQuestionNumber(request);
        if (request.getSession().getAttribute("editedTest") != null) {
            request.setAttribute("currentQuestion", ((Test) request.getSession().getAttribute("editedTest")).getQuestion(currentQuestionNumber));
            request.setAttribute("questionPageNumber", currentQuestionNumber);
        }

        if (request.getRequestURI().contains("admin/editTest")) {
            if (request.getParameter("id") == null || request.getParameter("id").isEmpty())
                return Path.REDIRECT_ADMIN_TESTS;
            return Path.ADMIN_EDIT_TEST;
        }
        return Path.REDIRECT_ADMIN_EDIT_TEST;

    }

    private int getQuestionNumber(HttpServletRequest request) {
        if (request.getSession().getAttribute("questionNumber") != null) {
            int questionNumber = (int) request.getSession().getAttribute("questionNumber");
            request.getSession().removeAttribute("questionNumber");
            return questionNumber;
        }
        if (request.getParameter("questionNumber") != null) {
            return Integer.parseInt(request.getParameter("questionNumber"));
        } else {
            return 1;
        }
    }

}
