package com.stason.testing.controller.listeners;

import com.stason.testing.controller.servlets.ControllerServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.HashSet;

@WebListener
public class DestroyingListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
    private final  static Logger logger = Logger.getLogger(ControllerServlet.class.getName());

    public DestroyingListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        HashSet<String> loggedUsers = (HashSet<String>) se.getSession().getServletContext().getAttribute("loggedUsers");
        String login = (String) se.getSession()
                .getAttribute("login");
        loggedUsers.remove(login);
        se.getSession().setAttribute("loggedUsers", loggedUsers);
        logger.info("The user with login "+login+" has removed from loggedUsers");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}