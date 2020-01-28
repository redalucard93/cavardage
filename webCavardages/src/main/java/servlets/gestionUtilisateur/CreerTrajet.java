package servlets.gestionUtilisateur;

import ejbs.IFacade;
import entities.Personne;
import exceptions.DonneesInvalidesException;
import exceptions.NombreDePlacesIncorrectException;
import exceptions.PasDeVehiculePossedeException;


import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/mps/creer_trajet")
public class CreerTrajet extends HttpServlet {
    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().getAttribute("loginSession");

        Personne p = null;

        try {
            p = facade.getPersonneByLogin((String) request.getSession().getAttribute("loginSession"));
        } catch (DonneesInvalidesException e) {
            response.sendRedirect(request.getContextPath() + "/mps/RedirigerAccueilServlet");
        }

        if (request.getParameter("action") != null) {

            /*
             Création des listes ville, horaire et prix
             */
            List<Integer> listeVille = new ArrayList<>();
            listeVille.add(Integer.valueOf(request.getParameter("ville_final")));

            String string = request.getParameter("horaire_final");
            String[] partsFinal = string.split("/");

            List<LocalDateTime> listeHoraire = new ArrayList<>();
            listeHoraire.add(LocalDateTime.of(
                    Integer.valueOf(partsFinal[2]),
                    Integer.valueOf(partsFinal[1]),
                    Integer.valueOf(partsFinal[0]),
                    Integer.valueOf(request.getParameter("heure_final")),
                    Integer.valueOf(request.getParameter("minute_final"))
            ));

            List<Float> listePrix = new ArrayList<>();
            listePrix.add(Float.valueOf(request.getParameter("prix_final")));

            String string2 = request.getParameter("horaire_depart");
            String[] parts = string2.split("/");

            int compteur = 1;

            // Ajout des horaires des villes intermédiaires
            while (request.getParameter("date_ville_inte_" + compteur) != null) {
                listePrix.add(Float.valueOf(request.getParameter("prix_inte_" + compteur)));
                listeVille.add(Integer.valueOf(request.getParameter("ville_inte_" + compteur)));

                String string3 = request.getParameter("date_ville_inte_" + compteur);
                String[] dateInte = string3.split("/");

                listeHoraire.add(LocalDateTime.of(
                        Integer.valueOf(dateInte[2]),
                        Integer.valueOf(dateInte[1]),
                        Integer.valueOf(dateInte[0]),
                        Integer.valueOf(request.getParameter("heure_inte_" + compteur)),
                        Integer.valueOf(request.getParameter("minute_inte_" + compteur))
                ));

                compteur++;
            }


            try {
                facade.proposerTrajet(p.getLogin(), Integer.valueOf(request.getParameter("villeDepart")), listeVille,
                        LocalDateTime.of(Integer.valueOf(parts[2]),
                                Integer.valueOf(parts[1]),
                                Integer.valueOf(parts[0]),
                                Integer.valueOf(request.getParameter("heure_depart")),
                                Integer.valueOf(request.getParameter("minute_depart"))),
                        listeHoraire, Integer.valueOf(request.getParameter("nbPlaces")), listePrix);
            } catch (DonneesInvalidesException e) {
                request.setAttribute("messageErreur", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages/erreur_creation_trajet.jsp").forward(request, response);
            } catch (PasDeVehiculePossedeException e) {
                request.setAttribute("messageErreur", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages/erreur_creation_trajet.jsp").forward(request, response);
            } catch (NombreDePlacesIncorrectException e) {
                request.setAttribute("messageErreur", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages/erreur_creation_trajet.jsp").forward(request, response);
            }

            response.sendRedirect(request.getContextPath() + "/mps/ConsulterProfilUtilisateur");

        } else {

            request.setAttribute("listeVille", facade.getAllVilles());

            request.getRequestDispatcher("/WEB-INF/pages/creer_trajet.jsp").forward(request, response);
        }

    }
}
