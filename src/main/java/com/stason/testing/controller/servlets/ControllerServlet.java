package com.stason.testing.controller.servlets;

import com.stason.testing.controller.commands.*;
import com.stason.testing.controller.commands.implementent.DefaultCommand;
import com.stason.testing.controller.commands.implementent.admin.*;
import com.stason.testing.controller.commands.implementent.student.*;
import com.stason.testing.controller.commands.implementent.guest.*;



import java.io.*;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ControllerServlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>() ;

    public void init() {
        commands.put("/",new DefaultCommand());
        commands.put("",new DefaultCommand());
        commands.put("/login",new LoginCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/registration", new RegistrationCommand());
        commands.put("/admin/info",new AdminInfoCommand());
        commands.put("/admin/showUsers", new ShowUsersCommand());
        commands.put("/admin/showTests", new ShowTestsCommand());
        commands.put("/admin/createTest", new CreateTestCommand());
        commands.put("/admin/createQuestion", new CreateQuestionCommand());
        commands.put("/student/info", new StudentInfoCommand());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("doGet");
        process(request,response);



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
        process(req,resp);
    }
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("It is servlet");
        System.out.println(request.getRequestURI());


            String uri = request.getRequestURI();
            uri = uri.replaceAll(".*/testing", "");
            Command command = commands.get(uri);
            if(command!=null) {
                String newUrl = command.execute(request);
                System.out.println("NewUrl is "+newUrl);
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