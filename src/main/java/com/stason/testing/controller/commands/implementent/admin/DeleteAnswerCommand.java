package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Question;

import javax.servlet.http.HttpServletRequest;

/**
 * It is a Command, which deletes an answer
 * @author Stanislav Hlova
 * @version 1.0
 */
public class DeleteAnswerCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("DeleteId"));
        Question question = (Question) request.getSession().getAttribute("editedQuestion");
        question.deleteAnswerById(id);
        request.getSession().setAttribute("editedQuestion", question);
        return Path.REDIRECT_ADMIN_EDIT_QUESTIONS_INFO + "?id=" + question.getId();

    }
}
