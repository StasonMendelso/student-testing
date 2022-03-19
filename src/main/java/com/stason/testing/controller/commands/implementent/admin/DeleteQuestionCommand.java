package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.AnswerDao;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.QuestionDao;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DeleteQuestionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        Test test = (Test)request.getSession().getAttribute("editedTest");
        int countOfQuestions = test.getCountOfQuestions();
        if(countOfQuestions==1){
            //Спочатку добавте питання, а потім видаліть зайве!
            // set Error

        }else{
            int id = Integer.parseInt(request.getParameter("id"));
            test.deleteQuestion(id);
            System.out.println(test);
            request.getSession().setAttribute("editedTest",test);
            
        }
        return "redirect:/web-application/testing/admin/editTest?id="+test.getId();
    }
    private void deleteQuestion(HttpServletRequest request,int id){

    }
}
