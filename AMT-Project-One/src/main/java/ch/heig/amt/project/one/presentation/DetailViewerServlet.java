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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailViewerServlet extends HttpServlet {

    @EJB
    WatchingInfosManagerLocal watchingInfosManagerLocal;

    @EJB
    ViewersManagerLocal viewersManagerLocal;

    @EJB
    SeriesManagerLocal seriesManagerLocal;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int NB_RECORD_PRINT = 50;
        String internError = null;
        User user = (User)request.getSession().getAttribute("user");
        String sIdViewer = request.getParameter("idviewer");
        int pagetable = 0;
        long IdViewer = 0;

        if(request.getParameter("pagetable") != null) {
            try {
                pagetable = Integer.valueOf(request.getParameter("pagetable"));
            } catch(NumberFormatException nb) {
                internError = "L'index du tableau doit être un entier";
            }
        }
        if(sIdViewer != null) {
            try {
                IdViewer = Long.valueOf(sIdViewer);
            } catch(NumberFormatException e) {
                internError = "L'id du viewer n'est pas correcte";
            }

            int nbWI = watchingInfosManagerLocal.countByViewer(user,IdViewer);
            int nbPage = nbWI / NB_RECORD_PRINT + ((nbWI % NB_RECORD_PRINT == 0) ? 0 : 1);

            Viewer viewer = viewersManagerLocal.findById(user, IdViewer);
            if(viewer.getId() != -1) {
                List<WatchingInfo> watchingInfoList = watchingInfosManagerLocal.findByViewer(user, viewer, (pagetable * NB_RECORD_PRINT),  NB_RECORD_PRINT);
                if(watchingInfoList.size() > 0) {
                    List<Pair<String, WatchingInfo>> seriesInfo = new ArrayList<>();
                    for(WatchingInfo w : watchingInfoList) {
                        Serie serie = seriesManagerLocal.findById(user, w.getIdSerie());
                        if(viewer.getId() != -1) {
                            Pair<String, WatchingInfo> pair = new Pair<>(serie.getTitle(), w);
                            seriesInfo.add(pair);
                        }
                    }
                    request.setAttribute("username", user.getUsername());
                    request.setAttribute("viewer", viewer);
                    request.setAttribute("seriesInfo", seriesInfo);
                    request.setAttribute("nbPage", nbPage);

                }
                else {
                    internError = "Il n'y a pas d'informations de visionnage pour ce spectateur";
                }
            }
            else {
                internError = "Le spectateur est introuvable";
            }
            request.setAttribute("internError", internError);
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("/WEB-INF/pages/detailViewer.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/restreint/viewers");
        }
    }
}
