package com.stason.testing.controller.filters;

import com.stason.testing.model.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldInitializer;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccessingFilterTest {
    AccessingFilter accessingFilter = new AccessingFilter();
    @Mock
    HttpSession mockSession = mock(HttpSession.class);
    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    ServletContext mockServletContext = mock(ServletContext.class);
    FilterChain mockFilterChain = mock(FilterChain.class);
    FilterConfig mockFilterConfig = mock(FilterConfig.class);

    @BeforeEach
    void setUp() {
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getServletContext()).thenReturn(mockServletContext);

    }

    @Test
    void init() throws ServletException {
        accessingFilter.init(mockFilterConfig);
    }


    @Test
    void doFilterGUEST() throws Exception {
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("testing/login"));
        when(mockRequest.getRequestURI()).thenReturn("/login");
        when(mockSession.getAttribute("role")).thenReturn(Role.GUEST.name());
        accessingFilter.init(mockFilterConfig);
        accessingFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
    }
    @Test
    void doFilterADMIN() throws Exception {
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("testing/admin/info"));
        when(mockRequest.getRequestURI()).thenReturn("/admin/info");
        when(mockSession.getAttribute("role")).thenReturn(Role.ADMIN.name());
        when(mockSession.getAttribute("id")).thenReturn(1);
        when(mockServletContext.getAttribute("blockedUsers")).thenReturn(Arrays.asList(2,4));
        when(mockServletContext.getAttribute("logoutUsersId")).thenReturn(Arrays.asList(3,5));

        accessingFilter.init(mockFilterConfig);
        accessingFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
    }
    @Test
    void doFilterSTUDENT() throws Exception {
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("testing/student/info"));
        when(mockRequest.getRequestURI()).thenReturn("/student/info");
        when(mockSession.getAttribute("role")).thenReturn(Role.STUDENT.name());
        when(mockSession.getAttribute("id")).thenReturn(1);
        when(mockServletContext.getAttribute("blockedUsers")).thenReturn(Arrays.asList(2,4));
        when(mockServletContext.getAttribute("logoutUsersId")).thenReturn(Arrays.asList(3,5));

        accessingFilter.init(mockFilterConfig);
        accessingFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
    }
}