package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ShowTestResultCommand implements com.stason.testing.controller.commands.Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        //Обраховуємо оцінку
        if(request.getRequestURI().contains("/student/result")) {
            if(request.getSession().getAttribute("test")!=null){
                Test test = (Test) request.getSession().getAttribute("test");
                System.out.println(test);
                int countOfQuestions = test.getCountOfQuestions();
                int countOfRightAnswers = 0;

                List<Question> questionList = test.getQuestions();
                for(Question question:questionList){
                    List<Answer> answerList = question.getAnswers();
                    List<Boolean> userOptions = question.getUserOptions();
                    boolean flag=true;
                    for(int i=0;i<answerList.size();i++){
                       if( !answerList.get(i).isRightAnswer()==(userOptions.get(i)) ){
                           flag = false;
                       }
                    }
                    if(flag) countOfRightAnswers++;
                }

                double mark = ((double)countOfRightAnswers/countOfQuestions) * 100;
                mark = (double)((int) (mark * 100)) / 100;
                int userId = (int) request.getSession().getAttribute("id");
                DaoFactory factory = DaoFactory.getInstance();
                TestDao testDao = factory.createTestDao();
//                testDao.addPassedTest(userId, test.getId(), mark);
                testDao.updatePassedTest(userId,test.getId(),mark);
                request.setAttribute("countOfRightAnswers",countOfRightAnswers);
                request.setAttribute("mark",mark);
                request.setAttribute("test",test);
                request.getSession().removeAttribute("test");
            }else{
                return Path.REDIRECT_STUDENT_TESTS;
            }
            return Path.STUDENT_RESULT;
        }else{
            return Path.REDIRECT_STUDENT_RESULT;
        }
    }
}
