package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.UserService;
import com.stason.testing.controller.utils.Path;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DeleteUserCommand implements Command {
    private final UserService userService = new UserService();
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(request.getParameter("secretPassword")==null){
            return Path.REDIRECT_ADMIN_USERS;
        }else {
            String secretPassword = request.getParameter("secretPassword");
            if (secretPassword.equals("delete")) { // todo добавити константний клас з паролями

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

                userService.deletePassedTestsByUserId(userId);
                userService.delete(userId);
                request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
                request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
                return Path.REDIRECT_ADMIN_USERS;
            }else{
                request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
                request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
                request.getSession().setAttribute("error", "Секретний код не співпадає");
                return Path.REDIRECT_ADMIN_USERS;
            }
        }
    }
}
