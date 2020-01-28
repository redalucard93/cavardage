package servlets.gestionReservation;

import ejbs.IFacade;
import entities.Avis;
import entities.Trajet;
import entities.Ville;
import exceptions.DonneesInvalidesException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RechercheTrajet", urlPatterns = "/mps/RechercheTrajet")
public class RechercheTrajetServlet extends HttpServlet {

    @EJB
    private IFacade facade;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // On récupére les information de la recherche
        String villeDep = request.getParameter("villeDepart");
        String villeDest = request.getParameter("villeDestination");

        String dateTrajet = request.getParameter("dateTrajet");

        String heureTrajet = request.getParameter("heureTrajet");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm");

        HttpSession session = request.getSession();

        // On vide la partie du message d'erreurs en cas d'une retentative de recherche
        request.getSession().removeAttribute("messageErreur");

        // On récupère les id des villes
        List<Ville> villes = facade.getAllVilles();


        int idVilleDep = 0, idVilleDest = 0;

        for (Ville v : villes) {
            if (v.getNom().equals(villeDep)) {
                idVilleDep = v.getId();
            } else if (v.getNom().equals(villeDest)) {
                idVilleDest = v.getId();
            }
        }
        try {
            request.getSession().removeAttribute("messageErreur");
            LocalDateTime date = LocalDateTime.parse(dateTrajet + ":" + heureTrajet.trim(), formatter);

            List<Trajet> trajets = facade.rechercherTrajets(idVilleDep,idVilleDest,date);

           // On récupére les association des trajets et on les envoie séparement

               Map<Trajet, Float> trajetsNotes = new HashMap<>();
               Map<Trajet, Float> trajetPrix = new HashMap<>();


                for(Trajet trajet : trajets){
                    if(!facade.adejaReserver(trajet.getId(), (String) request.getSession().getAttribute("loginSession"))) {
                        trajetsNotes.put(trajet, facade.getNoteParPersonne(trajet.getConducteur().getLogin()));
                        trajetPrix.put(trajet, facade.getPrixToralTrajet(trajet.getId(), idVilleDest));
                    }
                }
               session.setAttribute("trajetsNotes",trajetsNotes);
               session.setAttribute("trajetsPrix",trajetPrix);
               session.setAttribute("villeArrive",villeDest);
               session.setAttribute("idVilleArrive",idVilleDest);



            // Au cas où le trajet n'a pas été trouvé on rajoute un message

            if (trajets.isEmpty()) {
                session.setAttribute("messageErreur", "Aucun trajet traouvé !");
            }

            response.getWriter().write(request.getContextPath() + "/mps/RedirigerResultatRecherche");


        } catch (DonneesInvalidesException | DateTimeParseException e) {
            String message = "Données invalides,résseyez ";
            session.setAttribute("messageErreur", message);
            e.printStackTrace();
            response.getWriter().write(request.getContextPath() + "/mps/RedirigerResultatRecherche");

        }


    }
}
