package com.stason.testing.controller.utils;

public class Path {
    //jsp (forward)
    public static final String INDEX = "/index.jsp";
    //student
    public static final String STUDENT_INFO = "/WEB-INF/view/student/info.jsp";
    public static final String STUDENT_TESTS = "/WEB-INF/view/student/showTests.jsp";
    public static final String STUDENT_TEST= "/WEB-INF/view/student/doTest.jsp";
    public static final String STUDENT_RESULT= "/WEB-INF/view/student/showTestResult.jsp";
    //redirect
    //student
    public static final String REDIRECT_STUDENT_INFO = "redirect:/web-application/testing/student/info";
    public static final String REDIRECT_STUDENT_TESTS = "redirect:/web-application/testing/student/tests";
    public static final String REDIRECT_STUDENT_RESULT = "redirect:/web-application/testing/student/result";
    public static final String REDIRECT_STUDENT_TEST = "redirect:/web-application/testing/student/test";

}
