package servlets.gestionUtilisateur;

import ejbs.IFacade;
import entities.Personne;
import entities.Voiture;
import exceptions.DonneesInvalidesException;
import exceptions.IntrouvableDansLaBaseException;


import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/mps/gestion_voiture")
public class GestionVoiture extends HttpServlet {
    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().getAttribute("loginSession");

        Personne p = null;

        try {
            p = facade.getPersonneByLogin((String) request.getSession().getAttribute("loginSession"));
        } catch (DonneesInvalidesException e) {
            response.sendRedirect(request.getContextPath() + "/mps/RedirigerAccueilServlet");
        }

        if (request.getParameter("action").equals("modif")) {

            try {
                facade.modifierVehicule(p.getLogin(), request.getParameter("modeleModif"), Integer.parseInt(request.getParameter("gabaritModif")), Integer.parseInt(request.getParameter("nbrPlacesModif")));
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            } catch (IntrouvableDansLaBaseException e) {
                e.printStackTrace();
            }

        } else if (request.getParameter("action").equals("ajout")) {
            try {
                facade.declarerVehicule(p.getLogin(), request.getParameter("modele"), Integer.parseInt(request.getParameter("gabarit")), Integer.parseInt(request.getParameter("nbrPlaces")));
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            } catch (IntrouvableDansLaBaseException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/mps/ConsulterProfilUtilisateur");

    }
}
