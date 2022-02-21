package com.stason.testing.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "LocaleFilter")
public class LocaleFilter implements Filter {
    static int count =1;
    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println(count++);
        System.out.println("It is Filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if(req.getParameter("lang")!=null){
            req.getSession().setAttribute("lang", req.getParameter("lang"));
            String referrer = req.getHeader("referer");
            System.out.println("Referrer is "+referrer);
         //   res.sendRedirect(referrer);

        }
        if(req.getSession().getAttribute("lang")!=null){
            String lang= req.getSession().getAttribute("lang").toString();
            System.out.println("[INFO] IF-statement if(req.getSession().getAttribute(\"lang\")!=null). ShowAttribute LANG");
            System.out.println("lang is not a null");
            System.out.println("Lang = "+lang+".");

        }
        else{
            System.out.println("Lang is NULL.Set Lang by default");
            req.getSession().setAttribute("lang", "ua");
        }


        chain.doFilter(request, response);
    }
    
}
