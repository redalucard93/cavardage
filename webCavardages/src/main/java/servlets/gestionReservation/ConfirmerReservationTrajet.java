package servlets.gestionReservation;

import ejbs.IFacade;
import exceptions.DonneesInvalidesException;
import exceptions.IntrouvableDansLaBaseException;
import exceptions.PasAssezDePlacesException;
import exceptions.TrajetDejaProposeException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/mps/ConfirmerReservation")
public class ConfirmerReservationTrajet extends HttpServlet {
    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEtape = request.getParameter("idEtape");
        String nbPlacesAReserver = request.getParameter("nbPlacesAReserver");

        //On recupere le login de l'utilisateur et l'id du Trajet
        String login = (String) request.getSession().getAttribute("loginSession");
        int numEtape = Integer.parseInt(idEtape);
        int nbPlaces = Integer.parseInt(nbPlacesAReserver);
        String msg = "Merci pour de valider ma reservation"; //TODO: pas encore gérer [request.getParameter("msg")]

        try {
            //Redirection vers la page de recapitulatif de reservation
            facade.reserverTrajet(login, numEtape, nbPlaces, msg);
            //Redirection
            request.getRequestDispatcher("/WEB-INF/pages/recapitulatif_reservation.jsp").forward(request, response);
        } catch (DonneesInvalidesException e) {
            e.printStackTrace();
        } catch (PasAssezDePlacesException e) {
            e.printStackTrace();
        } catch (IntrouvableDansLaBaseException e) {
            e.printStackTrace();
        } catch (TrajetDejaProposeException e) {
            e.printStackTrace();
        }
    }
}