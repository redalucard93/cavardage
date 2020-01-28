package servlets.gestionNavigation;

import ejbs.IFacade;
import entities.Ville;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = "/mps/RedirigerRechercheTrajet")
public class RedirigerRechercheTrajet extends HttpServlet {

    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<Ville> villes = facade.getAllVilles();

        List<String> nomsVilles = new ArrayList<>();

        for(Ville v : villes){
            String nomVille = v.getNom();
            nomsVilles.add(nomVille);
        }


        session.setAttribute("villes", nomsVilles);

        request.getRequestDispatcher("/WEB-INF/pages/recherche_trajet.jsp").forward(request, response);
        session.removeAttribute("messageErreurReservation");

    }

}
