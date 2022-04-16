package com.stason.testing.controller.filters;

import com.stason.testing.controller.commands.implementent.guest.LogoutCommand;
import com.stason.testing.model.dao.ConnectionPool;
import com.stason.testing.model.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BackArrowFilterTest {
    BackArrowFilter backArrowFilter = new BackArrowFilter();
    @Mock
    HttpSession mockSession = mock(HttpSession.class);
    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    ServletContext mockServletContext = mock (ServletContext.class);
    FilterChain mockFilterChain = mock(FilterChain.class);
    @BeforeEach
    void setUp(){
        when(mockRequest.getSession()).thenReturn(mockSession);
    }

    @Test
    public void testBackArrowFilter() throws ServletException, IOException {
        when(mockSession.getAttribute("role")).thenReturn(Role.STUDENT.name());
        when(mockRequest.getRequestURI()).thenReturn("admasdnasd");
        when(mockRequest.getServletContext()).thenReturn(mockServletContext);
        Set<String> set = new HashSet<>();
        set.add("user");
        when(mockServletContext.getAttribute("loggedUsers")).thenReturn(set);
        backArrowFilter.doFilter(mockRequest,mockResponse,mockFilterChain);
        when(mockSession.getAttribute("role")).thenReturn(Role.ADMIN.name());
        backArrowFilter.doFilter(mockRequest,mockResponse,mockFilterChain);
        verify(mockRequest, times(2)).getRequestURI();
        verify(mockSession, times(2)).getAttribute("role");
    }
}