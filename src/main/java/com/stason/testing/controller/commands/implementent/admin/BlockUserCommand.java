package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.utils.Constants;
import com.stason.testing.controller.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BlockUserCommand implements Command {
    private final  static Logger logger = Logger.getLogger(BlockUserCommand.class.getName());
    private final UserService userService =new UserService();
    @Override
    public String execute(HttpServletRequest request) {
        if(request.getParameter("secretPassword")==null){
            return Path.REDIRECT_ADMIN_USERS;
        }else{
            String secretPassword =request.getParameter("secretPassword");
            if(secretPassword.equals(Constants.PASSWORD_BLOCK_USER)){
                int userId = Integer.parseInt(request.getParameter("id"));

                userService.block(userId);
                setAttributes(request);
                List<Integer> blockedList = (List<Integer>)request.getServletContext().getAttribute("blockedUsers");
                Integer id = Integer.valueOf(request.getParameter("id"));
                blockedList.add(id);
                request.getServletContext().setAttribute("blockedUsers",blockedList);
                logger.info("Admin["+request.getSession().getAttribute("id")+"] blocked user["+userId+"]");
                return Path.REDIRECT_ADMIN_USERS;
            }else{
                setAttributes(request);
                request.getSession().setAttribute("error", "Секретний код не співпадає");
                return Path.REDIRECT_ADMIN_USERS;
            }
        }
    }
    private void setAttributes(HttpServletRequest request){
        request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
        request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
    }
}
