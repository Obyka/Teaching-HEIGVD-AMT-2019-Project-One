package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.UsersManagerLocal;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.path.Route;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginServlet extends HttpServlet {
    @EJB
    private UsersManagerLocal usersManagerLocal;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("pathToLogin", Route.LOGIN);
        request.setAttribute("pathToRegister", Route.REGISTER);
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        List<String> errors = new ArrayList<>();
        if(username == null || username.trim().equals("")) {
            errors.add("Le nom d'utilisateur ne peut pas être vide");
        }
        if(password == null || password.trim().equals("")) {
            errors.add("Le mot de passe ne peut pas être libre");
        }
        boolean connectionSuccessful = usersManagerLocal.validConnection(username, password);
        if(!connectionSuccessful) {
            errors.add("Le nom d'utilisateur ou le mot de passe doit être erroné");
        }

        if(errors.size() == 0) {
            User user = usersManagerLocal.findUserByUsername(username);
            request.getSession(true);
            request.getSession().setAttribute("user", user);
            response.sendRedirect(Route.ALL_SERIE);
        } else {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
