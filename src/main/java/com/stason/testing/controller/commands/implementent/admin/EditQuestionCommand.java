package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EditQuestionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Question questionOrigin = (Question) request.getSession().getAttribute("editedQuestion");
        //Сохранити питання
        if (request.getParameter("Save") != null) {
            //todo перевірку isProperly...
            if (!isProperlyCheckboxChecked(request)) {
                //Вы выбрали ответ как пустой вариант ответа
                return Path.REDIRECT_ADMIN_EDIT_QUESTIONS_INFO + "?id=" + questionOrigin.getId();
            }
            if (request.getParameter("opt") == null) {
                //Вы не выбрали правильный ответ!
                return Path.REDIRECT_ADMIN_EDIT_QUESTIONS_INFO + "?id=" + questionOrigin.getId();
            } else {
                String questionText = EncodingConverter.convertFromISOtoUTF8(request.getParameter("questionText"));
                String rightOptions = Arrays.toString(request.getParameterValues("opt"));

                Test test = (Test) request.getSession().getAttribute("editedTest");

                Question question = (Question) request.getSession().getAttribute("editedQuestion");
                question.setTextQuestion(questionText);
                List<Answer> answerList = question.getAnswers();
                int i = 1;
                Iterator<Answer> iterator = answerList.iterator();
                while (iterator.hasNext()) {
                    Answer answer = iterator.next();
                    String paramName = "answer" + i;

                    if (request.getParameter(paramName) == null) {
                        continue;
                    } else {
                        String answerText = EncodingConverter.convertFromISOtoUTF8(request.getParameter(paramName));
                        answer.setAnswer(answerText);
                        if (rightOptions.contains(String.valueOf(i))) {
                            answer.setRightAnswer(true);
                        } else {
                            answer.setRightAnswer(false);
                        }
                        i++;
                    }

                }

                test.setQuestionById(question, question.getId());
                request.getSession().removeAttribute("editedQuestion");
                request.getSession().setAttribute("editedTest", test);
                request.getSession().setAttribute("questionNumber", question.getQuestionNumber());
                return Path.REDIRECT_ADMIN_EDIT_TEST + "?id=" + test.getId();
            }
        }
        //Видалити відповідь
        if (request.getParameter("DeleteId") != null) {
            new DeleteAnswerCommand().execute(request);
        }
        // Додати нову відповідь
        if (request.getParameter("Add") != null && request.getParameter("id") != null) {

            Question question = null;
            try {
                question = questionOrigin.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            Answer answer = new Answer();
            answer.setAnswer(request.getParameter("answerText"));
            if (request.getParameter("opt") != null) {
                answer.setRightAnswer(true);
            } else {
                answer.setRightAnswer(false);
            }
            answer.setQuestionId(question.getId());

            answer.setId(question.getLastAnswer().getId() + 1);

            question.addAnswer(answer);

            //  test.setQuestionById(question, Integer.parseInt(request.getParameter("id")));

            request.getSession().setAttribute("editedQuestion", question);
            //return "/WEB-INF/view/admin/editQuestionInfo.jsp";
            return Path.REDIRECT_ADMIN_EDIT_QUESTIONS_INFO + "?id=" + question.getId();
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + request.getSession().getAttribute("editedQuestion"));
        if (request.getParameter("id") != null) {
            if (request.getSession().getAttribute("editedQuestion") != null) {
                if (Integer.parseInt(request.getParameter("id")) != ((Question) request.getSession().getAttribute("editedQuestion")).getId()) {
                    Test test = (Test) request.getSession().getAttribute("editedTest");
                    Question question = test.getQuestionById(Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("editedQuestion", question);
                }

            } else {
                Test test = (Test) request.getSession().getAttribute("editedTest");
                Question question = test.getQuestionById(Integer.parseInt(request.getParameter("id")));
                request.getSession().setAttribute("editedQuestion", question);
            }
        }
        if (request.getRequestURI().contains("admin/editQuestionInfo")) {
            return Path.ADMIN_EDIT_QUESTIONS_INFO;
        } else {
            return Path.REDIRECT_ADMIN_EDIT_QUESTIONS_INFO;
        }


    }

    private String saveQuestion(HttpServletRequest request) {
        if (!isProperlyCheckboxChecked(request)) {
            //Вы выбрали ответ как пустой вариант ответа
            return Path.REDIRECT_ADMIN_CREATE_QUESTION;
        }
        if (request.getParameter("opt") == null) {
            //Вы не выбрали правильный ответ!
            return Path.REDIRECT_ADMIN_CREATE_QUESTION;
        } else {
            System.out.println(Arrays.toString(request.getParameterValues("opt")));
            String rightOptions = Arrays.toString(request.getParameterValues("opt"));
            // проверка, валидация тд тп

            String questionName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("questionName"));
            Question question = new Question();
            question.setTextQuestion(questionName);

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
                    question.addAnswer(answer);
                }
            }
            System.out.println("=====================================");
            System.out.println("=Добавляєм в сесію обновльонний тест=");
            System.out.println("=====================================");
            Test test = (Test) request.getSession().getAttribute("test");
            test.addQuestion(question);
            request.getSession().setAttribute("test", test);
            return Path.REDIRECT_ADMIN_CREATE_QUESTION;

        }
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

