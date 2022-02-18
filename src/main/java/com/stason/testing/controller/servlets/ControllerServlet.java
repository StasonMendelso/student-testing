package com.stason.testing.controller.servlets;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.commands.LoginCommand;
import com.stason.testing.controller.commands.LogoutCommand;
import com.stason.testing.controller.commands.RegisterCommand;


import java.io.*;

import java.nio.charset.StandardCharsets;
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
        commands.put("register", new RegisterCommand());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

//        String afterEncoding= new String(request.getParameter("login").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//        String message=request.getParameter("login");
//        System.out.println(message);
//        System.out.println(afterEncoding);
//        System.out.println(request.getCharacterEncoding());
//        // Hello
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>" + message + afterEncoding+"</h1>");
//        out.println("<h1>.вапавпвап</h1>");
//        out.println("</body></html>");

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