package ejbs;

import entities.*;
import exceptions.*;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Local
public interface IFacade {

    /**
     * @param login
     * @param password
     * @throws IdentifiantsIncorrectsException Si le login n'existe pas, ou s'il existe et que le mot de passe est incorrect
     * @throws DonneesInvalidesException       Si l'un des paramétres en entrée n'est pas valable
     */
    void connexion(String login, String password) throws IdentifiantsIncorrectsException, DonneesInvalidesException;

    /**
     * @param login
     * @param password
     * @param nom
     * @param prenom
     * @param dateDeNaissance
     * @param adresse
     * @throws LoginExistantException    Si la personne tente de s'inscrire avec un login déja existant
     * @throws DonneesInvalidesException Si l'un des parametres en entrée n'est pas valide
     */
    void inscription(String login, String password, String nom, String prenom, LocalDate dateDeNaissance, String adresse) throws
            LoginExistantException, DonneesInvalidesException;

    /**
     * @param idVilleDepart
     * @param idVilleArrivee
     * @param heureDepart
     * @return La liste des trajets de la ville de départ à la ville d'arrivée à partir de l'heure de départ le meme jour
     * @throws DonneesInvalidesException
     */
    List<Trajet> rechercherTrajets(int idVilleDepart, int idVilleArrivee, LocalDateTime heureDepart) throws DonneesInvalidesException;

    void proposerTrajet(String login, int idVilleDepart, List<Integer> idVillesArrivees, LocalDateTime heureDepart, List<LocalDateTime> heuresArrivees, int nbrPlaces, List<Float> prix)
            throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException;

    /**
     * @param login
     * @param idEtape
     * @param nbrPlaces
     * @throws DonneesInvalidesException      Si les paramètres d'entrée ne sont pas valides
     * @throws IntrouvableDansLaBaseException Si la personne, ou l'étape sont introuvables en base de données
     * @throws PasAssezDePlacesException      S'il n'y a plus de places dans le trajet
     */
    void reserverTrajet(String login, int idEtape, int nbrPlaces, String message)
            throws DonneesInvalidesException, IntrouvableDansLaBaseException, PasAssezDePlacesException, TrajetDejaProposeException;

    void accepterReservation(int idReservation) throws DonneesInvalidesException, IntrouvableDansLaBaseException, PasAssezDePlacesException;

    void refuserReservation(int idReservation) throws DonneesInvalidesException, IntrouvableDansLaBaseException;

    void annulerReservation(int idReservation) throws DonneesInvalidesException, IntrouvableDansLaBaseException;

    void laisserAvis(String loginEmetteur, String loginDestinataire, int idEtape, String message, float note)
            throws DonneesInvalidesException, IntrouvableDansLaBaseException, VoyageNonTermineException, AvisDejaLaisseException;

    List<Avis> listeAvis(String login) throws DonneesInvalidesException, IntrouvableDansLaBaseException;

    void declarerVehicule(String login, String modele, int idGabarit, int nbrPlaces) throws DonneesInvalidesException, IntrouvableDansLaBaseException;

    void setEm(EntityManager em);

    Personne getPersonneByLogin(String login) throws DonneesInvalidesException;

    List<Ville> getAllVilles();

    List<Trajet> getTrajets();

    Trajet getTrajetById(int idTrajet) throws DonneesInvalidesException;

    List<Trajet> getTrajetByPersonne(String login);

    float getMoyennePrix(int villeDep, int villeArrivee);

    float getNoteParPersonne(String login);

    List<Gabarit> getAllGabarit();

    void modifierVehicule(String login, String modele, int idGabarit, int nbrPlaces) throws DonneesInvalidesException, IntrouvableDansLaBaseException;


    List<Reservation> getReservationParEtape(int idEtape);

    List<Reservation> getReservationsParTrajet(int idTrajet);

    List<Reservation> getReservationsByLogin(String login);


    Reservation getReservationParId(int id);

    float getPrixToralTrajet(int id, int villeArrivee);

    Etape findEtapeByTrajet(int idTrajet,int villeArrive);

    boolean isAdmin(String login);

    //Partie Admin
    //Gabarit
    void ajouterGabarit(String libelleGabarit) throws DonneesInvalidesException;
    void modifierGabarit(int idGabarit, String libelleGabarit) throws DonneesInvalidesException, IntrouvableDansLaBaseException;
    void supprimerGabarit(int idGabarit) throws DonneesInvalidesException, IntrouvableDansLaBaseException;
    //Ville
    void ajouterVille(String nomVille) throws DonneesInvalidesException;
    void modifierVille(int idVille, String nomVille) throws DonneesInvalidesException, IntrouvableDansLaBaseException;
    void supprimerVille(int idVille) throws DonneesInvalidesException, IntrouvableDansLaBaseException;


    boolean adejaReserver(int idTrajet,String loginDemandeur);

    List<Reservation> getReservationParPersonne(String idPersonne);

    List<Avis> getAvisByIdPersonne(String login);

    float getMoyenneDesAvis(String loginDestinataire);
}
