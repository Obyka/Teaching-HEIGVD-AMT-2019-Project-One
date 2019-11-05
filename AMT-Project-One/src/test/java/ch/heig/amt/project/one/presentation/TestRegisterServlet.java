package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.UsersManagerLocal;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestRegisterServlet {
    @Mock
    User user;
    @Mock
    UsersManagerLocal usersManagerLocal;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;

    List<String> errors;
    RegisterServlet servlet;

    @BeforeEach
    public void setup() {
        errors = new ArrayList<>();
        servlet = new RegisterServlet();
    }

    void HelperDoPostInit(String username, String password1, String password2) {
        servlet.usersManagerLocal = usersManagerLocal;
        when(usersManagerLocal.findUserByUsername(username)).thenReturn(user);
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password1")).thenReturn(password1);
        when(request.getParameter("password2")).thenReturn(password2);
    }

    void HelperDoPostInvalid() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "password", "password");
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        when(usersManagerLocal.create(username, password1)).thenReturn(true);
        when(request.getContextPath()).thenReturn("AMT-project-one");

        servlet.doPost(request, response);

        verify(usersManagerLocal, atLeastOnce()).findUserByUsername(username);
        verify(usersManagerLocal, atLeastOnce()).create(username, password1);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/login");
    }

    @Test
    void doPostWithDuplicateUser() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "password", "password");
        String username = request.getParameter("username");
        when(user.getUsername()).thenReturn(username);

        errors.add("Le compte existe déjà, veuillez-vous connecter");

        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyUsername() throws ServletException, IOException {
        HelperDoPostInit("", "password", "password");

        errors.add("Le nom d'utilisateur ne peut pas être vide");

        HelperDoPostInvalid();
    }

    @Test
    void doPostWithEmptyPassword1() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "", "password");

        errors.add("Le mot de passe ne peut pas être vide");
        errors.add("Les mots de passe doivent correspondrent");

        HelperDoPostInvalid();
    }

    @Test
    void doPostWithWitEmptyPassword2() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "password", "");

        errors.add("La répétition du mot de passe ne peut pas être vide");
        errors.add("Les mots de passe doivent correspondrent");

        HelperDoPostInvalid();
    }

    @Test
    void doPostWithDifferentPassword() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "password", "password2");

        errors.add("Les mots de passe doivent correspondrent");

        HelperDoPostInvalid();
    }
}
