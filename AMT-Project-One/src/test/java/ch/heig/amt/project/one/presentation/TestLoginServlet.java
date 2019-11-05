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
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestLoginServlet {
    @Mock
    User user;
    @Mock
    UsersManagerLocal usersManagerLocal;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher requestDispatcher;

    List<String> errors;
    LoginServlet servlet;

    @BeforeEach
    public void setup() {
        errors = new ArrayList<>();
        servlet = new LoginServlet();
    }

    void HelperDoPostInit(String username, String password) {
        servlet.usersManagerLocal = usersManagerLocal;
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
    }

    void HelperDoPostTestInvalidConnection(List<String> errors) throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute("errors", errors);
        verify(request.getRequestDispatcher("/WEB-INF/pages/login.jsp"), atLeastOnce()).forward(request, response);
    }

    @Test
    void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(request.getRequestDispatcher("/WEB-INF/pages/login.jsp"), atLeastOnce()).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "password");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        when(usersManagerLocal.validConnection(username, password)).thenReturn(true);
        when(usersManagerLocal.findUserByUsername(username)).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(usersManagerLocal, atLeastOnce()).validConnection(username, password);
        verify(usersManagerLocal, atLeastOnce()).findUserByUsername(username);
        verify(request.getSession(), atLeastOnce()).setAttribute("user", user);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/restreint/series");
    }

    @Test
    void doPostWithWrongUsernameOrPassword() throws ServletException, IOException {
        HelperDoPostInit("Jojo", "password");
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        when(usersManagerLocal.validConnection(username, password)).thenReturn(false);
        errors.add("Le nom d'utilisateur ou le mot de passe doit être erroné");

        HelperDoPostTestInvalidConnection(errors);
    }

    @Test
    void doPostWithEmptyUsername() throws ServletException, IOException {
        HelperDoPostInit("", "password");
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        when(usersManagerLocal.validConnection(username, password)).thenReturn(false);
        errors.add("Le nom d'utilisateur ne peut pas être vide");
        errors.add("Le nom d'utilisateur ou le mot de passe doit être erroné");

        HelperDoPostTestInvalidConnection(errors);
    }

    @Test
    void doPostWithEmptyPassword() throws ServletException, IOException {
        HelperDoPostInit("Obyka", "");
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        when(usersManagerLocal.validConnection(username, password)).thenReturn(false);
        errors.add("Le mot de passe ne peut pas être vide");
        errors.add("Le nom d'utilisateur ou le mot de passe doit être erroné");

        HelperDoPostTestInvalidConnection(errors);
    }
}
