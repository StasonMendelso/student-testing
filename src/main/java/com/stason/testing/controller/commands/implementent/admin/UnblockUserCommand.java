package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.utils.Constants;
import com.stason.testing.controller.utils.Path;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;

import java.util.List;

public class UnblockUserCommand implements Command {
    private final  static Logger logger = Logger.getLogger(EditUserCommand.class.getName());
    final UserService userService = new UserService();
    @Override
    public String execute(HttpServletRequest request){
        if(request.getParameter("secretPassword")==null){
            return Path.REDIRECT_ADMIN_USERS;
        }else{
            String secretPassword =request.getParameter("secretPassword");
            if(secretPassword.equals(Constants.PASSWORD_UNBLOCK_USER)) {
                int userId = Integer.parseInt(request.getParameter("id"));

                userService.unblock(userId);
                request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
                request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
                List<Integer> blockedList = (List<Integer>)request.getServletContext().getAttribute("blockedUsers");
                Integer id = Integer.valueOf(request.getParameter("id"));
                blockedList.remove(id);
                request.getServletContext().setAttribute("blockedUsers",blockedList);
                logger.info("Admin["+request.getSession().getAttribute("id")+"] unblocked user["+userId+"]");
                return Path.REDIRECT_ADMIN_USERS;
            }else{
                request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
                request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
                request.getSession().setAttribute("error", "Секретний код не співпадає"); // TODO  локалізація
                return Path.REDIRECT_ADMIN_USERS;
            }
        }
    }
}
