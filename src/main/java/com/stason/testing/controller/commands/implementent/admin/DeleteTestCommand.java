package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.services.AnswerService;
import com.stason.testing.controller.services.QuestionService;
import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

public class DeleteTestCommand implements com.stason.testing.controller.commands.Command {
    private final  static Logger logger = Logger.getLogger(DeleteTestCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request){
        if(request.getParameter("secretPassword")==null){
            return Path.REDIRECT_ADMIN_USERS;
        }else {
            String secretPassword = request.getParameter("secretPassword");
            if (secretPassword.equals("delete")) { // todo добавити константний клас з паролями{
                int id = -1;
                if (request.getParameter("id") != null) {
                    id = Integer.parseInt(request.getParameter("id"));
                }
                deleteTest(id);
                logger.info("Admin deleted test["+id+"]");
                request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
                request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
                request.getSession().setAttribute("orderBy",request.getParameter("orderBy"));
                request.getSession().setAttribute("order",request.getParameter("order"));
                request.getSession().setAttribute("discipline",request.getParameter("discipline"));
            }else{
                request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
                request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
                request.getSession().setAttribute("orderBy",request.getParameter("orderBy"));
                request.getSession().setAttribute("order",request.getParameter("order"));
                request.getSession().setAttribute("discipline",request.getParameter("discipline"));
                request.getSession().setAttribute("error", "Секретний код не співпадає");
                return Path.REDIRECT_ADMIN_TESTS;
            }
        }


        return Path.REDIRECT_ADMIN_TESTS;
    }
    private  void deleteTest(int id){

        TestService testService = new TestService();
        QuestionService questionService = new QuestionService();
        AnswerService answerService = new AnswerService();
        List<Question> questionList = questionService.findAllByTestId(id);
        for(Question question :questionList){
            int questionId = question.getId();
            List<Answer> answerList = answerService.findAllByQuestionId(questionId);
            for(Answer answer:answerList){
                answerService.delete(answer.getId());
            }
            questionService.delete(questionId);
        }
        testService.delete(id);
    }
}
