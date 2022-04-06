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
import com.stason.testing.controller.exceptions.ServiceException;
import com.stason.testing.controller.utils.CommandsHelper;
import org.apache.log4j.Logger;


import java.io.*;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ControllerServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(ControllerServlet.class.getName());
    private Map<String, Command> commands = new HashMap<>();

    public void init() {
        logger.debug("Initialization " + ControllerServlet.class.getName());
        commands = CommandsHelper.getCommands();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet");
        String uri = request.getRequestURI();
        logger.info("Old URI is:" + uri);
        uri = uri.replaceAll(".*/testing", "");
        // uri = uri.replaceAll("\\?.*", "");
        Command command = commands.getOrDefault(uri, new DefaultCommand());
        logger.info("Command is:" + command);
        try {
            String newUrl = command.execute(request);
            logger.info("New URL is:" + newUrl);
            if (newUrl.contains("redirect:")) {
                response.sendRedirect(newUrl.replaceAll("redirect:", ""));
            } else {
                request.getRequestDispatcher(newUrl).forward(request, response);
            }
        } catch (ServiceException ex) {
            logger.error("Service Exception error was catch", ex);
        } catch (RuntimeException ex) {
            logger.error("Runtime error was catch", ex);
            throw ex;
        }
    }

}