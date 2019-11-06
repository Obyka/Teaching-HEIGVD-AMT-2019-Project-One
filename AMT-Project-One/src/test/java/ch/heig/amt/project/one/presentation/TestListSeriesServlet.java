package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestListSeriesServlet {
    @Mock
    User user;
    @Mock
    List<Serie> series;
    @Mock
    HttpSession session;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    SeriesManagerLocal seriesManagerLocal;

    ListSeriesServlet servlet;
    String internError;
    int NB_RECORD_PRINT;

    @BeforeEach
    void setup() {
        servlet = new ListSeriesServlet();
        servlet.seriesManager = seriesManagerLocal;
        internError = null;
        NB_RECORD_PRINT = 50;
    }

    void HelperDoGetInit(String pagetable) {
        when(request.getRequestDispatcher("/WEB-INF/pages/listseries.jsp")).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getUsername()).thenReturn("jo.mercier");
        when(request.getParameter("pagetable")).thenReturn(pagetable);
    }

    void HelperDoGetVerify() throws ServletException, IOException {
        verify(request, atLeastOnce()).setAttribute("internError", internError);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doGet() throws ServletException, IOException, NumberFormatException {
        HelperDoGetInit("0");
        int pagetable = Integer.valueOf(request.getParameter("pagetable"));
        when(seriesManagerLocal.findAll(user, pagetable * NB_RECORD_PRINT, NB_RECORD_PRINT)).thenReturn(series);
        when(series.size()).thenReturn(50);

        servlet.doGet(request, response);

        verify(seriesManagerLocal, atLeastOnce()).count(user);
        verify(request, atLeastOnce()).setAttribute("nbPage", 0);
        verify(request, atLeastOnce()).setAttribute("pagetable", 0);
        verify(request, atLeastOnce()).setAttribute("username", "jo.mercier");
        verify(request, atLeastOnce()).setAttribute("series", series);

        HelperDoGetVerify();
    }

    @Test
    void doGetWithErrorOnPageTable() throws ServletException, IOException {
        HelperDoGetInit("");
        internError = "Aucune serie n'a été encore créée";

        servlet.doGet(request, response);

        HelperDoGetVerify();
    }
}
