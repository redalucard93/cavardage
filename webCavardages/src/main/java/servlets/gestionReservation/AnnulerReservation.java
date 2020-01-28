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
@WebServlet(urlPatterns = "/mps/AnnulerReservation")
public class AnnulerReservation extends HttpServlet {

    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idReservation = request.getParameter("idReservation");
        try {
            facade.annulerReservation(Integer.parseInt(idReservation));
        } catch (DonneesInvalidesException e) {
            e.printStackTrace();
        } catch (IntrouvableDansLaBaseException e) {
            e.printStackTrace();
        }
    }
}
