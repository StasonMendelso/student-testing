package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.utils.CommandsHelper;
import com.stason.testing.controller.utils.Constants;
import com.stason.testing.controller.utils.ErrorForUser;
import com.stason.testing.controller.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * It is a Command, which deletes user
 * @author Stanislav Hlova
 * @version 1.0
 */
public class DeleteUserCommand implements Command {
    private static final Logger logger = Logger.getLogger(DeleteUserCommand.class.getName());
    private final UserService userService = new UserService();
    @Override
    public String execute(HttpServletRequest request){
        if(request.getParameter("secretPassword")!=null){
            String secretPassword = request.getParameter("secretPassword");
            if (secretPassword.equals(Constants.PASSWORD_DELETE_USER)) {
                int userId = Integer.parseInt(request.getParameter("id"));
                if(request.getServletContext().getAttribute("logoutUsersId")!=null){
                    List<Integer> logoutUsersIdList = (List<Integer>)request.getServletContext().getAttribute("logoutUsersId");
                    boolean flag = true;
                    for(Integer idLogouted : logoutUsersIdList) {
                        if (idLogouted == userId) {
                            flag = false;
                            break;
                        }
                    }
                    if(flag) {
                        logoutUsersIdList.add(userId);
                        request.getServletContext().setAttribute("logoutUsersId",logoutUsersIdList);
                    }

                }
                userService.delete(userId);
                logger.info("Admin deleted user["+userId+"]");
                CommandsHelper.setAttributesInSessionForPagination(request);
            }else{
                CommandsHelper.setAttributesInSessionForPagination(request);
                request.getSession().setAttribute("error", ErrorForUser.SECRET_CODE_NOT_MATCH);

            }
        }
        return Path.REDIRECT_ADMIN_USERS;
    }
}
