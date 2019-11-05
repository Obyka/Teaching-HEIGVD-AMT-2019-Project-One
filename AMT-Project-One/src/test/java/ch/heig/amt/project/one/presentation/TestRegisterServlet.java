package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.UsersManagerLocal;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestRegisterServlet {
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
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password1")).thenReturn(password1);
        when(request.getParameter("password1")).thenReturn(password2);
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
        String password2 = request.getParameter("password2");

        servlet.doPost(request, response);

        verify(usersManagerLocal, atLeastOnce()).findUserByUsername(username);
        verify(usersManagerLocal, atLeastOnce()).create(username, password1);
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/login");
    }
}
