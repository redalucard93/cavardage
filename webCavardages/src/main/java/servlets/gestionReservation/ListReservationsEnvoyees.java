package servlets.gestionReservation;

import ejbs.IFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(urlPatterns = "/mps/ConsulterReservationsDemandees")
public class ListReservationsEnvoyees extends HttpServlet {

    @EJB
    private IFacade facade;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = (String) request.getSession().getAttribute("loginSession");

            request.getSession().setAttribute("listeReservation", facade.getReservationsByLogin(login));

        response.getWriter().write(request.getContextPath()+"/mps/RedirigerResultatReservationsEnvoyees");

    }
}
