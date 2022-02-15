package com.stason.testing.controller.filters;

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
        String URL = ((HttpServletRequest) request).getRequestURI();
        System.out.println(URL);
        System.out.println(role);
        if(URL.contains("controller")){
            chain.doFilter(request,response);

        }else if(URL.contains("/student") && role.equals("student")){
            chain.doFilter(request, response);
        }else if(URL.contains("/admin") && role.equals("admin")){
            chain.doFilter(request, response);

        }else if(role.equals("guest") && (URL.contains("/admin") || URL.contains("/student"))){
            req.getRequestDispatcher("/testing").forward(request,response);
        }else  if(role.equals("guest")){
           chain.doFilter(request,response);

        }else {
            chain.doFilter(request, response);
        }

    }
}
