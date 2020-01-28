package servlets.gestionReservation;

import ejbs.IFacade;
import entities.Etape;
import entities.Reservation;
import entities.Trajet;
import entities.Ville;
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


@WebServlet(urlPatterns = "/mps/liste_reservation")
public class ListeReservation extends HttpServlet {

    @EJB
    private IFacade facade;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer idTrajet = Integer.valueOf(request.getParameter("idTrajet"));

        if (request.getParameter("action") == null) {
            request.setAttribute("listeReservation", facade.getReservationsParTrajet(idTrajet));
        }

        request.getRequestDispatcher("/WEB-INF/pages/liste_reservation.jsp").forward(request, response);
    }
}
