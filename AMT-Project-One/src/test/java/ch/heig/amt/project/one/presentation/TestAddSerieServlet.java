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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestAddSerieServlet {
    @Mock
    User user;
    @Mock
    Serie serie;
    @Mock
    HttpSession session;
    @Mock
    SeriesManagerLocal seriesManagerLocal;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;

    List<String> errors;
    AddSerieServlet servlet;

    @BeforeEach
    void setup() {
        servlet = new AddSerieServlet();
        errors = new ArrayList<>();
    }

    void HelperDoPostInit(String title, String genre, String producer, String ageRestriction, String synopsis) {
        servlet.seriesManagerLocal = seriesManagerLocal;
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getRequestDispatcher("/WEB-INF/pages/addSerie.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("title")).thenReturn(title);
        when(request.getParameter("genre")).thenReturn(genre);
        when(request.getParameter("producer")).thenReturn(producer);
        when(request.getParameter("ageRestriction")).thenReturn(ageRestriction);
        when(request.getParameter("synopsis")).thenReturn(synopsis);
    }

    void HelperDoPostInvalid() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/addSerie.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        HelperDoPostInit("X-files", "Sci-fi", "Fox", "16", "Alien thing !");
        String titre = request.getParameter("title");
        String genre = request.getParameter("genre");
        String producer = request.getParameter("producer");
        String ageRestriction = request.getParameter("ageRestriction");
        String synopsis = request.getParameter("synopsis");
        int iAgeRestriction = Integer.valueOf(ageRestriction);
        serie = Serie.builder()
                .title(titre)
                .genre(genre)
                .producer(producer)
                .ageRestriction(iAgeRestriction)
                .synopsis(synopsis)
                .build();
        serie.setOwner(user.getId());
        when(seriesManagerLocal.create(serie)).thenReturn(true);

        servlet.doPost(request, response);

        verify(seriesManagerLocal, atLeastOnce()).create(serie);
        verify(request, atLeastOnce()).setAttribute("stateOfCreation", "La série a bien été crée");
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPostWithEmptyTitle() throws ServletException, IOException {
        HelperDoPostInit("", "Sci-fi", "Fox", "16", "Alien thing !");
        errors.add("Le titre ne peut pas être vide");
        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyGenre() throws ServletException, IOException {
        HelperDoPostInit("X-files", "", "Fox", "16", "Alien thing !");
        errors.add("Le genre ne peut pas être vide");
        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyProducer() throws ServletException, IOException {
        HelperDoPostInit("X-files", "Sci-fi", "", "16", "Alien thing !");
        errors.add("Le producteur ne peut pas être vide");
        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyAgeRestriction() throws ServletException, IOException {
        HelperDoPostInit("X-files", "Sci-fi", "Sci-fi", "", "Alien thing !");
        errors.add("La restriction d'âge ne peut pas être vide");
        errors.add("L'âge doit être entier");
        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptySynopsis() throws ServletException, IOException {
        HelperDoPostInit("X-files", "Sci-fi", "Sci-fi", "16", "");
        errors.add("Le synopsis ne peut pas être vide");
        HelperDoPostInvalid();
    }
}