package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class DeleteAnswerCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        int id = Integer.parseInt(request.getParameter("DeleteId"));



        Question question = (Question) request.getSession().getAttribute("editedQuestion");
        question.deleteAnswerById(id);
        request.getSession().setAttribute("editedQuestion", question);
        return "redirect:/web-application/testing/admin/editQuestionInfo?id="+question.getId();
    }
}
