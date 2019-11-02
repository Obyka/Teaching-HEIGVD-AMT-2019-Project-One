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

        long idSerie = 0;
        long idViewer = 0;

        if(sidSerie == null || sIdViewer == null) {
            response.sendRedirect(request.getContextPath() + "/restreint/series");
            return;
        }

        try {
            idSerie = Long.valueOf(sidSerie);
            idViewer = Long.valueOf(sIdViewer);
            boolean deleted = watchingInfosManagerLocal.delete(user, idSerie, idViewer);
            response.sendRedirect(request.getContextPath() + "/restreint/detailserie?idserie" + idSerie);
        } catch (NumberFormatException nb) {
            response.sendRedirect(request.getContextPath() + "/restreint/series");
            return;
        }
    }
}
