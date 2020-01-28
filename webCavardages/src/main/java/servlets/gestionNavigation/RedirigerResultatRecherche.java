package servlets.gestionNavigation;

import org.apache.tools.ant.taskdefs.condition.Http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/mps/RedirigerResultatRecherche")
public class RedirigerResultatRecherche extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/resultat_recherche.jsp").forward(request, response);
        request.getSession().removeAttribute("trajetsPrix");
        request.getSession().removeAttribute("villeArrive");
        request.getSession().removeAttribute("idVilleArrive");

    }

}
