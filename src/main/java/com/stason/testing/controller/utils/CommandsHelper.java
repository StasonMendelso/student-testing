package com.stason.testing.controller.utils;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.commands.implementent.ChangePasswordCommand;
import com.stason.testing.controller.commands.implementent.DefaultCommand;
import com.stason.testing.controller.commands.implementent.admin.*;
import com.stason.testing.controller.commands.implementent.guest.LoginCommand;
import com.stason.testing.controller.commands.implementent.guest.LogoutCommand;
import com.stason.testing.controller.commands.implementent.guest.RecoveryPasswordCommand;
import com.stason.testing.controller.commands.implementent.guest.RegistrationCommand;
import com.stason.testing.controller.commands.implementent.student.DoTestCommand;
import com.stason.testing.controller.commands.implementent.student.InfoCommand;
import com.stason.testing.controller.commands.implementent.student.ShowTestResultCommand;
import com.stason.testing.controller.services.ValidatorService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandsHelper {
    private CommandsHelper() {
    }

    public static String validatePasswordForPasswordCommand(HttpServletRequest request) {
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");
        //Validate
        if (!ValidatorService.validatePassword(password) && !ValidatorService.validatePassword(repeatedPassword)) {
            request.setAttribute("error", ErrorForUser.INVALID_PASSWORD);
            return Path.RECOVERY_CREATE_NEW_PASSWORD;
        }
        //Check password
        if (!ValidatorService.isPasswordRepeated(password, repeatedPassword)) {
            request.setAttribute("error", ErrorForUser.PASSWORD_NOT_MATCH);
            return Path.RECOVERY_CREATE_NEW_PASSWORD;
        }
        return null;
    }

    public static Map<String, Command> getCommands() {
        final Map<String, Command> commands = new HashMap<>();
        commands.put("/", new DefaultCommand());
        commands.put("", new DefaultCommand());
        commands.put("/login", new LoginCommand());
        commands.put("/recovery", new RecoveryPasswordCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/registration", new RegistrationCommand());
        commands.put("/admin/info", new AdminInfoCommand());
        commands.put("/admin/changePassword", new ChangePasswordCommand());
        commands.put("/student/changePassword", new ChangePasswordCommand());
        commands.put("/admin/showUsers", new ShowUsersCommand());
        commands.put("/admin/showTests", new com.stason.testing.controller.commands.implementent.admin.ShowTestsCommand());
        commands.put("/admin/deleteTest", new DeleteTestCommand());
        commands.put("/admin/editTest", new EditTestCommand());
        commands.put("/admin/editTestInfo", new EditTestInfoCommand());
        commands.put("/admin/editTestDeleteQuestion", new DeleteQuestionCommand());
        commands.put("/admin/editQuestionInfo", new EditQuestionCommand());
        commands.put("/admin/deleteAnswer", new DeleteAnswerCommand());
        commands.put("/admin/addQuestion", new AddQuestionCommand());
        commands.put("/admin/createTest", new CreateTestCommand());
        commands.put("/admin/createQuestion", new CreateQuestionCommand());
        commands.put("/admin/userTests", new ShowUsersTestsCommand());
        commands.put("/admin/deletePassedTest", new DeletePassedTestByUserCommand());
        commands.put("/admin/blockUser", new BlockUserCommand());
        commands.put("/admin/unblockUser", new UnblockUserCommand());
        commands.put("/admin/deleteUser", new DeleteUserCommand());
        commands.put("/admin/editUser", new EditUserCommand());
        commands.put("/student/info", new InfoCommand());
        commands.put("/student/tests", new com.stason.testing.controller.commands.implementent.student.ShowTestsCommand());
        commands.put("/student/test", new DoTestCommand());
        commands.put("/student/result", new ShowTestResultCommand());
        return commands;
    }

    public static boolean isProperlyCheckboxChecked(HttpServletRequest request) {
        String rightOptions = Arrays.toString(request.getParameterValues("opt"));
        // Выбран 1 вариант как правильный, но нету варианта ответа
        if (rightOptions.contains("1") && request.getParameter("answer1").isEmpty()) return false;
        // Выбран 2 вариант как правильный, но нету варианта ответа
        if (rightOptions.contains("2") && request.getParameter("answer2").isEmpty()) return false;
        // Выбран 3 вариант как правильный, но нету варианта ответа
        if (rightOptions.contains("3") && request.getParameter("answer3").isEmpty()) return false;
        // Выбран 4 вариант как правильный, но нету варианта ответа
        if (rightOptions.contains("4") && request.getParameter("answer4").isEmpty()) return false;

        return true;
    }

    public static void validateQuestionParameters(HttpServletRequest request, List<ErrorForUser> errorForUserList) {
        String questionName = EncodingConverter.convertFromISOtoUTF8(request.getParameter("questionName"));
        if (!ValidatorService.validateQuestionText(questionName)) {
            errorForUserList.add(ErrorForUser.INVALID_QUESTION_NAME);
        }
        for (int i = 1; i <= 4; i++) {
            String paramName = "answer" + i;
            if (request.getParameter(paramName).isEmpty()) {
                continue;
            }
            String answerText = EncodingConverter.convertFromISOtoUTF8(request.getParameter(paramName));
            if (!ValidatorService.validateAnswerText(answerText) && !errorForUserList.contains(ErrorForUser.INVALID_ANSWER_NAME)) {
                errorForUserList.add(ErrorForUser.INVALID_ANSWER_NAME);
            }

        }
    }
    public static void setAttributesInSessionForPagination(HttpServletRequest request) {
        request.getSession().setAttribute("pageNumber", request.getParameter("pageNumber"));
        request.getSession().setAttribute("paginationParameter", request.getParameter("paginationParameter"));
    }
}
