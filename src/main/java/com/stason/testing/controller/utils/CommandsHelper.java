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
import java.util.HashMap;
import java.util.Map;

public class CommandsHelper {
    public static String validatePasswordForPasswordCommand(HttpServletRequest request){
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
    public static Map<String, Command> getCommands(){
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
}
