package com.stason.testing.controller.listeners;

import com.stason.testing.controller.services.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * It is an initialization servlet context listener
 * @author Stanislav Hlova
 * @version 1.0
 */
@WebListener
public class InitializationServletContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(InitializationServletContextListener.class.getName());

    /**
     * Initialized ServletContext with blocked users and an empty List of logouted id, which can be uses in further
     * @param sce a ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Initialization of ServletContext");
        UserService userService = new UserService();
        List<Integer> blockedUsers = userService.findIdBlockedUsers();
        List<Integer> logoutUsersIdList = new ArrayList<>();
        sce.getServletContext().setAttribute("logoutUsersId", logoutUsersIdList);
        sce.getServletContext().setAttribute("blockedUsers",blockedUsers);
        logger.info("Blocked users (id:"+Arrays.toString(blockedUsers.toArray())+") were added to ServletContext");
    }
}
