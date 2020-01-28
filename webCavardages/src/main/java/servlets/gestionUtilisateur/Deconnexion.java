package servlets.gestionUtilisateur;

import ejbs.IFacade;


import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/mps/deconnexion")
public class Deconnexion extends HttpServlet {
    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().setAttribute("loginSession", null);
        response.sendRedirect(request.getContextPath() + "/mps/RedirigerAccueilServlet");

    }
}
