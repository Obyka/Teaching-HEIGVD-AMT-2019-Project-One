package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permet à un utilisateur de visualiser toutes les séries
 */
public class ListSeriesServlet extends HttpServlet {
    @EJB
    SeriesManagerLocal seriesManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int NB_RECORD_PRINT = 50;
        String internError = null;
        List<Serie> series = new ArrayList<>();
        User user = ((User)req.getSession().getAttribute("user"));
        String username = user.getUsername();
        int pagetable = 0;
        if(req.getParameter("pagetable") != null) {
            try {
                pagetable = Integer.valueOf(req.getParameter("pagetable"));
            } catch(NumberFormatException nb) {
                Logger.getLogger(ch.heig.amt.project.one.presentation.ListSeriesServlet.this.getClass().getName()).log(Level.SEVERE, null, nb);
            }
        }
        series = seriesManager.findAll(user, (pagetable * NB_RECORD_PRINT), NB_RECORD_PRINT);

        req.setAttribute("username", username);
        if(series.size() > 0) {
            int count = seriesManager.count(user);
            int nbPage = count / NB_RECORD_PRINT + ((count % NB_RECORD_PRINT == 0) ? 0 : 1);
            req.setAttribute("nbPage", nbPage);
            req.setAttribute("pagetable", pagetable);
            req.setAttribute("series", series);
        }
        else {
            internError = "Aucune serie n'a été encore créée";
        }
        resp.setContentType("text/html;charset=UTF-8");
        req.setAttribute("internError", internError);
        req.getRequestDispatcher("/WEB-INF/pages/listseries.jsp").forward(req, resp);
    }
}
