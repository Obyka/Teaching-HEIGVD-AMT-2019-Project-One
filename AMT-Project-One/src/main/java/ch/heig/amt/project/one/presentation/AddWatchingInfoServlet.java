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

public class AddWatchingInfoServlet extends HttpServlet {
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
        Date dBeggingDate = new Date();

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

        java.util.Date dBeginningDate = new Date();

        try {
            dBeginningDate = new SimpleDateFormat("yyyy-MM-dd").parse(BeginningDate);
        }
        catch (Exception e) {
            Logger.getLogger(ch.heig.amt.project.one.presentation.AddViewerServlet.class.getName()).log(Level.SEVERE, null, e);
            errors.add("La date doit être au format décrit");
        }


        if (errors.size() == 0) {
            WatchingInfo watchingInfo = WatchingInfo.builder()
                    .beginningDate(dBeginningDate)
                    .idSerie(lIDSerie)
                    .idViewer(lIDViewer)
                    .timeSpent(lTimeSpent)
                    .build();
            watchingInfo.setOwner(user.getId());

            boolean created = watchingInfosManagerLocal.create(watchingInfo);
            if (created) {
                request.setAttribute("stateOfCreation", "Le profil de visionnage a bien été créé");
            } else {
                    request.setAttribute("stateOfCreation", "Une erreur est survenue");
                    setParams(request, seriesManagerLocal.findAll(user, 0, seriesManagerLocal.count(user)), viewersManagerLocal.findAll(user, 0, viewersManagerLocal.count(user)));
            }
        } else {
            request.setAttribute("errors", errors);
            setParams(request, seriesManagerLocal.findAll(user, 0, seriesManagerLocal.count(user)), viewersManagerLocal.findAll(user, 0, viewersManagerLocal.count(user)));
        }
        request.getRequestDispatcher("/WEB-INF/pages/addWatchingInfo.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = ((User) request.getSession().getAttribute("user"));

        List<Serie> allSeries = new ArrayList<>();
        allSeries = seriesManagerLocal.findAll(user, 0, seriesManagerLocal.count(user));

        List<Viewer> allViewers = new ArrayList<>();
        allViewers = viewersManagerLocal.findAll(user, 0, viewersManagerLocal.count(user));

        response.setContentType("text/html;charset=UTF-8");
        setParams(request, allSeries, allViewers);
        request.getRequestDispatcher("/WEB-INF/pages/addWatchingInfo.jsp").forward(request, response);
    }

    private void setParams(HttpServletRequest request, List<Serie> allSeries, List<Viewer> allViewers){
        request.setAttribute("series",allSeries);
        request.setAttribute("viewers",allViewers);
    }
}
