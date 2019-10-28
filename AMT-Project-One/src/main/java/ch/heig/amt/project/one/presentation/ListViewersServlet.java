package ch.heig.amt.project.one.presentation;

import ch.heig.amt.project.one.business.interfaces.ViewersManagerLocal;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;
import ch.heig.amt.project.one.path.Route;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListViewersServlet extends HttpServlet {
    @EJB
    ViewersManagerLocal viewersManagerLocal;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int NB_RECORD_PRINT = 50;
        User user = ((User)request.getSession().getAttribute("user"));
        String username = user.getUsername();
        int pagetable = 0;
        if(request.getParameter("pagetable") != null) {
            pagetable = Integer.valueOf(request.getParameter("pagetable"));
        }
        List<Viewer> viewers = viewersManagerLocal.findAll(user, (pagetable * NB_RECORD_PRINT), ((pagetable + 1) * NB_RECORD_PRINT));
        int nbPage = viewersManagerLocal.count() / NB_RECORD_PRINT + ((viewers.size() % NB_RECORD_PRINT == 0) ? 0 : 1);

        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("pathToViewers", Route.ALL_VIEWER);
        request.setAttribute("pathToSeries", Route.ALL_SERIE);
        request.setAttribute("pathToDeleteViewer", Route.DELETE_VIEWER);
        request.setAttribute("pathToLogout", Route.LOGOUT);
        request.setAttribute("username", username);
        request.setAttribute("nbPage", nbPage);
        request.setAttribute("viewers", viewers);
        request.getRequestDispatcher("/WEB-INF/pages/listviewers.jsp").forward(request, response);
    }
}
