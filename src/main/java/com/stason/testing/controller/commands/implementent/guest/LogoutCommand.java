package com.stason.testing.controller.commands.implementent.guest;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
/**
 * It is a Command, which log out the user
 * @author Stanislav Hlova
 * @version 1.0
 */
public class LogoutCommand implements Command {
    private static final Logger logger = Logger.getLogger(LogoutCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request) {
        String login = (String)request.getSession().getAttribute("login");
        Set<String> loggedUsers = (HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
        logger.info("LoggedUsers before logouting are "+loggedUsers);
        loggedUsers.remove(login);
        request.getSession().getServletContext().setAttribute("loggedUsers",loggedUsers);
        logger.info("LoggedUsers after logouting are "+loggedUsers);
        //Сохраняем язык чтобы не сменился когда сессия станет невалидной.
        request.getSession().invalidate();
        request.getSession().setAttribute("role", Role.GUEST.name());
        request.getSession().setAttribute("lang", request.getSession().getAttribute("lang"));
        return Path.REDIRECT_GUEST_LOGOUT;
    }
}
