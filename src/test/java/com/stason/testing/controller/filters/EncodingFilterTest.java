package com.stason.testing.controller.filters;

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

class EncodingFilterTest {
    EncodingFilter encodingFilter = new EncodingFilter();
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
    public void testEncodingFilter() throws ServletException, IOException {
        encodingFilter.doFilter(mockRequest,mockResponse,mockFilterChain);
        verify(mockRequest,times(1)).setCharacterEncoding("UTF-8");
        verify(mockResponse,times(1)).setCharacterEncoding("UTF-8");
        verify(mockResponse,times(1)).setContentType("text/html");
    }
}