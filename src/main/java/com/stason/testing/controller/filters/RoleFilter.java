package com.stason.testing.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
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
        if(req.getSession().getAttribute("role")==null){
            req.getSession().setAttribute("role","guest");
        }
        chain.doFilter(request, response);
    }
}
