package com.stason.testing.controller.filters;

import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * It is a filter, which set a UTF-8 encoding for all requests and responses
 * @author Stanislav Hlova
 * @version 1.0
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    private static final Logger logger = Logger.getLogger(EncodingFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("Encoding Filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");

        chain.doFilter(req, res);
    }

}
