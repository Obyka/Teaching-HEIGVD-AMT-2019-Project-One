package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.business.interfaces.ViewersManagerLocal;
import ch.heig.amt.project.one.business.interfaces.WatchingInfosManagerLocal;
import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;
import ch.heig.amt.project.one.model.WatchingInfo;
import ch.heig.amt.project.one.utils.Pair;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailSerieServlet extends HttpServlet {
    @EJB
    SeriesManagerLocal seriesManagerLocal;

    @EJB
    WatchingInfosManagerLocal watchingInfosManagerLocal;

    @EJB
    ViewersManagerLocal viewersManagerLocal;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int NB_RECORD_PRINT = 50;
        String internError = null;
        User user = (User)request.getSession().getAttribute("user");
        String sIdSerie = request.getParameter("idserie");
        int pagetable = 0;
        long idserie = 0;

        if(request.getParameter("pagetable") != null) {
            try {
                pagetable = Integer.valueOf(request.getParameter("pagetable"));
            } catch(NumberFormatException nb) {
                internError = "L'index du tableau doit être un entier";
            }
        }
        if(sIdSerie != null) {
            try {
                idserie = Long.valueOf(sIdSerie);
            } catch(NumberFormatException e) {
                internError = "L'id de la série n'est pas correcte";
            }

            int nbWI = watchingInfosManagerLocal.countBySerie(user,idserie);
            int nbPage = nbWI / NB_RECORD_PRINT + ((nbWI % NB_RECORD_PRINT == 0) ? 0 : 1);

            Serie serie = seriesManagerLocal.findById(user, idserie);
            if(serie.getId() != -1) {
                List<WatchingInfo> watchingInfoList = watchingInfosManagerLocal.findBySerie(user, serie, (pagetable * NB_RECORD_PRINT), NB_RECORD_PRINT);
                if(watchingInfoList.size() > 0) {
                    List<Pair<String, WatchingInfo>> viewersInfo = new ArrayList<>();
                    for(WatchingInfo w : watchingInfoList) {
                        Viewer viewer = viewersManagerLocal.findById(user, w.getIdViewer());
                        if(viewer.getId() != -1) {
                            Pair<String, WatchingInfo> pair = new Pair<>(viewer.getUsername(), w);
                            viewersInfo.add(pair);
                        }
                    }
                    request.setAttribute("username", user.getUsername());
                    request.setAttribute("serie", serie);
                    request.setAttribute("viewersInfos", viewersInfo);
                    request.setAttribute("nbPage", nbPage);
                    request.setAttribute("pagetable", pagetable);


                }
                else {
                    internError = "Il n'y a pas d'informations de visionnage pour cette série";
                }
            }
            else {
                internError = "La série est introuvable";
            }
            request.setAttribute("internError", internError);
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("/WEB-INF/pages/detailserie.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/restreint/series");
        }
    }
}
