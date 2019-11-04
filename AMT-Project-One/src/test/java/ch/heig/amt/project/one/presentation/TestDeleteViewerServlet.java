package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.ViewersManagerLocal;
import ch.heig.amt.project.one.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestDeleteViewerServlet {
    @Mock
    User user;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ViewersManagerLocal viewersManagerLocal;
    @Mock
    HttpSession session;

    DeleteViewerServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new DeleteViewerServlet();
        user = User.builder().id(1).username("Obyka").password("Password").build();
        servlet.viewersManagerLocal = viewersManagerLocal;

        when(request.getContextPath()).thenReturn("AMT-project-one");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
    }

    @Test
    void doGetValidDelete() throws ServletException, IOException {
        when(request.getParameter("idviewer")).thenReturn("1");
        servlet.doGet(request, response);

        verify(viewersManagerLocal, atLeastOnce()).delete(user, 1);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/restreint/viewers");
    }

    @Test
    void doGetInvalidDelete() throws ServletException, IOException {
        when(request.getParameter("idviewer")).thenReturn("");
        servlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/restreint/viewers");
    }
}
