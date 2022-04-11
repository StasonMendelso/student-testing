package com.stason.testing.controller.filters;

import com.stason.testing.controller.commands.implementent.guest.LogoutCommand;
import com.stason.testing.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * It is a filter, which clean the cache of web-browser and when the user click on back arrow in his browser,
 * this forces browser to access the server again (for login for example).
 * @author Stanislav Hlova
 * @version 1.0
 */
@WebFilter(filterName = "BackArrowFilter")
public class BackArrowFilter implements Filter {
    private static final Logger logger = Logger.getLogger(BackArrowFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);

        String role = (String) req.getSession().getAttribute("role");
        String URI = req.getRequestURI();

        if (role.equals(Role.STUDENT.name()) && !URI.contains("student")) {
            logger.info("Do logout, because URI doesn't contain /student");
            new LogoutCommand().execute(req);
        }
        if (role.equals(Role.ADMIN.name()) && !URI.contains("admin")) {
            logger.info("Do logout, because URI doesn't contain /admin");
            new LogoutCommand().execute(req);
        }

        chain.doFilter(request, response);
    }
}
