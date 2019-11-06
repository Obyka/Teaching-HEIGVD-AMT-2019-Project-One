package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.ViewersManagerLocal;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestAddViewerServlet {
    @Mock
    User user;
    @Mock
    Viewer viewer;
    @Mock
    HttpSession session;
    @Mock
    ViewersManagerLocal viewersManagerLocal;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;

    List<String> errors;
    AddViewerServlet servlet;

    @BeforeEach
    void setup() {
        servlet = new AddViewerServlet();
        errors = new ArrayList<>();
    }

    void HelperDoPostInit(String firstname, String lastname, String username, String genre, String birthdate) {
        servlet.viewersManagerLocal = viewersManagerLocal;
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/addViewer.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("firstname")).thenReturn(firstname);
        when(request.getParameter("genre")).thenReturn(genre);
        when(request.getParameter("lastname")).thenReturn(lastname);
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("birthdate")).thenReturn(birthdate);
    }

    void HelperDoPostInvalid() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doGet() throws ServletException, IOException {
       when(request.getRequestDispatcher("/WEB-INF/pages/addViewer.jsp")).thenReturn(requestDispatcher);

       servlet.doGet(request, response);

       verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        HelperDoPostInit("Jordan", "Mercier", "jo.mercier", "Homme", "1998-03-31");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String username = request.getParameter("username");
        String genre = request.getParameter("genre");
        String birthdate = request.getParameter("birthdate");
        Date dbirthdate = new Date();
        try {
            dbirthdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthdate);
        }
        catch (Exception e) {
            Logger.getLogger(ch.heig.amt.project.one.presentation.TestAddViewerServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        viewer = Viewer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .genre(genre)
                .birthDate(dbirthdate)
                .build();
        viewer.setOwner(user.getId());
        when(viewersManagerLocal.create(viewer)).thenReturn(true);

        servlet.doPost(request, response);

        verify(viewersManagerLocal, atLeastOnce()).create(viewer);
        verify(request, atLeastOnce()).setAttribute("stateOfCreation", "Le viewer a bien été crée");
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPostWithEmptyFirstname() throws ServletException, IOException {
        HelperDoPostInit("", "Mercier", "jo.mercier", "Homme", "1998-03-31");
        errors.add("Le prénom ne peut pas être vide");
        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyLastname() throws ServletException, IOException {
        HelperDoPostInit("Jordan", "", "jo.mercier", "Homme", "1998-03-31");
        errors.add("Le nom ne peut pas être vide");
        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyUsername() throws ServletException, IOException {
        HelperDoPostInit("Jordan", "Mercier", "", "Homme", "1998-03-31");
        errors.add("Le nom d'utilisateur ne peut pas être vide");
        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyGenre() throws ServletException, IOException {
        HelperDoPostInit("Jordan", "Mercier", "jo.mercier", "", "1998-03-31");
        errors.add("Le genre ne peut pas être vide");
        HelperDoPostInvalid();
    }

    @Test
    void doPostWithNoDefinedGenre() throws ServletException, IOException {
        HelperDoPostInit("Jordan", "Mercier", "jo.mercier", "Genre", "1998-03-31");
        errors.add("Le genre doit être défini");
        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyBirthdate() throws ServletException, IOException {
        HelperDoPostInit("Jordan", "Mercier", "jo.mercier", "Homme", "");
        errors.add("La date de naissance ne peut pas être vide");
        errors.add("La date doit être au format décrit");
        HelperDoPostInvalid();
    }
}
