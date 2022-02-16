package com.stason.testing.controller.servlets;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.commands.LoginCommand;
import com.stason.testing.controller.commands.LogoutCommand;


import java.io.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class ControllerServlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>() ;

    public void init() {
        commands.put("login",new LoginCommand());
        commands.put("logout", new LogoutCommand());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        // out.println("<h1>" + message + "</h1>");
        out.println("<h1>.${messages['vovy']}</h1>");
        out.println("</body></html>");

        String action = request.getParameter("action");
        Command cmd = commands.get(action);
        System.out.println(action);
        System.out.println(cmd);
        String url = cmd.execute(request);
        System.out.println(url);
        if(url.contains("redirect:")){
            url = url.replaceAll("redirect:","");
            response.sendRedirect(url);
        }else{
            getServletContext().getRequestDispatcher(url).forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    public void destroy() {
    }
}