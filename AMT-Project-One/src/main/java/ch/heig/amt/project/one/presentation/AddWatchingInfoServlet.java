package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.DAO.WatchingInfosManager;
import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.business.interfaces.WatchingInfosManagerLocal;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.WatchingInfo;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddWatchingInfoServlet extends HttpServlet {
    @EJB
    private WatchingInfosManagerLocal watchingInfosManagerLocal;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = ((User) request.getSession().getAttribute("user"));
        String IDSerie = request.getParameter("idserie");
        String IDViewer = request.getParameter("idviewer");
        String TimeSpent = request.getParameter("timespent");
        String BeginningDate = request.getParameter("beginningdate");
        String OwnerID = request.getParameter("ownerid");

        long lIDSerie = 0;
        long lIDViewer = 0;
        long lIDOwner = 0;
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

        if (OwnerID == null || OwnerID.trim().equals("")) {
            errors.add("Le propriétaire ne peut pas être vide");
        }

        try{
            lIDOwner = Long.valueOf(OwnerID);
        }catch (Exception e){
            errors.add("Propriétaire invalide");
        }

        if (BeginningDate == null || BeginningDate.trim().equals("")) {
            errors.add("La date de départ ne peut pas être vide");
        }

        try{
            dBeggingDate = new Date(BeginningDate);
        } catch (Exception e){
            errors.add("Date invalide");
        }


        if (errors.size() == 0) {
            WatchingInfo watchingInfo = WatchingInfo.builder()
                    .beginningDate(dBeggingDate)
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
            }
        } else {
            request.setAttribute("errors", errors);
        }
        request.getRequestDispatcher("/WEB-INF/pages/addSerie.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/pages/addWatchingInfol.jsp").forward(request, response);
    }
}
l