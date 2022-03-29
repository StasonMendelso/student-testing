package com.stason.testing.controller.filters;

import com.stason.testing.controller.servlets.ControllerServlet;
import com.stason.testing.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "AccessingFilter")
public class AccessingFilter implements Filter {
    private final  static Logger logger = Logger.getLogger(ControllerServlet.class.getName());

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String role = (String) req.getSession().getAttribute("role");
        String URI = req.getRequestURI();
        String URL = req.getRequestURL().toString();


        //Перевірка чи не заблокували користувача під час його сесії або чи він не заблокований зовсім
        if((URI.contains("/student") && role.equals(Role.STUDENT.name())) ||(URI.contains("/admin") && role.equals(Role.ADMIN.name()))) {
            List<Integer> blockedList = (List<Integer>) req.getServletContext().getAttribute("blockedUsers");
            int userId = (int) req.getSession().getAttribute("id");
            for (int blockedId : blockedList) {
                if (blockedId == userId) {
                    logger.info("Accessing Filter - User "+userId+" is blocked");
                    res.sendRedirect("/web-application/testing/login");
                    return;
                }
            }
            List<Integer> logoutUsersId = (List<Integer>) req.getServletContext().getAttribute("logoutUsersId");
            for (int logoutId : logoutUsersId) {
                if (logoutId == userId) {
                    logger.info("Accessing Filter - User "+userId+" must be log outed");
                    logoutUsersId.remove((Integer) logoutId);
                    req.getServletContext().setAttribute("logoutUsersId", logoutUsersId);
                    res.sendRedirect("/web-application/testing/login");
                    return;
                }
            }
        }
        if(URI.contains("/student") && role.equals(Role.STUDENT.name())) {
            logger.info("Accessing Filter - Student has access to this URL "+URL);
            chain.doFilter(request, response);
        }else if(URI.contains("/admin") && role.equals(Role.ADMIN.name())) {
            logger.info("Accessing Filter - Admin has access to this URL "+URL);
            chain.doFilter(request, response);
        }else if((URI.contains("/login") || URI.contains("/registration") || URI.endsWith("/testing") || URI.endsWith("/testing/") || URI.endsWith("/recovery")) && role.equals(Role.GUEST.name())){
            logger.info("Accessing Filter - Guest has access to this URL "+URL);
            chain.doFilter(request,response);
        }else{
            if(URI.contains("/login")) chain.doFilter(request,response);
            logger.warn("Accessing Filter - ACCESS DENIED FOR "+ role + " for URL "+URL);
            res.sendRedirect("/web-application/testing/login");
        }
    }
}
