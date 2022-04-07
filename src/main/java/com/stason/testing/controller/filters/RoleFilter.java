package com.stason.testing.controller.filters;

import com.stason.testing.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "RoleFilter")
public class RoleFilter implements Filter {
    private static final Logger logger = Logger.getLogger(RoleFilter.class.getName());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if(req.getSession().getAttribute("role")==null){
            logger.info("Set role as "+Role.GUEST.name());
            req.getSession().setAttribute("role", Role.GUEST.name());
            chain.doFilter(req, resp);
        }else {
            logger.info("Role is "+req.getSession().getAttribute("role"));
            chain.doFilter(req, resp);
        }
    }
}
