package com.stason.testing.controller.filters;

import com.stason.testing.controller.commands.LogoutCommand;
import com.stason.testing.model.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "BackArrowFilter")
public class BackArrowFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("It is BackArrowFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        res.setHeader("Pragma","no-cache");
        res.setDateHeader("Expires",0);

        String role = (String) req.getSession().getAttribute("role");
        String URI = req.getRequestURI();

        //ToDo Добавить стрелочку назад для админа
        //ToDo Соединить два фильтра в один, потому что оба отвечают за доступ
        if(role.equals(Role.STUDENT.name())){
            if(URI.contains("student")){

            }else{
                new LogoutCommand().execute(req);
            }
        }
        if(role.equals(Role.ADMIN.name())){
            if(URI.contains("admin")){

            }else{
                new LogoutCommand().execute(req);
            }
        }

        chain.doFilter(request, response);
    }
}
