package com.stason.testing.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("it is encoding filter!");
//        System.out.println(request.getContentType());
//        System.out.println(response.getContentType());
//        System.out.println(response.getCharacterEncoding());
//        System.out.println(request.getCharacterEncoding());
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");


//        System.out.println(request.getContentType());
//        System.out.println(response.getContentType());
//        System.out.println(response.getCharacterEncoding());
//        System.out.println(request.getCharacterEncoding());
        chain.doFilter(req, res);
    }

}
