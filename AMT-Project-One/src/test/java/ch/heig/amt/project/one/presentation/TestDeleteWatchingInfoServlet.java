package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.WatchingInfosManagerLocal;
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
public class TestDeleteWatchingInfoServlet {
    @Mock
    User user;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    WatchingInfosManagerLocal watchingInfosManagerLocal;
    @Mock
    HttpSession session;

    DeleteWatchingInfoServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new DeleteWatchingInfoServlet();
        user = User.builder().id(1).username("Obyka").password("Password").build();
        servlet.watchingInfosManagerLocal = watchingInfosManagerLocal;

        when(request.getContextPath()).thenReturn("AMT-project-one");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
    }

    @Test
    void doGetValidDelete() throws ServletException, IOException {
        when(request.getParameter("idserie")).thenReturn("1");
        when(request.getParameter("idviewer")).thenReturn("1");
        servlet.doGet(request, response);

        verify(watchingInfosManagerLocal, atLeastOnce()).delete(user, 1, 1);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/restreint/detailserie?idserie=1");
    }

    @Test
    void doInvalidDelete() throws ServletException, IOException {
        when(request.getParameter("idserie")).thenReturn("");
        when(request.getParameter("idviewer")).thenReturn("");
        servlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/restreint/series");
    }
}
