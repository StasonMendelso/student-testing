package com.stason.testing.controller.filters;

import com.stason.testing.controller.commands.implementent.guest.LogoutCommand;
import com.stason.testing.controller.exceptions.ForbiddenException;
import com.stason.testing.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * It is a filter, which check if the user's role has the permission to this URLs
 * @author Stanislav Hlova
 * @version 1.0
 */
@WebFilter(filterName = "AccessingFilter")
public class AccessingFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AccessingFilter.class.getName());

    private final Map<Role, HashSet<String>> accessingURLs = new HashMap<>();

    @Override
    public void init(FilterConfig config) throws ServletException {
        HashSet<String> accessingURLAdmin = new HashSet<>();
        //config.getInitParameter();
        accessingURLAdmin.add("/");
        accessingURLAdmin.add("");
        accessingURLAdmin.add("/logout");
        accessingURLAdmin.add("/admin/info");
        accessingURLAdmin.add("/admin/changePassword");
        accessingURLAdmin.add("/admin/showUsers");
        accessingURLAdmin.add("/admin/showTests");
        accessingURLAdmin.add("/admin/deleteTest");
        accessingURLAdmin.add("/admin/editTest");
        accessingURLAdmin.add("/admin/editTestInfo");
        accessingURLAdmin.add("/admin/editTestDeleteQuestion");
        accessingURLAdmin.add("/admin/editQuestionInfo");
        accessingURLAdmin.add("/admin/deleteAnswer");
        accessingURLAdmin.add("/admin/addQuestion");
        accessingURLAdmin.add("/admin/createTest");
        accessingURLAdmin.add("/admin/createQuestion");
        accessingURLAdmin.add("/admin/userTests");
        accessingURLAdmin.add("/admin/deletePassedTest");
        accessingURLAdmin.add("/admin/blockUser");
        accessingURLAdmin.add("/admin/unblockUser");
        accessingURLAdmin.add("/admin/deleteUser");
        accessingURLAdmin.add("/admin/editUser");
        accessingURLs.put(Role.ADMIN, accessingURLAdmin);

        HashSet<String> accessingURLStudent = new HashSet<>();
        accessingURLStudent.add("/");
        accessingURLStudent.add("");
        accessingURLStudent.add("/logout");
        accessingURLStudent.add("/student/changePassword");
        accessingURLStudent.add("/student/info");
        accessingURLStudent.add("/student/tests");
        accessingURLStudent.add("/student/test");
        accessingURLStudent.add("/student/result");
        accessingURLs.put(Role.STUDENT, accessingURLStudent);

        HashSet<String> accessingURLGuest = new HashSet<>();
        accessingURLGuest.add("/");
        accessingURLGuest.add("");
        accessingURLGuest.add("/login");
        accessingURLGuest.add("/recovery");
        accessingURLGuest.add("/registration");
        accessingURLs.put(Role.GUEST, accessingURLGuest);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String role = (String) req.getSession().getAttribute("role");
        String requestURI = req.getRequestURI();
        String URL = req.getRequestURL().toString();
        String newURI = requestURI.replaceAll(".*/testing","");

        //Перевірка чи не заблокували користувача під час його сесії або чи він не заблокований зовсім
        if (!role.equals(Role.GUEST.name())) {
            List<Integer> blockedList = (List<Integer>) req.getServletContext().getAttribute("blockedUsers");
            int userId = (int) req.getSession().getAttribute("id");
            for (Integer blockedId : blockedList) {
                if (blockedId == userId) {
                    logger.info("Accessing Filter - User " + userId + " is blocked");
                    new LogoutCommand().execute(req);
                    res.sendRedirect("/web-application/testing/login");
                    return;
                }
            }
            List<Integer> logoutUsersId = (List<Integer>) req.getServletContext().getAttribute("logoutUsersId");
            for (Integer logoutId : logoutUsersId) {
                if (logoutId == userId) {
                    logger.info("Accessing Filter - User " + userId + " must be log outed");
                    logoutUsersId.remove(logoutId);
                    req.getServletContext().setAttribute("logoutUsersId", logoutUsersId);
                    new LogoutCommand().execute(req);
                    res.sendRedirect("/web-application/testing/login");
                    return;
                }
            }
        }

        if(role.equals(Role.GUEST.name()) && accessingURLs.get(Role.GUEST).contains(newURI)){
                logger.info("Accessing Filter - Guest has access to this URL " + URL);
                chain.doFilter(request,response);
                return;

        }
        if(role.equals(Role.STUDENT.name()) && accessingURLs.get(Role.STUDENT).contains(newURI)){
                logger.info("Accessing Filter - Student has access to this URL " + URL);
                chain.doFilter(request,response);
                return;
        }
        if(role.equals(Role.ADMIN.name()) && accessingURLs.get(Role.ADMIN).contains(newURI)){
                logger.info("Accessing Filter - Admin has access to this URL " + URL);
                chain.doFilter(request,response);
                return;
        }

        accessingURLs.forEach((role1, strings) -> {
            if(strings.contains(newURI)){
                logger.warn("Accessing Filter - ACCESS DENIED FOR " + role + " for URL " + URL);
                throw new ForbiddenException("Accessing was denied");
            }
        });

        chain.doFilter(request,response);

    }
}
