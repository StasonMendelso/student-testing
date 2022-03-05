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
        String action = req.getParameter("action");
        String param = req.getQueryString();
//        System.out.println(param);
//        System.out.println(URI);
//        System.out.println(role);
//
        if (role.equals(Role.GUEST.name()) && (URI.contains("login") || URI.contains("register"))) {
            chain.doFilter(request, response);

        } else if (role.equals(Role.GUEST.name()) && URI.contains("controller") && !(action.contains("login") || action.contains("register"))) {
            System.out.println("Error accesing");
            ((HttpServletResponse) response).sendRedirect("/testing");
        } else if (URI.contains("/student") && role.equals(Role.STUDENT.name())) {
            chain.doFilter(request, response);
        } else if ((URI.contains("/admin") || URI.contains("controller")) && role.equals(Role.ADMIN.name())) {
            chain.doFilter(request, response);

        } else if (role.equals(Role.GUEST.name()) && (URI.contains("/admin") || URI.contains("/student"))) {
            if (!URI.contains("/login")) res.sendRedirect("/web-application/testing/login");
        } else if (role.equals(Role.GUEST.name())) {
            chain.doFilter(request, response);

        } else {
            System.out.println("Error accesing");
            chain.doFilter(request, response);
           // if (!URI.contains("/testing")) ((HttpServletResponse) response).sendRedirect("/web-application/testing");

    }
}}
