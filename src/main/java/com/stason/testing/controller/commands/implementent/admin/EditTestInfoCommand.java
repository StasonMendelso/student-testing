package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.services.EncodingConverter;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class EditTestInfoCommand implements com.stason.testing.controller.commands.Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(checkParameters(request)){
            saveEditedTest(request);
            Test test = (Test)request.getSession().getAttribute("editedTest");
            return "redirect:/web-application/testing/admin/editTest?id="+test.getId();
        }

        if(request.getRequestURI().contains("/admin/editTestInfo")){
            return "/WEB-INF/view/admin/editTestInfo.jsp";
        }else {
            return "redirect:/web-application/testing/admin/editTestInfo";
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
        test.setDifficulty(request.getParameter("difficulty"));
        request.getSession().setAttribute("editedTest",test);
    }
}
