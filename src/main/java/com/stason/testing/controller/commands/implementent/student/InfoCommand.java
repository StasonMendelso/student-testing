package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class InfoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
       if(request.getRequestURI().contains("/student/info")){
           int userId = (int) request.getSession().getAttribute("id");
           DaoFactory factory = DaoFactory.getInstance();
           TestDao testDao = factory.createTestDao();

           List<Test> list = testDao.findPassedTests(userId);
           int paginationParameter;
            if(request.getParameter("paginationParameter")!=null){
                paginationParameter = Integer.parseInt(request.getParameter("paginationParameter"));
            }else{
                paginationParameter = 5;
            }
           int pageNumber;
           if(request.getParameter("pageNumber")!=null){
               if(Integer.parseInt(request.getParameter("pageNumber"))<=0) {
                    pageNumber=1;
               }else{
                   pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
               }
           }else{
               pageNumber = 1;
           }
            double countOfPageNumberButtons=(double)list.size()/paginationParameter;
           if(countOfPageNumberButtons<=1){
               countOfPageNumberButtons=0;
           }else {
               countOfPageNumberButtons=Math.ceil(countOfPageNumberButtons);
           }

            list = pagination(paginationParameter,pageNumber,list);
           request.setAttribute("testList", list);
           request.setAttribute("countOfPageNumberButtons",countOfPageNumberButtons);
           request.setAttribute("paginationParameter",paginationParameter);
           request.setAttribute("pageNumber",pageNumber);

            return "/WEB-INF/view/student/info.jsp";
       }

        return "redirect:/web-application/testing/student/info";
    }
    private List<Test> pagination(int paginationParameter, int pageNumber, List<Test> list){
        List<Test> paginationList = new ArrayList<>();

        for(int i=paginationParameter*(pageNumber-1);i< list.size() && i<paginationParameter*pageNumber;i++){
            paginationList.add(list.get(i));
        }
        return paginationList;
    }
}
