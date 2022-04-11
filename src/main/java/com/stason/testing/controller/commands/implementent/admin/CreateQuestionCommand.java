package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.utils.CommandsHelper;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.ErrorForUser;
import com.stason.testing.controller.utils.Path;

import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * It is a Command, which creates a new question
 * @author Stanislav Hlova
 * @version 1.0
 */
public class CreateQuestionCommand implements Command {
    private final TestService testService = new TestService();

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("SaveTest") != null) {
            String url = saveQuestion(request);
            // не пройшов валідацію
            if (!url.contains("redirect")) return url;
            if (url.contains("createQuestion")) {
                Test test = (Test) request.getSession().getAttribute("test");
                // добавляємо тест в БД
                try {
                    testService.create(test);
                } catch (RuntimeException ex) {
                    deleteLastQuestion(request);
                    throw ex;
                }
                //видаляємо з сесії
                request.getSession().removeAttribute("test");
                //переходимо на сторінку, що тест успішно зберігся
                return Path.ADMIN_SUCCESSFUL_CREATING_TEST;
            }
            return url;
        }

        if (request.getParameter("SaveQuestion") != null) {
            if (request.getSession().getAttribute("test") != null) return saveQuestion(request);
            return Path.REDIRECT_ADMIN_CREATE_TEST;
        }
        if (request.getRequestURI().contains("/createQuestion")) return Path.ADMIN_CREATE_QUESTION;
        return Path.REDIRECT_ADMIN_CREATE_QUESTION;

    }

    private void deleteLastQuestion(HttpServletRequest request) {
        Test test = (Test) request.getSession().getAttribute("test");
        test.deleteLastQuestion();
    }

    private String saveQuestion(HttpServletRequest request) {
        List<ErrorForUser> errorForUserList = new ArrayList<>();
        if (!CommandsHelper.isProperlyCheckboxChecked(request)) {
            //Вы выбрали ответ как пустой вариант ответа
            errorForUserList.add(ErrorForUser.EMPTY_ANSWER_OPTION);
            request.setAttribute("errorsList", errorForUserList);
            return Path.ADMIN_CREATE_QUESTION;
        }
        if (request.getParameter("opt") == null) {
            //Вы не выбрали правильный ответ!
            errorForUserList.add(ErrorForUser.EMPTY_RIGHT_ANSWER_OPTION);
            request.setAttribute("errorsList", errorForUserList);
            return Path.ADMIN_CREATE_QUESTION;
        }
        String rightOptions = Arrays.toString(request.getParameterValues("opt"));
        String questionName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("questionName"));
        CommandsHelper.validateQuestionParameters(request, errorForUserList);
        if (!errorForUserList.isEmpty()) {
            request.setAttribute("errorsList", errorForUserList);
            return Path.ADMIN_CREATE_QUESTION;
        }
        Question question = new Question();
        question.setTextQuestion(questionName);

        for (int i = 1; i <= 4; i++) {
            String paramName = "answer" + i;
            if (request.getParameter(paramName).isEmpty()) continue;
            String answerText = EncodingConverter.convertFromISOtoUTF8(request.getParameter(paramName));
            Answer answer = new Answer(answerText, rightOptions.contains(String.valueOf(i)), 0);
            question.addAnswer(answer);
        }
        Test test = (Test) request.getSession().getAttribute("test");
        test.addQuestion(question);
        request.getSession().setAttribute("test", test);
        return Path.REDIRECT_ADMIN_CREATE_QUESTION;
    }

}
