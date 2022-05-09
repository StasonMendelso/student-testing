package com.stason.testing.controller.utils;

import javax.servlet.jsp.PageContext;

/**It is class with URL-paths
 * @author Stanislav Hlova
 * @version 1.0
 */
public abstract class Path {
    //jsp (forward)
    public static final String INDEX = "/index.jsp";
    public static final String CHANGE_PASSWORD = "/WEB-INF/view/changePasswordSendEmail.jsp";
    public static final String CHANGE_PASSWORD_ERROR_IDENTIFICATION = "/WEB-INF/view/errorIdentificationLink.jsp";
    public static final String CHANGE_PASSWORD_SUCCESS ="/WEB-INF/view/successfulOfChangingPassword.jsp" ;

    //guest
    public static final String GUEST_LOGIN = "/WEB-INF/view/guest/login.jsp";
    public static final String GUEST_REGISTER = "/WEB-INF/view/guest/register.jsp";
    public static final String GUEST_SUCCESSFUL_REGISTER = "/WEB-INF/view/guest/successful_registration.jsp";
    public static final String GUEST_UNSUCCESSFUL_REGISTER = "/WEB-INF/view/guest/unsuccessful_registration.jsp";
    public static final String RECOVERY_EMAIL = "/WEB-INF/view/guest/recoveryPassword/email.jsp";
    public static final String RECOVERY_ACTIVATION_CODE = "/WEB-INF/view/guest/recoveryPassword/activationCode.jsp";
    public static final String RECOVERY_CREATE_NEW_PASSWORD = "/WEB-INF/view/guest/recoveryPassword/createNewPassword.jsp";
    public static final String RECOVERY_SUCCESSFUL = "/WEB-INF/view/guest/recoveryPassword/successfulOfRecoveringPassword.jsp";

    //student
    public static final String STUDENT_INFO = "/WEB-INF/view/student/info.jsp";
    public static final String STUDENT_TESTS = "/WEB-INF/view/student/showTests.jsp";
    public static final String STUDENT_TEST= "/WEB-INF/view/student/doTest.jsp";
    public static final String STUDENT_RESULT= "/WEB-INF/view/student/showTestResult.jsp";
    //admin
    public static final String ADMIN_INFO =  "/WEB-INF/view/admin/info.jsp";
    public static final String ADMIN_TESTS = "/WEB-INF/view/admin/showTests.jsp";
    public static final String ADMIN_USERS = "/WEB-INF/view/admin/showUsers.jsp";
    public static final String ADMIN_USER_TESTS = "/WEB-INF/view/admin/showUsersTests.jsp";
    public static final String ADMIN_EDIT_USER = "redirect:/web-application/testing/admin/editUser";
    public static final String ADMIN_EDIT_USER_INFO = "/WEB-INF/view/admin/editUserInfo.jsp";
    public static final String ADMIN_ADD_QUESTIONS = "/WEB-INF/view/admin/addQuestion.jsp";
    public static final String ADMIN_CREATE_QUESTION = "/WEB-INF/view/admin/createQuestion.jsp";
    public static final String ADMIN_CREATE_TEST = "/WEB-INF/view/admin/createTest.jsp";
    public static final String ADMIN_EDIT_TEST = "/WEB-INF/view/admin/editTest.jsp";
    public static final String ADMIN_EDIT_TEST_INFO = "/WEB-INF/view/admin/editTestInfo.jsp";
    public static final String ADMIN_SUCCESSFUL_CREATING_TEST = "/WEB-INF/view/admin/successful_creatingTest.jsp";
    public static final String ADMIN_EDIT_QUESTIONS_INFO = "/WEB-INF/view/admin/editQuestionInfo.jsp";
    //-------------------------------redirect--------------------------------
    //guest
    public static final String REDIRECT_GUEST_LOGIN = "redirect:/web-application/testing";
    public static final String REDIRECT_GUEST_LOGOUT = "redirect:/web-application/testing/login";
    //student
    public static final String REDIRECT_STUDENT_INFO = "redirect:/web-application/testing/student/info";
    public static final String REDIRECT_STUDENT_TESTS = "redirect:/web-application/testing/student/tests";
    public static final String REDIRECT_STUDENT_RESULT = "redirect:/web-application/testing/student/result";
    public static final String REDIRECT_STUDENT_TEST = "redirect:/web-application/testing/student/test";
    //admin
    public static final String REDIRECT_ADMIN_INFO = "redirect:/web-application/testing/admin/info";
    public static final String REDIRECT_ADMIN_TESTS = "redirect:/web-application/testing/admin/showTests";
    public static final String REDIRECT_ADMIN_USERS = "redirect:/web-application/testing/admin/showUsers";
    public static final String REDIRECT_ADMIN_ADD_QUESTIONS = "redirect:/web-application/testing/admin/addQuestion";
    public static final String REDIRECT_ADMIN_EDIT_QUESTIONS_INFO = "redirect:/web-application/testing/admin/editQuestionInfo";
    public static final String REDIRECT_ADMIN_EDIT_TEST = "redirect:/web-application/testing/admin/editTest";
    public static final String REDIRECT_ADMIN_EDIT_TEST_INFO = "redirect:/web-application/testing/admin/editTestInfo";
    public static final String REDIRECT_ADMIN_CREATE_TEST = "redirect:/web-application/testing/admin/createTest";
    public static final String REDIRECT_ADMIN_CREATE_QUESTION = "redirect:/web-application/testing/admin/createQuestion";
    public static final String REDIRECT_ADMIN_USER_TESTS = "redirect:/web-application/testing/admin/userTests";

}
