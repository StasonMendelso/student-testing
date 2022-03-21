package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.filters.EncodingFilter;
import com.stason.testing.controller.utils.EncodingConverter;
import com.stason.testing.controller.utils.comparators.test.TestCountOfQuestionsComparator;
import com.stason.testing.controller.utils.comparators.test.TestDifficultyComparator;
import com.stason.testing.controller.utils.comparators.test.TestNameComparator;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.TestDao;
import com.stason.testing.model.entity.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowTestsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String uri = request.getRequestURI();
        if(uri.contains("/student/tests")){
            int userId = (int) request.getSession().getAttribute("id");
            DaoFactory factory = DaoFactory.getInstance();
            TestDao testDao = factory.createTestDao();

            List<Test> unsurpassedTests = testDao.findUnsurpassedTests(userId);
            List<Test> testList = testDao.findAll();
            List<String> disciplinesList = testDao.findAllDisciplines();
            if(request.getParameter("orderBy")!=null && request.getParameter("order")!=null && request.getParameter("discipline")!=null){
                String orderBy = request.getParameter("orderBy");
                String order = request.getParameter("order");
                String discipline = EncodingConverter.convertFromISOtoUTF8(request.getParameter("discipline"));

                switch (orderBy){
                    case "name" :
                        testList.sort(new TestNameComparator());
                        unsurpassedTests.sort(new TestNameComparator());
                        break;
                    case "difficulty" :
                        testList.sort(new TestDifficultyComparator());
                        unsurpassedTests.sort(new TestDifficultyComparator());
                        break;
                    case "countOfQuestions" :
                        testList.sort(new TestCountOfQuestionsComparator());
                        unsurpassedTests.sort(new TestCountOfQuestionsComparator());
                        break;
                }
                if(order.equals("descending")){
                    Collections.reverse(testList);
                    Collections.reverse(unsurpassedTests);
                }
                if(!discipline.equals("all")){
                    testList = testList.stream()
                            .filter((Test test)-> test.getNameOfDiscipline().equals(discipline))
                            .collect(Collectors.toList());
                    unsurpassedTests = unsurpassedTests.stream()
                            .filter((Test test)-> test.getNameOfDiscipline().equals(discipline))
                            .collect(Collectors.toList());
                }
                Map<String,String> sortingOptions = new HashMap<>();
                sortingOptions.put("orderBy",orderBy);
                sortingOptions.put("order",order);
                sortingOptions.put("discipline",discipline);
                request.setAttribute("sortingOptions", sortingOptions);
            }
            request.setAttribute("disciplinesList", disciplinesList);
            request.setAttribute("unsurpassedTestList", unsurpassedTests);
            request.setAttribute("allTestList", testList);
            return "/WEB-INF/view/student/showTests.jsp";
        }else{
            return "redirect:/web-application/testing/student/tests";
        }

    }
}
