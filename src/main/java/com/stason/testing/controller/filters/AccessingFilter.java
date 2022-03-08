package com.stason.testing.controller.filters;

import com.stason.testing.model.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AccessingFilter")
public class AccessingFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("It is AccessingFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String role = (String) req.getSession().getAttribute("role");
        String URI = req.getRequestURI();

        if(URI.contains("/student") && role.equals(Role.STUDENT.name())) {
            chain.doFilter(request, response);
        }else if(URI.contains("/admin") && role.equals(Role.ADMIN.name())) {
            chain.doFilter(request, response);
        }else if((URI.contains("/login") || URI.contains("/registration") || URI.endsWith("/testing") || URI.endsWith("/testing/")) && role.equals(Role.GUEST.name())){
            chain.doFilter(request,response);
        }else{
            if(URI.contains("/login")) chain.doFilter(request,response);
            res.sendRedirect("/web-application/testing/login");
        }
    }
}
