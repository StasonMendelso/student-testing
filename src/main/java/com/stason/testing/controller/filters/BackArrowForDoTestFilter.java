package com.stason.testing.controller.filters;

import com.stason.testing.controller.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * It is a filter, which clean the cache of web-browser and when the user click on back arrow in his browser,
 * this forces browser to access the server again. Works when the user passed test and want to change answers after getting the result.
 * @author Stanislav Hlova
 * @version 1.0
 */
@WebFilter(filterName = "BackArrowForDoTestFilter")
public class BackArrowForDoTestFilter implements Filter {
    private static final Logger logger = Logger.getLogger(BackArrowForDoTestFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        res.setHeader("Pragma","no-cache");
        res.setDateHeader("Expires",0);
        if((req.getSession().getAttribute("test")==null && req.getParameter("id")==null)){
            res.sendRedirect(Path.REDIRECT_STUDENT_TESTS.replace("redirect:",""));
            return;
        }
        chain.doFilter(request, response);
    }
}
