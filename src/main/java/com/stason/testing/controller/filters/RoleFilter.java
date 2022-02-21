package com.stason.testing.controller.filters;

import com.stason.testing.model.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "RoleFilter")
public class RoleFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("It is RoleFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        System.out.println(req.getSession().getAttribute("role"));
        if(req.getSession().getAttribute("role")==null){
            System.out.println("It is RoleFilter if statement");
            req.getSession().setAttribute("role", Role.GUEST.name());
            chain.doFilter(req, resp);
        }else {
            chain.doFilter(req, resp);
        }
    }
}
