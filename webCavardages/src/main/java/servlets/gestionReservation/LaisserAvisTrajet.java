package servlets.gestionReservation;

import ejbs.IFacade;
import entities.Reservation;
import exceptions.AvisDejaLaisseException;
import exceptions.DonneesInvalidesException;
import exceptions.IntrouvableDansLaBaseException;
import exceptions.VoyageNonTermineException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/mps/LaisserAvis")
public class LaisserAvisTrajet extends HttpServlet{
    @EJB
    private IFacade facade;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idReservation = request.getParameter("idReservation");

        Reservation reservation = facade.getReservationParId(Integer.parseInt(idReservation));

        String emeteur = (java.lang.String) request.getSession().getAttribute("loginSession");
        String destinataire = reservation.getEtape().getTrajet().getConducteur().getLogin();
        int idEtape = reservation.getEtape().getId();
        String message = request.getParameter("message");
        float note = Float.parseFloat(request.getParameter("note"));

        try {
            try {
                facade.laisserAvis(emeteur,destinataire,idEtape,message,note);
            } catch (AvisDejaLaisseException e) {
                String messageAvisDejaLaisse = e.getMessage();
                request.getSession().setAttribute("messageAvisDejaLaisse",messageAvisDejaLaisse);
                request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
                request.getSession().removeAttribute("messageAvisDejaLaisse");
            }
        } catch (DonneesInvalidesException e) {
            e.printStackTrace();
        } catch (IntrouvableDansLaBaseException e) {
            e.printStackTrace();
        } catch (VoyageNonTermineException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
    }
}
