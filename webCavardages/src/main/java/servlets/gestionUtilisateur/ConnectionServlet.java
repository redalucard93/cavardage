package servlets.gestionUtilisateur;

import ejbs.IFacade;
import exceptions.DonneesInvalidesException;
import exceptions.IdentifiantsIncorrectsException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/mps/ConnectionServlet")
public class ConnectionServlet extends HttpServlet {

    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        //On test dans si l'utilisateur existe via la facade
        try {
            facade.connexion(login, password);
            request.getSession().setAttribute("loginSession", login);
            boolean isAdmin = facade.isAdmin(login);
            if (isAdmin){
                request.getSession().setAttribute("isAdmin", isAdmin);
            }
            request.getSession().removeAttribute("messageErreur");

            response.getWriter().write(request.getContextPath()+"/mps/ConsulterProfilUtilisateur");
        } catch (IdentifiantsIncorrectsException e) {

        } catch (DonneesInvalidesException e) {
            response.sendRedirect(request.getContextPath() + "/mps/RedirigerAccueilServlet");
        }
    }
}
