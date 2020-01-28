package servlets.gestionReservation;

import ejbs.IFacade;
import entities.Etape;
import entities.Trajet;
import exceptions.DonneesInvalidesException;
import exceptions.IntrouvableDansLaBaseException;
import exceptions.PasAssezDePlacesException;
import exceptions.TrajetDejaProposeException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/mps/ReserverTrajet")
public class ReserverTrajetServlet extends HttpServlet {

    @EJB
    private IFacade facade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!(request.getSession().getAttribute("loginSession") == null)) {

            //On recupere l'id du Trajet selectionné et les autres infos
            String idTrajet = request.getParameter("idTrajet");


            String nbPlacesAReserver = request.getParameter("nbrPLacesDisponibles");
            System.out.println("nbplaces" + nbPlacesAReserver);
            HttpSession session = request.getSession();
            int idVilleDest = Integer.parseInt(request.getParameter("idVilleArrive"));

            //Vérifier que la reservation est faisable avec le nb de places

            try {
               Etape etape1 = facade.findEtapeByTrajet(Integer.parseInt(idTrajet),idVilleDest);
                Trajet leTrajet = facade.getTrajetById(Integer.parseInt(idTrajet));

                facade.reserverTrajet((String)session.getAttribute("loginSession"), etape1.getId(), Integer.parseInt(nbPlacesAReserver),
                        (String)session.getAttribute("message"));
                int nbPlacesDispo = leTrajet.getNbrPLacesDisponibles();
                if (nbPlacesDispo >= Integer.parseInt(nbPlacesAReserver)) {
                    //Go
                    session.setAttribute("villeDepart", leTrajet.getVilleDepart().getNom());
                    session.setAttribute("heureDepart", leTrajet.getHeureDepart());
                    session.setAttribute("nbPassagers", nbPlacesAReserver);
                    session.setAttribute("prix", etape1.getPrix());
                    session.setAttribute("villeArrivee", etape1.getVilleArrivee().getNom());
                    session.setAttribute("heureArrivee", etape1.getHeureArrivee());
                    request.getRequestDispatcher("/WEB-INF/pages/recapitulatif_reservation.jsp").forward(request, response);

                } else {
                    //TODO: message à afficher dans le formulaire
                    throw new Exception("Il n'y a plus assez de place dans la voiture");
                }
            } catch (DonneesInvalidesException e) {
                e.printStackTrace();
            }
              catch (TrajetDejaProposeException e){
                String message = e.getMessage();
                session.setAttribute("messageErreurReservation",message);

                response.sendRedirect(request.getContextPath() + "/mps/RedirigerRechercheTrajet");

              }

            catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            request.getSession().setAttribute("messageErreur","Veuillez vous connectez ou vous inscrire svp");
            response.sendRedirect(request.getContextPath() + "/mps/RedirigerAccueilServlet");
        }

    }
}
