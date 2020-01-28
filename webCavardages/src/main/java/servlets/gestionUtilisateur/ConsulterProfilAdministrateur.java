package servlets.gestionUtilisateur;

import ejbs.IFacade;
import entities.Personne;
import exceptions.DonneesInvalidesException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/mps/ConsulterProfilAdministrateur")
public class ConsulterProfilAdministrateur extends HttpServlet {
    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Personne admin = null;
        try{
            admin = facade.getPersonneByLogin((String) request.getSession().getAttribute("loginSession"));
        } catch (DonneesInvalidesException e) {
            response.sendRedirect(request.getContextPath() + "/mps/RedirigerAccueilServlet");
        }

        request.setAttribute("listeGabarit", facade.getAllGabarit());
        request.setAttribute("listeVille", facade.getAllVilles());
        request.setAttribute("admin", admin);

        request.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(request, response);
    }
}