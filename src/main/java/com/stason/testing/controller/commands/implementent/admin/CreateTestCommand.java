package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.EncodingConverter;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class CreateTestCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        //1.Валидация
        //2.Проверка нету с таким именем теста
        //3.Проверка всех параметров формы и сохранение в сессию объекта Тест


        if(request.getParameter("testName")!=null){
            String name = EncodingConverter.convertFromISOtoUTF8(request.getParameter("testName"));
            String disciplineName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("disciplineName"));
            String difficulty = EncodingConverter.convertFromISOtoUTF8(request.getParameter("difficulty"));

            Test test = new Test();
            test.setName(name);
            test.setNameOfDiscipline(disciplineName);
            test.setDifficulty(difficulty);
            test.setTimeMinutes(Integer.parseInt(request.getParameter("duration")));
            request.getSession().setAttribute("test",test);

            return "redirect:/web-application/testing/admin/createQuestion";
        }

        if(request.getRequestURI().contains("/createTest")){
            return "/WEB-INF/view/admin/createTest.jsp";
        }else {
            return "redirect:/web-application/testing/admin/createTest";
        }
    }
}
