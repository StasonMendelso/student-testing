package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.dao.TestDao;
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
            if(url.contains("createQuestion")){
                // беремо з сесії тест і зберігаємо в БД
                DaoFactory factory = DaoFactory.getInstance();
                TestDao testDao = factory.createTestDao();
                QuestionDao questionDao = factory.createQuestionDao();
                AnswerDao answerDao = factory.createAnswerDao();

                Test test = (Test) request.getSession().getAttribute("test");

                // добавляємо тест в БД
                testDao.create (test);
                // добавляємо вопросы в БД
                int testId = testDao.findIdByName(test.getName());
                int i =1;
                for(Question question : test.getQuestions()){
                    question.setTestId(testId);
                    question.setQuestionNumber(i++);
                    questionDao.create(question);
                    //добавляємо до кожного вопроса відповіді в БД
                    int questionId = questionDao.findId(question);

                    for(Answer answer: question.getAnswers()){
                        answer.setQuestionId(questionId);
                        answerDao.create(answer);
                    }
                }
                //видаляємо з сесії
                request.getSession().removeAttribute("test");
                //переходимо на сторінку, що тест успішно зберігся
                return Path.ADMIN_SUCCESSFUL_CREATING_TEST;
            }else{
                return  url;
            }




        }

        if(request.getParameter("SaveQuestion")!=null){
            if(request.getSession().getAttribute("test")!=null){
                return saveQuestion(request);
            }else{
                return Path.REDIRECT_ADMIN_CREATE_TEST;
            }
        }
        if(request.getRequestURI().contains("/createQuestion")){
            return Path.ADMIN_CREATE_QUESTION;
        }else {
            return Path.REDIRECT_ADMIN_CREATE_QUESTION;
        }
    }

    private String saveQuestion(HttpServletRequest request) {
        if(!isProperlyCheckboxChecked(request)){
            //Вы выбрали ответ как пустой вариант ответа
            return Path.REDIRECT_ADMIN_CREATE_QUESTION;
        }
        if(request.getParameter("opt")==null){
            //Вы не выбрали правильный ответ!
            return Path.REDIRECT_ADMIN_CREATE_QUESTION;
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
            return Path.REDIRECT_ADMIN_CREATE_QUESTION;

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
