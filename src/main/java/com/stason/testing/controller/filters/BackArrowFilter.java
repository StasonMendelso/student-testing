package com.stason.testing.controller.filters;

import com.stason.testing.controller.commands.implementent.guest.LogoutCommand;
import com.stason.testing.controller.servlets.ControllerServlet;
import com.stason.testing.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "BackArrowFilter")
public class BackArrowFilter implements Filter {
    private final  static Logger logger = Logger.getLogger(ControllerServlet.class.getName());

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        res.setHeader("Pragma","no-cache");
        res.setDateHeader("Expires",0);

        String role = (String) req.getSession().getAttribute("role");
        String URI = req.getRequestURI();

        if(role.equals(Role.STUDENT.name())){
            if(URI.contains("student")){

            }else{
                logger.info("Do logout, because URI doesn't contain /student");
                new LogoutCommand().execute(req);
            }
        }
        if(role.equals(Role.ADMIN.name())){
            if(URI.contains("admin")){

            }else{
                logger.info("Do logout, because URI doesn't contain /admin");
                new LogoutCommand().execute(req);
            }
        }

        chain.doFilter(request, response);
    }
}
