package servlets.gestionReservation;

import ejbs.IFacade;

import exceptions.DonneesInvalidesException;
import exceptions.IntrouvableDansLaBaseException;
import exceptions.PasAssezDePlacesException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(urlPatterns = "/mps/gestion_reservation")
public class GestionReservation extends HttpServlet {

    @EJB
    private IFacade facade;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idResa = Integer.parseInt(request.getParameter("idReservation"));

        if (request.getParameter("action").equals("accepte")) {

            try {
                facade.accepterReservation(idResa);
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            } catch (IntrouvableDansLaBaseException e) {
                e.printStackTrace();
            } catch (PasAssezDePlacesException e) {
                e.printStackTrace();
            }

        } else if (request.getParameter("action").equals("refuse")) {

            try {
                facade.refuserReservation(idResa);
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            } catch (IntrouvableDansLaBaseException e) {
                e.printStackTrace();
            }

        }

        response.sendRedirect(request.getContextPath() + "/mps/liste_reservation?idTrajet=" + facade.getReservationParId(idResa).getEtape().getTrajet().getId());

    }
}
