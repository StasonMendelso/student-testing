package com.stason.testing.controller.listeners;

import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class InitializationServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DaoFactory factory = DaoFactory.getInstance();
        UserDao userDao = factory.createUserDao();
        List<Integer> blockedUsers = userDao.findIdBlockedUsers();
        for(int i=0;i<blockedUsers.size();i++){
            System.out.println(blockedUsers.get(i));
        }
        List<Integer> logoutUsersIdList = new ArrayList<>();
        sce.getServletContext().setAttribute("logoutUsersId", logoutUsersIdList);
        sce.getServletContext().setAttribute("blockedUsers",blockedUsers);
    }
}
