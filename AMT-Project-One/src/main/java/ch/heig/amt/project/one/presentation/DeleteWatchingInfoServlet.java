package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.WatchingInfosManagerLocal;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.WatchingInfo;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteWatchingInfoServlet extends HttpServlet {
    @EJB
    WatchingInfosManagerLocal watchingInfosManagerLocal;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = ((User) request.getSession().getAttribute("user"));
        String sidSerie = request.getParameter("idserie");
        String sIdViewer = request.getParameter("idviewer");

        try {
            long idSerie = Long.valueOf(sidSerie);
            long idViewer = Long.valueOf(sIdViewer);
            boolean deleted = watchingInfosManagerLocal.delete(user, idSerie, idViewer);
            response.sendRedirect(request.getContextPath() + "/restreint/detailserie?idserie=" + idSerie);
        } catch (Exception e) {
            Logger.getLogger(ch.heig.amt.project.one.presentation.DeleteWatchingInfoServlet.this.getClass().getName()).log(Level.SEVERE, null, e);
            response.sendRedirect(request.getContextPath() + "/restreint/series");
            return;
        }
    }
}
