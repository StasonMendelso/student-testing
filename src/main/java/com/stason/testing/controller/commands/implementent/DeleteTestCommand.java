package com.stason.testing.controller.commands.implementent;

import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DeleteTestCommand implements com.stason.testing.controller.commands.Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        int id = Integer.parseInt(request.getParameter("id"));
        deleteTest(id);

        return "redirect:/web-application/testing/admin/showTests";
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
