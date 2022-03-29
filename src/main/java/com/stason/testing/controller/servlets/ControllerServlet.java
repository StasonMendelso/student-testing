package com.stason.testing.controller.servlets;

import com.stason.testing.controller.commands.*;
import com.stason.testing.controller.commands.implementent.ChangePasswordCommand;
import com.stason.testing.controller.commands.implementent.DefaultCommand;
import com.stason.testing.controller.commands.implementent.admin.DeleteTestCommand;
import com.stason.testing.controller.commands.implementent.admin.EditTestCommand;
import com.stason.testing.controller.commands.implementent.admin.*;
import com.stason.testing.controller.commands.implementent.student.*;
import com.stason.testing.controller.commands.implementent.guest.*;
import com.stason.testing.controller.commands.implementent.student.ShowTestsCommand;
import org.apache.log4j.Logger;



import java.io.*;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

public class ControllerServlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>() ;
private final  static Logger logger = Logger.getLogger(ControllerServlet.class.getName());
    public void init() {
        logger.debug("Initialization "+ControllerServlet.class.getName());
        commands.put("/",new DefaultCommand());
        commands.put("",new DefaultCommand());
        commands.put("/login",new LoginCommand());
        commands.put("/recovery",new RecoveryPasswordCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/registration", new RegistrationCommand());
        commands.put("/admin/info",new AdminInfoCommand());
        commands.put("/admin/changePassword",new ChangePasswordCommand());
        commands.put("/student/changePassword",new ChangePasswordCommand());
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
        commands.put("/student/tests", new ShowTestsCommand());
        commands.put("/student/test", new DoTestCommand());
        commands.put("/student/result", new ShowTestResultCommand());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request,response);



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        process(req,resp);
    }
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet");
            String uri = request.getRequestURI();
        logger.info("Old URI is:"+uri);
            uri = uri.replaceAll(".*/testing", "");
            uri = uri.replaceAll("\\?.*","");
            Command command = commands.getOrDefault(uri, new DefaultCommand());
        logger.info("Command is:"+command);
        if(command!=null) {
                String newUrl = command.execute(request);
            logger.info("New URL is:"+newUrl);
                if (newUrl.contains("redirect:")) {
                    response.sendRedirect(newUrl.replaceAll("redirect:", ""));
                } else {
                    request.getRequestDispatcher(newUrl).forward(request, response);
                }
            }
        }


    public void destroy() {
    }
}