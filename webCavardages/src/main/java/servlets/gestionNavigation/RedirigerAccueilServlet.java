package servlets.gestionNavigation;

import ejbs.IFacade;
import entities.Ville;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/mps/RedirigerAccueilServlet")

public class RedirigerAccueilServlet extends HttpServlet {

    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ville> villes = facade.getAllVilles();
        request.setAttribute("villes", villes);


        request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
    }

}
