package ch.heig.amt.project.one.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Ce filtre permet d'empêcher aux utilisateurs connectés d'accéder aux pages de login et de register
 */
public class LoggedFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connecté.
         */
        if (session.getAttribute("user") != null) {
            /* Redirection vers la page publique */
            response.sendRedirect(request.getContextPath() + "/restreint/series");
        } else {
            /* Affichage de la page restreinte */
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }
}