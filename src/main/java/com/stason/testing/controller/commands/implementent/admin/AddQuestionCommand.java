package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.ValidatorService;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.ErrorForUser;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddQuestionCommand implements Command {
    private final static Logger logger = Logger.getLogger(AddQuestionCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("SaveQuestion") != null) {
            if (request.getSession().getAttribute("editedTest") != null) {
                return saveQuestion(request);
            } else {
                return Path.REDIRECT_ADMIN_TESTS;
            }
        }
        if (request.getRequestURI().contains("/admin/addQuestion")) {
            return Path.ADMIN_ADD_QUESTIONS;
        } else {

            return Path.REDIRECT_ADMIN_ADD_QUESTIONS;
        }
    }

    private String saveQuestion(HttpServletRequest request) {
        List<ErrorForUser> errorForUserList = new ArrayList();
        if (!isProperlyCheckboxChecked(request)) {
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
        if(!ValidatorService.validateQuestionText(questionName)){
            errorForUserList.add(ErrorForUser.INVALID_QUESTION_NAME);
        }
        for (int i = 1; i <= 4; i++) {
            String paramName = "answer" + i;
            if (request.getParameter(paramName).isEmpty()) {
                continue;
            } else {
                String answerText = EncodingConverter.convertFromISOtoUTF8(request.getParameter(paramName));
                if(!ValidatorService.validateAnswerText(answerText) && !errorForUserList.contains(ErrorForUser.INVALID_ANSWER_NAME)){
                    errorForUserList.add(ErrorForUser.INVALID_ANSWER_NAME);
                }
            }
        }
        if(errorForUserList.size()!=0){
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
        int answerSize = answerList.size();
        Answer lastAnswer = answerList.get(answerSize - 1);
        int lastAnswerId = lastAnswer.getId();

        for (int i = 1; i <= 4; i++) {
            String paramName = "answer" + i;
            if (request.getParameter(paramName).isEmpty()) {
                continue;
            } else {
                Answer answer = new Answer();
                String answerText = EncodingConverter.convertFromISOtoUTF8(request.getParameter(paramName));
                answer.setAnswer(answerText);
                if (rightOptions.contains(String.valueOf(i))) {
                    answer.setRightAnswer(true);
                } else {
                    answer.setRightAnswer(false);
                }
                answer.setQuestionId(lastAnswer.getQuestionId());
                answer.setId(++lastAnswerId);
                question.addAnswer(answer);
            }
        }
        question.setQuestionNumber(questionSize + 1);
        question.setId(questionList.get(questionSize - 1).getId() + 1);
        test.addQuestion(question);
        request.getSession().setAttribute("editedTest", test);
        return Path.REDIRECT_ADMIN_EDIT_TEST + "?id=" + test.getId();

    }

    private boolean isProperlyCheckboxChecked(HttpServletRequest request) {
        String rightOptions = Arrays.toString(request.getParameterValues("opt"));
        // Выбран 1 вариант как правильный, но нету варианта ответа
        if (rightOptions.contains("1") && request.getParameter("answer1").isEmpty()) return false;
        // Выбран 2 вариант как правильный, но нету варианта ответа
        if (rightOptions.contains("2") && request.getParameter("answer2").isEmpty()) return false;
        // Выбран 3 вариант как правильный, но нету варианта ответа
        if (rightOptions.contains("3") && request.getParameter("answer3").isEmpty()) return false;
        // Выбран 4 вариант как правильный, но нету варианта ответа
        if (rightOptions.contains("4") && request.getParameter("answer4").isEmpty()) return false;

        return true;
    }
}
