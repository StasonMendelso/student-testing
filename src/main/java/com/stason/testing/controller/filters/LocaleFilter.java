package com.stason.testing.controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "LocaleFilter")
public class LocaleFilter implements Filter {
    static int count =1;
    private static final Logger logger = Logger.getLogger(LocaleFilter.class.getName());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("Filters time:"+count++);
        logger.info("Locale Filter");
        if(req.getParameter("lang")!=null) {
            req.getSession().setAttribute("lang", req.getParameter("lang"));
            logger.info("Change lang to \""+req.getParameter("lang")+"\"");
        }
        if(req.getSession().getAttribute("lang")==null){
            req.getSession().setAttribute("lang", "ua");
            logger.info("Set Lang by default \"UA\"");

        }
        chain.doFilter(request, response);
    }
    
}
