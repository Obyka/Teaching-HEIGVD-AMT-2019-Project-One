package ch.heig.amt.project.one.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestIndexServlet {
    @Mock
    HttpServletResponse response;
    @Mock
    HttpServletRequest request;

    IndexServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new IndexServlet();

        when(request.getContextPath()).thenReturn("AMT-project-one");
    }

    @Test
    void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/login");
    }

    @Test
    void doPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/login");
    }
}
