package servlets.gestionUtilisateur;

import ejbs.IFacade;
import exceptions.DonneesInvalidesException;
import exceptions.LoginExistantException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(urlPatterns = "/mps/InscriptionServlet")
public class InscriptionServlet extends HttpServlet {
    @EJB
    private IFacade facade;



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String lastName = request.getParameter("lastName");

        String firstName = request.getParameter("firstName");

        String login = request.getParameter("login");

        String password = request.getParameter("password");

        String dateNaissance = request.getParameter("date");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {

            facade.inscription(login, password,lastName , firstName, LocalDate.parse(dateNaissance,formatter), "test");
            request.getSession().setAttribute("loginSession", login);

            response.getWriter().write(request.getContextPath()+"/mps/ConsulterProfilUtilisateur");

            request.getSession().removeAttribute("messageErreur");


        }

        catch (DonneesInvalidesException | LoginExistantException | DateTimeException e) {
            String message = "";
            if(e instanceof LoginExistantException){
                message = "Login déjà existant";
            }
            else if(e instanceof DonneesInvalidesException){
                message = "Données invalides, résseyez l'inscription";
            }
            else if(e instanceof DateTimeException ){
                message = "Format de la date incorrect";
            }
            e.printStackTrace();
            request.getSession().setAttribute("messageErreur",message);
            response.getWriter().write(request.getContextPath()+"/mps/RedirigerAccueilServlet");

        }
    }
}
