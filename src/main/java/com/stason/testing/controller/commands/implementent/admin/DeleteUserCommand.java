package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.model.dao.DaoFactory;
import com.stason.testing.model.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DeleteUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        if(request.getParameter("secretPassword")==null){
            return "redirect:/web-application/testing/admin/showUsers";
        }else {
            String secretPassword = request.getParameter("secretPassword");
            if (secretPassword.equals("delete")) { // todo добавити константний клас з паролями
                DaoFactory factory = DaoFactory.getInstance();
                UserDao userDao = factory.createUserDao();
                int id = Integer.parseInt(request.getParameter("id"));
                if(request.getServletContext().getAttribute("logoutUsersId")!=null){
                    List<Integer> logoutUsersIdList = (List<Integer>)request.getServletContext().getAttribute("logoutUsersId");
                    boolean flag = true;
                    for(Integer idLogouted : logoutUsersIdList) {
                        if (idLogouted == id) {
                            flag = false;
                            break;
                        }
                    }
                    if(flag) {
                        logoutUsersIdList.add(id);
                        request.getServletContext().setAttribute("logoutUsersId",logoutUsersIdList);
                    }

                }
                userDao.deletePassedTestByUserId(id);
                userDao.delete(id);
                request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
                request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
                return "redirect:/web-application/testing/admin/showUsers";
            }else{
                request.getSession().setAttribute("pageNumber",request.getParameter("pageNumber"));
                request.getSession().setAttribute("paginationParameter",request.getParameter("paginationParameter"));
                request.getSession().setAttribute("error", "Секретний код не співпадає");
                return "redirect:/web-application/testing/admin/showUsers";
            }
        }
    }
}
