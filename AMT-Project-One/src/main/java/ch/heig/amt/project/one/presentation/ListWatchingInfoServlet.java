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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListWatchingInfoServlet extends HttpServlet {
    @EJB
    WatchingInfosManagerLocal watchingInfosManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int NB_RECORD_PRINT = 1000;
        List<WatchingInfo> watchingInfos = new ArrayList<>();
        User user = ((User)req.getSession().getAttribute("user"));
        String username = user.getUsername();
        int pagetable = 0;
        if(req.getParameter("nb") != null) {
            NB_RECORD_PRINT = Integer.valueOf(req.getParameter("nb"));
        }
        if(req.getParameter("pagetable") != null) {
            try {
                pagetable = Integer.valueOf(req.getParameter("pagetable"));
            } catch(NumberFormatException nb) {
                Logger.getLogger(ListWatchingInfoServlet.this.getClass().getName()).log(Level.SEVERE, null, nb);
            }
        }
        watchingInfos = watchingInfosManager.findAllTest(pagetable*NB_RECORD_PRINT, NB_RECORD_PRINT);

        req.setAttribute("username", username);
        if(watchingInfos.size() > 0) {
            int count = watchingInfosManager.count(user);
            int nbPage = count / NB_RECORD_PRINT + ((count % NB_RECORD_PRINT == 0) ? 0 : 1);
            req.setAttribute("nbPage", nbPage);
            req.setAttribute("pagetable", pagetable);
            req.setAttribute("watchingInfos", watchingInfos);
            req.setAttribute("nb", NB_RECORD_PRINT);
        }
        else {
        }
        resp.setContentType("text/html;charset=UTF-8");
        req.getRequestDispatcher("/WEB-INF/pages/listwatchinginfo.jsp").forward(req, resp);
    }
}
