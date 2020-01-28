package servlets.gestionUtilisateur;

import ejbs.IFacade;
import entities.Personne;
import exceptions.DonneesInvalidesException;
import exceptions.IntrouvableDansLaBaseException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/mps/GestionAdministrateur")
public class GestionAdministrateur extends HttpServlet {
    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().getAttribute("loginSession");
        Personne admin = null;
        try {
            admin = facade.getPersonneByLogin((String) request.getSession().getAttribute("loginSession"));
        } catch (DonneesInvalidesException e) {
            response.sendRedirect(request.getContextPath() + "/mps/RedirigerAccueilServlet");
        }

        if (request.getParameter("action").equals("addGabarit")) {
            try {
                facade.ajouterGabarit(request.getParameter("add_libelleGabarit"));
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("modifierGabarit")) {
            try {
                facade.modifierGabarit(Integer.parseInt(request.getParameter("modifGabarit")), request.getParameter("modifier_libelleGabarit"));
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            } catch (IntrouvableDansLaBaseException e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("supprimerGabarit")) {
            try {
                facade.supprimerGabarit(Integer.parseInt(request.getParameter("delGabarit")));
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            } catch (IntrouvableDansLaBaseException e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("addVille")) {
            try {
                facade.ajouterVille(request.getParameter("add_nomVille"));
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("modifierVille")) {
            try {
                facade.modifierVille(Integer.parseInt(request.getParameter("modifVille")), request.getParameter("modifier_nomVille"));
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            } catch (IntrouvableDansLaBaseException e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("supprimerVille")) {
            try {
                facade.supprimerVille(Integer.parseInt(request.getParameter("delVille")));
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            } catch (IntrouvableDansLaBaseException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/mps/ConsulterProfilAdministrateur");
    }
}