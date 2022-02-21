package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.EncodingConverter;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class CreateQuestionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println(request.getSession().getAttribute("test"));
        if (request.getParameter("SaveTest")!=null){

            String url = saveQuestion(request);
            if(url.contains("createQuestion.jsp")){
                // беремо з сесії тест і зберігаємо в БД

                //переходимо на сторінку, що тест успішно зберігся
                return "/WEB-INF/view/admin/successful_creatingTest.jsp";
            }else{
                return  url;
            }




        }

        if(request.getParameter("SaveQuestion")!=null){
            if(request.getSession().getAttribute("test")!=null){
                return saveQuestion(request);



            }else{
                return "redirect:/web-application/testing/admin/createTest";
            }


        }

        if(request.getRequestURI().contains("/createQuestion")){
            return "/WEB-INF/view/admin/createQuestion.jsp";
        }else {
            return "redirect:/web-application/testing/admin/createQuestion";
        }
    }

    private String saveQuestion(HttpServletRequest request) {
        if(!isProperlyCheckboxChecked(request)){
            //Вы выбрали ответ как пустой вариант ответа
            return "redirect:/web-application/testing/admin/createQuestion";
        }
        if(request.getParameter("opt")==null){
            //Вы не выбрали правильный ответ!
            return "redirect:/web-application/testing/admin/createQuestion";
        }else{
            System.out.println(Arrays.toString(request.getParameterValues("opt")));
            String rightOptions = Arrays.toString(request.getParameterValues("opt"));
            // проверка, валидация тд тп

            String questionName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("questionName"));
            Question question = new Question();
            question.setTextQuestion(questionName);

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
                    question.addAnswer(answer);
                }
            }
            System.out.println("=====================================");
            System.out.println("=Добавляєм в сесію обновльонний тест=");
            System.out.println("=====================================");
            Test test = (Test) request.getSession().getAttribute("test");
            test.addQuestion(question);
            request.getSession().setAttribute("test",test);
            return "/WEB-INF/view/admin/createQuestion.jsp";
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
