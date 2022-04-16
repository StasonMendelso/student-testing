package com.stason.testing.controller.filters;

import com.stason.testing.model.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleFilterTest {
    RoleFilter roleFilter = new RoleFilter();
    @Mock
    HttpSession mockSession = mock(HttpSession.class);
    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    FilterChain mockFilterChain = mock(FilterChain.class);
    @BeforeEach
    void setUp(){
        when(mockRequest.getSession()).thenReturn(mockSession);
    }

    @Test
    void doRoleFilterNull() throws ServletException, IOException {
        roleFilter.doFilter(mockRequest,mockResponse,mockFilterChain);
        verify(mockSession,times(1)).setAttribute("role", Role.GUEST.name());
        verify(mockSession,times(1)).getAttribute("role");
    }
    @Test
    void doRoleFilterNotNull() throws ServletException, IOException {
        when(mockSession.getAttribute("role")).thenReturn(Role.STUDENT.name());
        roleFilter.doFilter(mockRequest,mockResponse,mockFilterChain);
        verify(mockSession,times(2)).getAttribute("role");
    }
}