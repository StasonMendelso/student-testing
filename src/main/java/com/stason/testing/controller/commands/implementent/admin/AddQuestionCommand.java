package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
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

public class AddQuestionCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("SaveQuestion") != null) {
            if (request.getSession().getAttribute("editedTest") != null) return saveQuestion(request);
            return Path.REDIRECT_ADMIN_TESTS;
        }
        if (request.getRequestURI().contains("/admin/addQuestion")) return Path.ADMIN_ADD_QUESTIONS;
        return Path.REDIRECT_ADMIN_ADD_QUESTIONS;
    }

    private String saveQuestion(HttpServletRequest request) {
        List<ErrorForUser> errorForUserList = new ArrayList<>();
        if (!CommandsHelper.isProperlyCheckboxChecked(request)) {
            //Вы выбрали ответ как пустой вариант ответа
            errorForUserList.add(ErrorForUser.EMPTY_ANSWER_OPTION);
            request.setAttribute("errorsList", errorForUserList);
            return Path.ADMIN_ADD_QUESTIONS;
        }
        if (request.getParameter("opt") == null) {
            //Вы не выбрали правильный ответ!
            errorForUserList.add(ErrorForUser.EMPTY_RIGHT_ANSWER_OPTION);
            request.setAttribute("errorsList", errorForUserList);
            return Path.ADMIN_ADD_QUESTIONS;
        }

        String rightOptions = Arrays.toString(request.getParameterValues("opt"));
        String questionName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("questionName"));
        CommandsHelper.validateQuestionParameters(request, errorForUserList);

        if (!errorForUserList.isEmpty()) {
            request.setAttribute("errorsList", errorForUserList);
            return Path.ADMIN_ADD_QUESTIONS;
        }
        Question question = new Question();
        question.setTextQuestion(questionName);

        Test test = (Test) request.getSession().getAttribute("editedTest");
        List<Question> questionList = test.getQuestions();
        int questionSize = questionList.size();
        Question lastQuestion = questionList.get(questionSize - 1);
        List<Answer> answerList = lastQuestion.getAnswers();
        Answer lastAnswer = answerList.get(answerList.size() - 1);
        int lastAnswerId = lastAnswer.getId();

        for (int i = 1; i <= 4; i++) {
            String paramName = "answer" + i;
            if (request.getParameter(paramName).isEmpty()) continue;
            String answerText = EncodingConverter.convertFromISOtoUTF8(request.getParameter(paramName));
            Answer answer = new Answer(++lastAnswerId, answerText, rightOptions.contains(String.valueOf(i)), lastAnswer.getQuestionId());
            question.addAnswer(answer);

        }
        question.setQuestionNumber(questionSize + 1);
        question.setId(questionList.get(questionSize - 1).getId() + 1);
        test.addQuestion(question);
        request.getSession().setAttribute("editedTest", test);
        return Path.REDIRECT_ADMIN_EDIT_TEST + "?id=" + test.getId();

    }

}
