package com.stason.testing.controller.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocaleFilterTest {
    LocaleFilter localeFilter = new LocaleFilter();
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
    void doLocaleFilterNullAttribute() throws ServletException, IOException {
        localeFilter.doFilter(mockRequest,mockResponse,mockFilterChain);
        verify(mockSession,times(1)).getAttribute("lang");
        verify(mockSession,times(1)).setAttribute("lang","ua");
    }
    @Test
    void doLocaleFilterNotNullAttribute() throws ServletException, IOException {
        when(mockRequest.getParameter("lang")).thenReturn("en");
        localeFilter.doFilter(mockRequest,mockResponse,mockFilterChain);
        verify(mockRequest,times(3)).getParameter("lang");
        verify(mockSession,times(1)).setAttribute("lang","en");
    }
}