package servlets.gestionUtilisateur;

import ejbs.IFacade;
import entities.Avis;
import entities.Personne;
import entities.Reservation;
import entities.Trajet;
import exceptions.DonneesInvalidesException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/mps/ConsulterProfilUtilisateur")
public class ConsulterProfilUtilisateur extends HttpServlet {
    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Personne p = null;

        try {
            p = facade.getPersonneByLogin((String) request.getSession().getAttribute("loginSession"));
        } catch (DonneesInvalidesException e) {
            response.sendRedirect(request.getContextPath() + "/mps/RedirigerAccueilServlet");
        }

        request.setAttribute("listeGabarit", facade.getAllGabarit());

        request.setAttribute("personne", p);

        // Récuperation des données concernant les trajets créer
        List<Trajet> lesTrajets = facade.getTrajetByPersonne(p.getLogin());
        request.setAttribute("trajetsCree", lesTrajets);

        List<Reservation> lesReservations = facade.getReservationParPersonne((String) request.getSession().getAttribute("loginSession"));
        request.setAttribute("listeReservation", lesReservations);

        List<Float> listeMoyenne = new ArrayList<>();
        String login = (String) request.getSession().getAttribute("loginSession");
        List<Avis> avis = facade.getAvisByIdPersonne(login);

        for (Trajet unTrajet : lesTrajets) {
            listeMoyenne.add(facade.getMoyennePrix(unTrajet.getVilleDepart().getId(),unTrajet.getEtapeFinal().getVilleArrivee().getId()));
        }

        request.setAttribute("listeMoyenne", listeMoyenne);
        request.setAttribute("avis",avis);
        request.setAttribute("moyenne",facade.getNoteParPersonne(login));

        request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
    }
}
