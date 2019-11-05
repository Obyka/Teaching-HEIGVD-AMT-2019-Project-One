package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModifySerieServlet extends HttpServlet {
    @EJB
    private SeriesManagerLocal seriesManagerLocal;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = ((User)request.getSession().getAttribute("user"));
        String id = (request.getParameter("id"));
        String titre = request.getParameter("title");
        String genre = request.getParameter("genre");
        String producer = request.getParameter("producer");
        String ageRestriction = request.getParameter("ageRestriction");
        String synopsis = request.getParameter("synopsis");

        boolean errorOccured = false;

        List<String> errors = new ArrayList<>();
        if(id == null || id.trim().equals("")){
            errors.add("Erreur d'autorisation");
            errorOccured = true;
        }

        if(titre == null || titre.trim().equals("")) {
            errors.add("Le titre ne peut pas être vide");
            errorOccured = true;
        }
        if(genre == null || genre.trim().equals("")) {
            errors.add("Le genre ne peut pas être vide");
            errorOccured = true;
        }
        if(producer == null || producer.trim().equals("")) {
            errors.add("Le titre ne peut pas être vide");
            errorOccured = true;
        }
        if(ageRestriction == null || ageRestriction.trim().equals("")) {
            errors.add("La restriction d'âge ne peut pas être vide");
            errorOccured = true;
        }
        if(synopsis == null || synopsis.trim().equals("")) {
            errors.add("Le synopsis ne peut pas être vide");
            errorOccured = true;
        }
        long idParsed = 0;
        int iAgeRestriction = 0;
        try{
            idParsed = Long.parseLong(id);
        } catch (Exception e){
            errors.add("La série demandée n'est pas modifiable");
            errorOccured = true;
        }
        try{
            iAgeRestriction = Integer.valueOf(ageRestriction);
        } catch (Exception e){
            errors.add("La restriction d'âge est invalide.");
            errorOccured = true;
        }

        if(!errorOccured) {
            Serie serie = seriesManagerLocal.findById(user, idParsed);
            if(serie.getOwner() == user.getId() && serie.getId() != -1) {
                Serie serieUpdated = Serie.builder()
                        .title(titre)
                        .genre(genre)
                        .producer(producer)
                        .ageRestriction(iAgeRestriction)
                        .synopsis(synopsis)
                        .build();
                serieUpdated.setOwner(user.getId());
                serieUpdated.setId(idParsed);

                boolean updated = seriesManagerLocal.update(serieUpdated);
                if (updated) {
                    request.setAttribute("stateOfCreation", "La série a bien été modifiée");
                } else {
                    request.setAttribute("stateOfCreation", "Une erreur est survenue");
                    setParams(request, seriesManagerLocal.findById(user, idParsed));
                }
            }
        } else {
            request.setAttribute("errors", errors);
            setParams(request, seriesManagerLocal.findById(user, idParsed));
        }
        request.getRequestDispatcher("/WEB-INF/pages/modifySerie.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sIdSerie = request.getParameter("idserie");
        User user = ((User)request.getSession().getAttribute("user"));
        if(sIdSerie != null) {
            long idserie = Long.valueOf(sIdSerie);
            Serie serie = seriesManagerLocal.findById(user, idserie);
            setParams(request, serie);
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("/WEB-INF/pages/modifySerie.jsp").forward(request, response);
        } else{
            response.sendRedirect(request.getContextPath() + "/restreint/series");
        }
    }

    private void setParams(HttpServletRequest request, Serie serie){
        request.setAttribute("serie", serie);
    }
}
