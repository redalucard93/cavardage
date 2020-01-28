package servlets.gestionUtilisateur;

import ejbs.IFacade;
import entities.Avis;
import entities.Personne;
import exceptions.DonneesInvalidesException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/mps/RechercherAvis")
public class RechercherAvis extends HttpServlet {
    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");

        List<Avis> avis = facade.getAvisByIdPersonne(login);

        request.getSession().setAttribute("avis",avis);
        response.getWriter().write(request.getContextPath() + "/mps/RedirigerAvis");
    }
}
