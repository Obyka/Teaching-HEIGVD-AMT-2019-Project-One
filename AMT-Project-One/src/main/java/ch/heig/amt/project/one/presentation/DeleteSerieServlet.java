package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteSerieServlet extends HttpServlet {
    @EJB
    SeriesManagerLocal seriesManagerLocal;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sIdSerie = request.getParameter("idserie");
        User user = (User)request.getSession().getAttribute("user");
        if(sIdSerie != null || sIdSerie.trim().equals("")) {
            try {
                long idserie = Long.valueOf(sIdSerie);
                boolean deleted = seriesManagerLocal.delete(user, idserie);
            } catch (Exception e) {
                Logger.getLogger(ch.heig.amt.project.one.presentation.DeleteSerieServlet.this.getClass().getName()).log(Level.SEVERE, null, e);
            }
        }

        response.sendRedirect(request.getContextPath() + "/restreint/series");
    }
}
