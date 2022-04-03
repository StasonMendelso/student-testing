package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;

import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;


public class DeleteQuestionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request){
        Test test = (Test)request.getSession().getAttribute("editedTest");
        int countOfQuestions = test.getCountOfQuestions();
        if (countOfQuestions != 1) {
            int id = Integer.parseInt(request.getParameter("id"));
            test.deleteQuestion(id);
            request.getSession().setAttribute("editedTest",test);

        }
        return Path.REDIRECT_ADMIN_EDIT_TEST+"?id="+test.getId();
    }

}
