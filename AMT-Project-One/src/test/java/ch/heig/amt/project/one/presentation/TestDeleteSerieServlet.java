package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestDeleteSerieServlet {
    @Mock
    User user;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    SeriesManagerLocal seriesManagerLocal;
    @Mock
    HttpSession session;

    DeleteSerieServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new DeleteSerieServlet();
        servlet.seriesManagerLocal = seriesManagerLocal;

        when(request.getContextPath()).thenReturn("AMT-project-one");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
    }

    @Test
    void doGetValidDelete() throws ServletException, IOException {
        when(request.getParameter("idserie")).thenReturn("1");
        servlet.doGet(request, response);

        verify(seriesManagerLocal, atLeastOnce()).delete(user, 1);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/restreint/series");
    }

    @Test
    void doGetInvalidDelete() throws ServletException, IOException {
        when(request.getParameter("idserie")).thenReturn("");
        servlet.doGet(request, response);
        
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/restreint/series");
    }
}