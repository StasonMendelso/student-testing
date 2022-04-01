package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;

public class EditTestInfoCommand implements com.stason.testing.controller.commands.Command {
    @Override
    public String execute(HttpServletRequest request){
        if(checkParameters(request)){
            saveEditedTest(request);
            Test test = (Test)request.getSession().getAttribute("editedTest");
            return Path.REDIRECT_ADMIN_EDIT_TEST + "?id="+test.getId();
        }

        if(request.getRequestURI().contains("/admin/editTestInfo")){
            return Path.ADMIN_EDIT_TEST_INFO;
        }else {
            return Path.REDIRECT_ADMIN_EDIT_TEST_INFO;
        }
    }
    private boolean checkParameters(HttpServletRequest request){
        if(request.getParameter("testName")!=null && request.getParameter("disciplineName")!=null && request.getParameter("difficulty")!=null && request.getParameter("duration")!=null)return true;
        return false;
    }
    private void saveEditedTest(HttpServletRequest request){
        Test test = (Test) request.getSession().getAttribute("editedTest");
        if(!request.getParameter("testName").isEmpty()){
            String testName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("testName"));
            test.setName(testName);
        }
        if(!request.getParameter("disciplineName").isEmpty()){
            String disciplineName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("disciplineName"));
            test.setNameOfDiscipline(disciplineName);
        }
        if(!request.getParameter("duration").isEmpty()){
            test.setTimeMinutes(Integer.parseInt(request.getParameter("duration")));
        }
        test.setDifficulty(Integer.parseInt(request.getParameter("difficulty")));
        request.getSession().setAttribute("editedTest",test);
    }
}
