package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DoTestCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("-------------------");
        System.out.println(request.getParameter("currentClientTime"));
        System.out.println("-------------------");

        String uri = request.getRequestURI();
        int testId = 0;

        //Для инициализации
        if(request.getParameter("id")!=null){

            testId = Integer.parseInt(request.getParameter("id"));
        }
        //якщо тест не налл і айді зійшлось, то продовжуємо проходження теста
        if(request.getSession().getAttribute("test")!=null && request.getParameter("id")!=null){
            Test currentTest = (Test) request.getSession().getAttribute("test");
            if(testId==currentTest.getId()){
                return "redirect:/web-application/testing/student/test?question=1";
            }else {
                return "redirect:/web-application/testing/student/tests";
            }
        }
        //Для инициализации
        if(request.getSession().getAttribute("test")==null && request.getParameter("id")!=null  && request.getParameter("currentClientTime")!=null){
            //start-> занести в бд що тест пройдено на 0%, занести в сесію, що тест вже пройден.
            List<Integer> idPassedTestsList = (List<Integer>) request.getSession().getAttribute("idOfPassedTests");
            for(Integer id : idPassedTestsList){
                if(id == testId){
                    return "redirect:/web-application/testing/student/tests";
                }
            }
            int userId = (int) request.getSession().getAttribute("id");
            DaoFactory factory = DaoFactory.getInstance();
            TestDao testDao = factory.createTestDao();
            testDao.addPassedTest(userId,testId,0);
            idPassedTestsList.add(testId);
            request.getSession().setAttribute("idOfPassedTests",idPassedTestsList);
            QuestionDao questionDao = factory.createQuestionDao();
            AnswerDao answerDao = factory.createAnswerDao();
            Test test = testDao.findById(testId);
            List<Question> questionList = questionDao.findAllByTestId(testId);
            Iterator<Question> iterator = questionList.iterator();

            while (iterator.hasNext()){
                Question question = iterator.next();
                int questionId = question.getId();
                List<Answer> answerList = answerDao.findAllByQuestionId(questionId);
                List<Boolean> userOptions = new LinkedList<>();
                for(int i =1; i <= answerList.size();i++){
                    userOptions.add(new Boolean(false));
                }
                question.setUserOptions(userOptions);
                question.setAnswers(answerList);
            }
            test.setQuestions(questionList);
            Date date = new Date(Long.parseLong(request.getParameter("currentClientTime")));
            long outDateMilliseconds = Long.parseLong(request.getParameter("currentClientTime"))+test.getTimeSeconds()*1000L;
            Date outDate = new Date(outDateMilliseconds);
            System.out.println("Дата клієнта: "+date);
            System.out.println("Дата здачі тесту для цього клієнта: "+outDate);
            request.getSession().setAttribute("test",test);
            request.getSession().setAttribute("outDateMilliseconds",outDateMilliseconds);
            return "redirect:/web-application/testing/student/test?question=1";
        }
        if(request.getSession().getAttribute("test")==null ){
            return "redirect:/web-application/testing/student/tests";
        }
        //Для сохранения ответов пользователя

        if(request.getParameter("save")!=null && request.getParameter("questionNumber")!=null && request.getParameter("finish")!=null){
            saveAnswers(request);
            return new ShowTestResultCommand().execute(request);
        }
        //Для сохранения ответов пользователя
        if(request.getParameter("nextQuestion")!=null && request.getParameter("save")!=null && request.getParameter("questionNumber")!=null){
            return saveAnswers(request);
        }


        //Для инициализации
        if(request.getParameter("question")!=null){
            int questionNumber = Integer.parseInt(request.getParameter("question"));
            Test test = (Test) request.getSession().getAttribute("test");
            Question question = test.getQuestion(questionNumber);
            System.out.println(question);
            request.setAttribute("question", question);
            return "/WEB-INF/view/student/doTest.jsp";
        }


        if(uri.contains("/student/test")){
            return "/WEB-INF/view/student/doTest.jsp";
        }else{
            return "redirect:/web-application/testing/student/test";
        }

    }

    private String saveAnswers(HttpServletRequest request) {
        int questionNumber = Integer.parseInt(request.getParameter("questionNumber"));
        String[] opt = request.getParameterValues("opt");
        if(opt!=null){
            Test test = (Test) request.getSession().getAttribute("test");
            Question question = test.getQuestion(questionNumber);
            List<Boolean> userOptions =  question.getUserOptions();
            for(int i=0; i< userOptions.size();i++){
                userOptions.set(i,false);
            }
            for(int i = 0; i< opt.length;i++){
                userOptions.set(Integer.parseInt(opt[i])-1, true);
            }
            question.setUserOptions(userOptions);
            test.setQuestion(question,questionNumber);

            request.getSession().setAttribute("test",test);

            return "redirect:/web-application/testing/student/test?question=" + request.getParameter("nextQuestion");
        }else{
            Test test = (Test) request.getSession().getAttribute("test");
            Question question = test.getQuestion(questionNumber);
            List<Boolean> userOptions =  question.getUserOptions();

            for(int i=0; i< userOptions.size();i++){
                userOptions.set(i,false);
            }

            question.setUserOptions(userOptions);
            test.setQuestion(question,questionNumber);
            request.getSession().setAttribute("test",test);
            request.getSession().setAttribute("timeLeft",request.getParameter("leftTime"));
            return "redirect:/web-application/testing/student/test?question=" + request.getParameter("nextQuestion");
        }
    }
}
