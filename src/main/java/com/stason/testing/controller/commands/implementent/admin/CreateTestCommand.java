package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Test;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;



public class CreateTestCommand implements Command{
    private final  static Logger logger = Logger.getLogger(CreateTestCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request){
        //1.Валидация
        //2.Проверка нету с таким именем теста
        //3.Проверка всех параметров формы и сохранение в сессию объекта Тест


        if(request.getParameter("testName")!=null){
            String name = EncodingConverter.convertFromISOtoUTF8(request.getParameter("testName"));
            String disciplineName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("disciplineName"));
            int difficulty = Integer.parseInt(request.getParameter("difficulty"));
            //todo constructors?
            Test test = new Test();
            test.setName(name);
            test.setNameOfDiscipline(disciplineName);
            test.setDifficulty(difficulty);
            test.setTimeMinutes(Integer.parseInt(request.getParameter("duration")));
            request.getSession().setAttribute("test",test);
            logger.info("Admin creates new test");
            return Path.REDIRECT_ADMIN_CREATE_QUESTION;
        }

        if(request.getRequestURI().contains("/createTest")){
            return Path.ADMIN_CREATE_TEST;
        }else {
            return  Path.REDIRECT_ADMIN_CREATE_TEST;
        }
    }
}
