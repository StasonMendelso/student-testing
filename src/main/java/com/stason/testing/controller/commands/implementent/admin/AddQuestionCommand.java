package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class AddQuestionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(request.getParameter("SaveQuestion")!=null){
            Test test = (Test) request.getSession().getAttribute("editedTest");
            if(request.getSession().getAttribute("editedTest")!=null){
                return saveQuestion(request);
            }else{
                return "redirect:/web-application/testing/admin/showTests";
            }
        }
        if(request.getRequestURI().contains("/admin/addQuestion")){
            return "/WEB-INF/view/admin/addQuestion.jsp";
        }else {
            return "redirect:/web-application/testing/admin/addQuestion";
        }
    }

    private String saveQuestion(HttpServletRequest request) {
        if(!isProperlyCheckboxChecked(request)){
            //Вы выбрали ответ как пустой вариант ответа
            return "redirect:/web-application/testing/admin/addQuestion";
        }
        if(request.getParameter("opt")==null){
            //Вы не выбрали правильный ответ!
            return "redirect:/web-application/testing/admin/addQuestion";
        }else{

            System.out.println(Arrays.toString(request.getParameterValues("opt")));
            String rightOptions = Arrays.toString(request.getParameterValues("opt"));
            // проверка, валидация тд тп

            String questionName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("questionName"));
            Question question = new Question();
            question.setTextQuestion(questionName);

            Test test = (Test) request.getSession().getAttribute("editedTest");
            List<Question> questionList = test.getQuestions();
            int questionSize = questionList.size();
            Question lastQuestion = questionList.get(questionSize-1);
            List<Answer> answerList = lastQuestion.getAnswers();
            int answerSize = answerList.size();
            Answer lastAnswer = answerList.get(answerSize-1);
            int lastAnswerId = lastAnswer.getId();

            for(int i =1;i<=4;i++){
                String paramName = "answer" + i;
                if(request.getParameter(paramName).isEmpty()){
                    continue;
                }else{
                    Answer answer = new Answer();
                    String answerText = EncodingConverter.convertFromISOtoUTF8(request.getParameter(paramName));
                    answer.setAnswer(answerText);
                    if(rightOptions.contains(String.valueOf(i))){
                        answer.setRightAnswer(true);
                    }else{
                        answer.setRightAnswer(false);
                    }
                    answer.setQuestionId(lastAnswer.getQuestionId());
                    answer.setId(++lastAnswerId);
                    question.addAnswer(answer);
                }
            }
            System.out.println("=====================================");
            System.out.println("=Добавляєм в сесію обновльонний тест=");
            System.out.println("=====================================");


            question.setQuestionNumber(questionSize+1);
            question.setId(questionList.get(questionSize-1).getId()+1);
            test.addQuestion(question);
            request.getSession().setAttribute("editedTest",test);
            return "redirect:/web-application/testing/admin/editTest?id="+test.getId();
        }
    }

    private boolean isProperlyCheckboxChecked(HttpServletRequest request){
        String rightOptions = Arrays.toString(request.getParameterValues("opt"));
        // Выбран 3 вариант как правильный, но нету варианта ответа
        if(rightOptions.contains("3") && request.getParameter("answer3").isEmpty()) return false;
        // Выбран 4 вариант как правильный, но нету варианта ответа
        if(rightOptions.contains("4") && request.getParameter("answer4").isEmpty()) return false;

        return true;
    }
}
