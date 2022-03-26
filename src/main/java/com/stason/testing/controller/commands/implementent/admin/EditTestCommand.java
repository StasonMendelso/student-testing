package com.stason.testing.controller.commands.implementent.admin;

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
import java.util.Iterator;
import java.util.List;

public class EditTestCommand implements com.stason.testing.controller.commands.Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(request.getParameter("Save")!=null){

            if(request.getParameter("secretPassword")==null){
                return Path.ADMIN_EDIT_TEST;
            }else{
                String secretPassword =request.getParameter("secretPassword");
                if(secretPassword.equals("save")){ // todo добавити константний клас з паролями
                    DaoFactory factory = DaoFactory.getInstance();
                    TestDao testDao = factory.createTestDao();
                    QuestionDao questionDao = factory.createQuestionDao();
                    AnswerDao answerDao = factory.createAnswerDao();
                    Test test = (Test) request.getSession().getAttribute("editedTest");
                    //Удаляєм тест повністю

                    deleteTest(test.getId());
                    //Добавляємо тест заново
                    testDao.create(test);
                    List<Question> questionList = test.getQuestions();
                    int testId = testDao.findIdByName(test.getName());
                    int i =1;
                    System.out.println("!!!!!!!!!!ADD TO BD QUESTION!!!!!!!!!!!!!");
                    for(Question question:questionList){
                        question.setTestId(testId);
                        question.setQuestionNumber(i++);
                        questionDao.create(question);
                        List<Answer> answerList = question.getAnswers();

                        int questionId = questionDao.findId(question);
                        for(Answer answer:answerList){
                            answer.setQuestionId(questionId);
                            answerDao.create(answer);
                        }
                    }
                    return Path.REDIRECT_ADMIN_TESTS;
                }else{
                    request.setAttribute("error","Секретний код не співпадає");
                    int currentQuestionNumber = getQuestionNumber(request);
                    if(request.getSession().getAttribute("editedTest")!=null){
                        request.setAttribute("currentQuestion", ((Test) request.getSession().getAttribute("editedTest")).getQuestion(currentQuestionNumber));
                        request.setAttribute("questionPageNumber", currentQuestionNumber);
                    }
                    return Path.ADMIN_EDIT_TEST;
                }
            }

        }
        if(request.getParameter("id")!=null && !request.getParameter("id").isEmpty()){
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println(id);
            DaoFactory factory = DaoFactory.getInstance();
            TestDao testDao = factory.createTestDao();
            QuestionDao questionDao = factory.createQuestionDao();
            AnswerDao answerDao = factory.createAnswerDao();
            Test test = testDao.findById(id);
            List<Question> questionList = questionDao.findAllByTestId(id);
            Iterator<Question> iterator = questionList.iterator();

            while (iterator.hasNext()){
                Question question = iterator.next();
                int questionId = question.getId();
                List<Answer> answerList = answerDao.findAllByQuestionId(questionId);
                question.setAnswers(answerList);
            }
            test.setQuestions(questionList);
            if(request.getSession().getAttribute("editedTest")==null) {
                request.getSession().setAttribute("editedTest", test);
            }else{
                if(((Test)request.getSession().getAttribute("editedTest")).getId()!=id){
                    request.getSession().setAttribute("editedTest", test);

                }
            }
        }
        int currentQuestionNumber = getQuestionNumber(request);
        if(request.getSession().getAttribute("editedTest")!=null){
            request.setAttribute("currentQuestion", ((Test) request.getSession().getAttribute("editedTest")).getQuestion(currentQuestionNumber));
            request.setAttribute("questionPageNumber", currentQuestionNumber);
        }

        if(request.getRequestURI().contains("admin/editTest")){
            if(request.getParameter("id")==null || request.getParameter("id").isEmpty()) return "redirect:/web-application/testing/admin/showTests";
            return Path.ADMIN_EDIT_TEST;
        }else {
            return Path.REDIRECT_ADMIN_EDIT_TEST;
        }
    }

    private int getQuestionNumber(HttpServletRequest request) {
        if(request.getSession().getAttribute("questionNumber")!=null){
            int questionNumber = (int)request.getSession().getAttribute("questionNumber");
            request.getSession().removeAttribute("questionNumber");
            return questionNumber;

        }
        if(request.getParameter("questionNumber")!=null){
            return Integer.parseInt(request.getParameter("questionNumber"));
        }else{
            return 1;
        }
    }
    private  void deleteTest(int id){
        DaoFactory factory = DaoFactory.getInstance();
        TestDao testDao = factory.createTestDao();
        QuestionDao questionDao = factory.createQuestionDao();
        AnswerDao answerDao = factory.createAnswerDao();
        List<Question> questionList = questionDao.findAllByTestId(id);
        for(Question question :questionList){
            int questionId = question.getId();
            List<Answer> answerList = answerDao.findAllByQuestionId(questionId);
            for(Answer answer:answerList){
                answerDao.delete(answer.getId());
            }
            questionDao.delete(questionId);
        }
        testDao.delete(id);
    }
}
