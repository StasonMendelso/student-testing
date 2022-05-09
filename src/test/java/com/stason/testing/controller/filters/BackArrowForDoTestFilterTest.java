package com.stason.testing.controller.filters;

import com.stason.testing.controller.utils.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BackArrowForDoTestFilterTest {
    BackArrowForDoTestFilter backArrowForDoTestFilter = new BackArrowForDoTestFilter();
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
    public void testFilterNullAttributes() throws ServletException, IOException {
        backArrowForDoTestFilter.doFilter(mockRequest,mockResponse,mockFilterChain);
        verify(mockResponse,times(1)).sendRedirect(Path.REDIRECT_STUDENT_TESTS.replace("redirect:",""));
    }
    @Test
    public void testFilterNotNullAttributes() throws ServletException, IOException {
        when(mockSession.getAttribute("test")).thenReturn("test");
        backArrowForDoTestFilter.doFilter(mockRequest,mockResponse,mockFilterChain);
        verify(mockFilterChain,times(1)).doFilter(mockRequest,mockResponse);
    }
}