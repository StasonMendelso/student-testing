package com.stason.testing.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    String encoding;
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getServletContext().getInitParameter("encoding");
    }

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



//        System.out.println(request.getContentType());
//        System.out.println(response.getContentType());
//        System.out.println(response.getCharacterEncoding());
//        System.out.println(request.getCharacterEncoding());
        chain.doFilter(req, res);
    }

}
