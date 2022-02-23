package com.stason.testing.controller.commands.implementent.admin;

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

        if(request.getParameter("id")!=null && !request.getParameter("id").isEmpty()){
            int id = Integer.parseInt(request.getParameter("id"));
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

        if(request.getRequestURI().contains("admin/editTest")){
            if(request.getParameter("id")==null || request.getParameter("id").isEmpty()) return "redirect:/web-application/testing/admin/showTests";
            return "/WEB-INF/view/admin/editTest.jsp";
        }else {
            return "redirect:/web-application/testing/admin/editTest";
        }
    }
}
