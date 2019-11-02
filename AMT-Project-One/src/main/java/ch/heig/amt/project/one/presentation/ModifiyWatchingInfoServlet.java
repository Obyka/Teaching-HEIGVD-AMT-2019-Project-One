package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.business.interfaces.ViewersManagerLocal;
import ch.heig.amt.project.one.business.interfaces.WatchingInfosManagerLocal;
import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;
import ch.heig.amt.project.one.model.WatchingInfo;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifiyWatchingInfoServlet extends HttpServlet {
    @EJB
    private WatchingInfosManagerLocal watchingInfosManagerLocal;

    @EJB
    private SeriesManagerLocal seriesManagerLocal;

    @EJB
    private ViewersManagerLocal viewersManagerLocal;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = ((User) request.getSession().getAttribute("user"));
        String IDSerie = request.getParameter("idserie");
        String IDViewer = request.getParameter("idviewer");
        String TimeSpent = request.getParameter("timespent");
        String BeginningDate = request.getParameter("beginningdate");

        long lIDSerie = 0;
        long lIDViewer = 0;
        int lTimeSpent = 0;

        List<String> errors = new ArrayList<>();
        if (IDSerie == null || IDSerie.trim().equals("")) {
            errors.add("La série ne peut pas être vide");
        }

        try{
            lIDSerie = Long.valueOf(IDSerie);
        }catch (Exception e){
            errors.add("Série invalide");
        }

        if (IDViewer == null || IDViewer.trim().equals("")) {
            errors.add("Le viewer ne peut pas être vide");
        }

        try{
            lIDViewer = Long.valueOf(IDViewer);
        }catch (Exception e){
            errors.add("Viewer invalide");
        }

        if (TimeSpent == null || TimeSpent.trim().equals("")) {
            errors.add("Le temps passé ne peut pas être vide");
        }

        try{
            lTimeSpent = Integer.valueOf(TimeSpent);
        }catch (Exception e){
            errors.add("Temps invalide");
        }

        Date dBeginningDate = new Date();

        try {
            dBeginningDate = new SimpleDateFormat("yyyy-MM-dd").parse(BeginningDate);
        }
        catch (Exception e) {
            Logger.getLogger(ch.heig.amt.project.one.presentation.AddViewerServlet.class.getName()).log(Level.SEVERE, null, e);
            errors.add("La date doit être au format décrit");
        }

        WatchingInfo watchingInfo = watchingInfosManagerLocal.findOne(user, lIDSerie, lIDViewer);
        if(watchingInfo.getId() == -1) {
            errors.add("Le profil de visionnage que vous voulez modifier n'existe pas");
        }

        if (errors.size() == 0) {
            watchingInfo.setTimeSpent(lTimeSpent);
            watchingInfo.setBeginningDate(dBeginningDate);

            boolean updated = watchingInfosManagerLocal.update(watchingInfo);

            if (updated) {
                request.setAttribute("stateOfCreation", "Le profil de visionnage a bien été modifié");
            } else {
                request.setAttribute("stateOfCreation", "Une erreur est survenue");
            }
        } else {
            request.setAttribute("errors", errors);
        }
        request.getRequestDispatcher("/WEB-INF/pages/modifyWatchingInfo.jsp").forward(request, response);
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
        } catch (NumberFormatException nb) {
            response.sendRedirect(request.getContextPath() + "/restreint/series");
        }

        WatchingInfo watchingInfo = watchingInfosManagerLocal.findOne(user, idSerie, idViewer);

        if(watchingInfo.getId() == -1) {
            response.sendRedirect(request.getContextPath() + "/restreint/series");
            return;
        }

        Serie serie = seriesManagerLocal.findById(user, idSerie);
        Viewer viewer = viewersManagerLocal.findById(user, idViewer);
        request.setAttribute("serie", serie);
        request.setAttribute("viewer", viewer);
        request.setAttribute("watchingInfo", watchingInfo);
        request.setAttribute("backToWebsite", "./detailserie?idserie=" + idSerie);
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/pages/modifyWatchingInfo.jsp").forward(request, response);
    }
}
