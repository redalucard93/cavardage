package ejbs;

import entities.*;
import exceptions.*;
import utils.ArgsValidation;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Stateless(name = "FacadeEJB")
public class Facade implements IFacade {

    @PersistenceContext(unitName = "monUnite")
    private EntityManager em;

    @Override
    public void connexion(String login, String password) throws IdentifiantsIncorrectsException, DonneesInvalidesException {

        ArgsValidation.verifierChaine(login);
        ArgsValidation.verifierChaine(password);

        // On récupère la personne depuis la base de données
        Personne p = em.find(Personne.class, login);

        // Si l'identifiant est incorrect, la personne est égale à null

        if (p == null || !(p.getPassword().equals(password))) {
            throw new IdentifiantsIncorrectsException();

        }
    }

    @Override
    public void inscription(String login, String password, String nom, String prenom, LocalDate dateDeNaissance, String adresse)
            throws LoginExistantException, DonneesInvalidesException {


        ArgsValidation.verifierChaine(login);
        ArgsValidation.verifierChaine(password);
        ArgsValidation.verifierChaine(nom);
        ArgsValidation.verifierChaine(prenom);
        ArgsValidation.verifierDateDeNaissance(dateDeNaissance);
        ArgsValidation.verifierChaine(adresse);

        if (em.find(Personne.class, login) != null) {


            throw new LoginExistantException("Login déja pris");
        } else {
            Personne p = new Personne(login, password, nom, prenom, dateDeNaissance, adresse);
            em.persist(p);
        }
    }

    @Override
    public List<Trajet> rechercherTrajets(int idVilleDepart, int idVilleArrivee, LocalDateTime heureDepart) throws DonneesInvalidesException {

        ArgsValidation.verifierEntier(idVilleDepart);
        ArgsValidation.verifierEntier(idVilleArrivee);
        ArgsValidation.verifierHeureDepart(heureDepart);

        if (idVilleDepart == idVilleArrivee) {
            throw new DonneesInvalidesException();
        }

        String query = "SELECT t FROM Trajet t JOIN  Etape  e ON t.id = e.trajet.id JOIN Ville v ON e.villeArrivee.id = v.id WHERE e.villeArrivee.id = :villeDest AND t.villeDepart.id = :villeDep AND t.heureDepart >= :heureDep";

        List<Trajet> trajets = (ArrayList<Trajet>) em.createQuery(query)
                .setParameter("villeDep", idVilleDepart)
                .setParameter("villeDest", idVilleArrivee)
                .setParameter("heureDep", heureDepart).getResultList();


        return trajets;
    }

    @Override
    public void proposerTrajet(String login, int idVilleDepart, List<Integer> idVillesArrivees, LocalDateTime heureDepart, List<LocalDateTime> heuresArrivees, int nbrPlaces, List<Float> prix)
            throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        /*Tests de vérification*/
        ArgsValidation.verifierChaine(login);
        ArgsValidation.verifierEntier(idVilleDepart);
        ArgsValidation.verifierListEntiers(idVillesArrivees);
        ArgsValidation.verifierHeureDepart(heureDepart);
        ArgsValidation.verifierListHeures(heuresArrivees);
        ArgsValidation.verifierEntier(nbrPlaces);
        ArgsValidation.verifierListFloat(prix);

        /* Nombre de villes != du nombre d'heures */
        if (idVillesArrivees.size() != heuresArrivees.size()) {
            throw new DonneesInvalidesException();
        }

        /*Nombre de villes != du nombre de prix */
        if (idVillesArrivees.size() != prix.size()) {
            throw new DonneesInvalidesException();
        }


        //VilleDepart = à une des villes d’arrivées
        if (idVillesArrivees.contains(idVilleDepart)) {
            throw new DonneesInvalidesException();
        }

        //égalité entre 2 villes d’arrivées
        ArgsValidation.verifierDoublonsEntiers(idVillesArrivees);

        //égalité entre 2 heures d'arrivées
        ArgsValidation.verifierDoublonsLocalDateTime(heuresArrivees);

        /*Une des HeureArrivee ≤ heureDepart et heureArrivee(n+1) ≤ heureArrivee(n)*/
        for (LocalDateTime h : heuresArrivees) {
            LocalDateTime before = LocalDateTime.MIN; //The minimum supported LocalDateTime, '-999999999-01-01T00:00:00'.
            if (h.isBefore(heureDepart) || h.isBefore(before)) {
                //exception
                throw new DonneesInvalidesException();
            } else {
                before = h;
            }
        }

        /*La personne est introuvable*/
        Personne conducteur = em.find(Personne.class, login);
        if (conducteur == null) {
            throw new DonneesInvalidesException();
        }
        /*La personne ne possede pas de vehicule*/
        if (conducteur.getVoiture() == null) {
            //exception
            throw new PasDeVehiculePossedeException();
        }

        /*Le nombre de places proposées est supérieur au nombre de places du véhicule possédé*/
        if (conducteur.getVoiture().getNbrPlaces() < nbrPlaces) {
            //exception
            throw new NombreDePlacesIncorrectException();
        }

        List<Etape> listEtape = new ArrayList<Etape>();

        for (int idVille : idVillesArrivees) {
            int i = idVillesArrivees.indexOf(idVille);
            //Creation nouvelle etape
            Ville villeArrivee = em.find(Ville.class, idVille);
            Etape nouvelleEtape = new Etape(prix.get(i), heuresArrivees.get(i), villeArrivee);
            //Ajout de l'etape
            em.persist(nouvelleEtape);
            //Ajout dans la liste d'etape qui va servir à la création du Trajet
            listEtape.add(nouvelleEtape);
        }



        /*Données correctes*/
        Ville villeDepart = em.find(Ville.class, idVilleDepart);
        Trajet nouveauTrajet = new Trajet(heureDepart, nbrPlaces, conducteur, villeDepart, listEtape);

        for (Etape uneEtape : listEtape) {
            uneEtape.setTrajet(nouveauTrajet);
        }

        //Ajout du trajet
        em.persist(nouveauTrajet);
    }

    @Override
    public void reserverTrajet(String login, int idEtape, int nbrPlaces, String message)
            throws DonneesInvalidesException, IntrouvableDansLaBaseException, PasAssezDePlacesException, TrajetDejaProposeException {
        ArgsValidation.verifierChaine(login);
        ArgsValidation.verifierEntier(idEtape);
        ArgsValidation.verifierEntier(nbrPlaces);

        Personne p = em.find(Personne.class, login);
        Etape etape = em.find(Etape.class, idEtape);

        if (p == null || etape == null) {
            throw new IntrouvableDansLaBaseException();
        }

        String query = new String("FROM Trajet t WHERE ?1 MEMBER OF t.etapes");
        Trajet trajet = (Trajet) em.createQuery(query).setParameter(1, etape).getSingleResult();

        if (trajet == null) {
            throw new IntrouvableDansLaBaseException();
        }

        if (trajet.getNbrPLacesDisponibles() < nbrPlaces) {
            throw new PasAssezDePlacesException();
        }
        if(trajet.getConducteur().getLogin().equals(p.getLogin())){
            throw new TrajetDejaProposeException();
        }


        Reservation r = new Reservation(message, nbrPlaces, p, etape);
        em.persist(r);
    }

    /*TODO: rajouter des etats de reservation dans une prochaine version?*/
    @Override
    public void accepterReservation(int idReservation) throws DonneesInvalidesException, IntrouvableDansLaBaseException, PasAssezDePlacesException {

        /*Tests de vérification: variable en entrée non valide*/
        ArgsValidation.verifierEntier(idReservation);

        Reservation reservation = em.find(Reservation.class, idReservation);

        /*Reservation introuvable en base de données*/
        if (reservation == null) {
            throw new IntrouvableDansLaBaseException();
        }

        /*Plus de places dans le trajet et nombre de places restantes = 0*/
        String query = "SELECT t FROM Trajet t WHERE ?1 MEMBER OF t.etapes";
        Trajet t = (Trajet) em.createQuery(query).
                setParameter(1, reservation.getEtape()).getSingleResult();

        if (t.getNbrPLacesDisponibles() < reservation.getNbrPlaces()) {
            throw new PasAssezDePlacesException();
        }

        //TODO: si un voyageur veut rajouter une personne en plus dans le futur? (modifier/annuler reservation)
        //TODO: faire une fonction qui decremente le nb de places?
        /*Données correctes*/

        //Ajout de la personne à la liste des voyageurs
        reservation.setEtat(Reservation.Etat.ACCEPTEE);
        reservation.getEtape().getVoyageurs().add(reservation.getDemandeur());


        //Decrementation du nb de places dispo
        t.setNbrPLacesDisponibles(t.getNbrPLacesDisponibles() - reservation.getNbrPlaces());

        //on veut la liste des reservations en cours pour le trajet qui correspond à la reservation

        /*
        String query2 = "SELECT r, t FROM Reservation r , Trajet t WHERE t = :trajet AND r.etape MEMBER OF t.etapes " +
                "AND r.nbrPlaces > ?2";
        List<Reservation> listeReservation = (List<Reservation>) em.createQuery(query2)
                .setParameter("trajet", t)
                .setParameter(2, t.getNbrPLacesDisponibles()).getResultList();

        em.remove(listeReservation);
        */
    }

    @Override
    public void refuserReservation(int idReservation) throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        ArgsValidation.verifierEntier(idReservation);

        Reservation r = em.find(Reservation.class, idReservation);
        if (r == null) {
            throw new IntrouvableDansLaBaseException();
        }

        r.setEtat(Reservation.Etat.REFUSEE);
    }

    @Override
    public void annulerReservation(int idReservation) throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        ArgsValidation.verifierEntier(idReservation);

        Reservation r = em.find(Reservation.class, idReservation);
        if (r == null) {
            throw new IntrouvableDansLaBaseException();
        }

        em.remove(r);

    }

    @Override
    public void laisserAvis(String loginEmetteur, String loginDestinataire, int idEtape, String message, float note)
            throws DonneesInvalidesException,NoResultException, IntrouvableDansLaBaseException, VoyageNonTermineException, AvisDejaLaisseException {
       try {
           Etape etape = em.find(Etape.class,idEtape);
           Avis avis = (Avis) em.createQuery("SELECT a FROM Avis a WHERE a.etape.trajet.id = :trajet AND a.emetteur.login = :loginEmetteur AND a.destinataire.login = :loginDestinataire")
                   .setParameter("loginEmetteur", loginEmetteur)
                   .setParameter("loginDestinataire", loginDestinataire)
                   .setParameter("trajet",etape.getTrajet().getId())
                   .getSingleResult();
           throw new AvisDejaLaisseException();
       }
       catch (NoResultException e) {
        /*variables en entrée non valides*/
        ArgsValidation.verifierChaine(loginEmetteur);
        ArgsValidation.verifierChaine(loginDestinataire);
        ArgsValidation.verifierEntier(idEtape);
        ArgsValidation.verifierChaine(message);
        ArgsValidation.verifierFloat(note);

         /*Note > note_max ou Note < 0*/
        if (note > Avis.NOTE_MAX || note < 0) {
            throw new DonneesInvalidesException();
        }

        /*Emetteur ou Destinataire ou Trajet introuvabe en base de données*/
        Personne emetteur = em.find(Personne.class, loginEmetteur);
        Personne destinataire = em.find(Personne.class, loginDestinataire);
        Etape etape = em.find(Etape.class, idEtape);

        if (emetteur == null || destinataire == null || etape == null) {
            throw new IntrouvableDansLaBaseException();
        }

        ArgsValidation.verifierHeureArrivee(etape.getHeureArrivee());

        Avis nouvelAvis = new Avis(note, message, emetteur, destinataire, etape);
        em.persist(nouvelAvis);
    }
    }

    @Override
    public List<Avis> listeAvis(String login) throws DonneesInvalidesException, IntrouvableDansLaBaseException {

        /*variables en entrée non valides*/
        ArgsValidation.verifierChaine(login);

        /*Personne introuvable en base de données*/
        Personne p = em.find(Personne.class, login);
        if (p == null) {
            throw new IntrouvableDansLaBaseException();
        }

        /*Aucun ou plusieurs avis dans la base de données*/
        String query = "SELECT a FROM Avis a WHERE a.destinataire=?1";
        List<Avis> listeAvis = (List<Avis>) em.createQuery(query).setParameter(1, p).getResultList();

        return listeAvis;
    }

    @Override
    public void declarerVehicule(String login, String modele, int idGabarit, int nbrPlaces)
            throws DonneesInvalidesException, IntrouvableDansLaBaseException {

        /* Validation */
        ArgsValidation.verifierChaine(login);
        ArgsValidation.verifierChaine(modele);
        ArgsValidation.verifierEntier(idGabarit);
        ArgsValidation.verifierEntier(nbrPlaces);

        /* On s'assure que les elements se trouvent bien dans la base de données */
        Personne p = em.find(Personne.class, login);
        Gabarit g = em.find(Gabarit.class, idGabarit);

        if (p == null || g == null) {
            throw new IntrouvableDansLaBaseException();
        }

        Voiture v = new Voiture(modele, g, nbrPlaces);
        em.persist(v);

        p.setVoiture(v);
    }


    public void setEm(EntityManager entityManager) {

        System.out.println(entityManager);
        this.em = entityManager;
    }

    @Override
    public Personne getPersonneByLogin(String login) throws DonneesInvalidesException {
        ArgsValidation.verifierChaine(login);
        return em.find(Personne.class, login);
    }

    @Override
    public List<Ville> getAllVilles() {
        String q = new String("Select v FROM Ville v");
        return (List<Ville>) em.createQuery(q).getResultList();
    }

    @Override
    public List<Trajet> getTrajets() {
        String q = new String("Select t FROM Trajet t");
        return (List<Trajet>) em.createQuery(q).getResultList();
    }

    @Override
    public Trajet getTrajetById(int idTrajet) throws DonneesInvalidesException {
        ArgsValidation.verifierEntier(idTrajet);
        return em.find(Trajet.class, idTrajet);
    }

    @Override
    public List<Trajet> getTrajetByPersonne(String login) {
        String query = "SELECT t FROM Trajet t WHERE t.conducteur.login = ?1";

        List<Trajet> listeTrajet = (List<Trajet>) em.createQuery(query).setParameter(1, login).getResultList();
        return listeTrajet;
    }


    @Override
    public float getMoyennePrix(int villeDep, int villeArrive) {
        float total = 0;
        int compteurTrajet = 0;

        //Récupération de tous les trajets
        List<Trajet> trajets = this.getTrajets();

        for (Trajet t : trajets) {

            //Tester si la ville de départ correspond
            if (t.getVilleDepart().getId() == villeDep) {

                // Tester si la ville d'arrivée correspond
                for (Etape e : t.getEtapes()) {
                    if (e.getVilleArrivee().getId() == villeArrive) {
                        //Calcul du prix total
                        total = +e.getPrix();
                        compteurTrajet++;
                    }
                }
            }
        }
        //Retourner le résultat en arrondi
        return total / compteurTrajet;
    }

    @Override
    public float getNoteParPersonne(String login) {
        float total = 0;
        Personne personne = em.find(Personne.class, login);

        String query = "SELECT  a FROM Avis a , Personne p WHERE p = :personne AND p = a.destinataire ";
        List<Avis> listeAvis = (List<Avis>) em.createQuery(query)
                .setParameter("personne", personne).getResultList();
        for (Avis a : listeAvis) {
            total = +a.getNote();
        }
        return total / Avis.NOTE_MAX;
    }


    public List<Gabarit> getAllGabarit() {
        String q = new String("Select g FROM Gabarit g");
        return (List<Gabarit>) em.createQuery(q).getResultList();
    }

    @Override
    public void modifierVehicule(String login, String modele, int idGabarit, int nbrPlaces) throws DonneesInvalidesException, IntrouvableDansLaBaseException {

        /* Validation */
        ArgsValidation.verifierChaine(login);
        ArgsValidation.verifierChaine(modele);
        ArgsValidation.verifierEntier(idGabarit);
        ArgsValidation.verifierEntier(nbrPlaces);


        Personne p = getPersonneByLogin(login);
        Gabarit g = em.find(Gabarit.class, idGabarit);

        if (p == null || g == null) {
            throw new IntrouvableDansLaBaseException();
        }

        p.getVoiture().setGabarit(g);
        p.getVoiture().setModele(modele);
        p.getVoiture().setNbrPlaces(nbrPlaces);
    }

    @Override
    public float getPrixToralTrajet(int id, int villeArrivee) {
        Trajet trajet = em.find(Trajet.class, id);
        float total = 0f;
        for (Etape e : trajet.getEtapes()) {
            total += e.getPrix();
            if (e.getVilleArrivee().getId() == villeArrivee) break;
        }
        return total;
    }

    @Override
    public List<Reservation> getReservationParEtape(int idEtape) {

        String query = "SELECT r FROM Reservation r WHERE r.etape.id = ?1";
        List<Reservation> listeReservation = (List<Reservation>) em.createQuery(query).setParameter(1, idEtape).getResultList();

        return listeReservation;
    }

    @Override
    public List<Reservation> getReservationsParTrajet(int idTrajet) {
        Trajet t = null;

        try {
            t = this.getTrajetById(idTrajet);
        } catch (DonneesInvalidesException e) {
            e.printStackTrace();
        }

        List<Reservation> reservations = new ArrayList<Reservation>();

        for (Etape uneEtape : t.getEtapes()) {
            reservations.addAll(this.getReservationParEtape(uneEtape.getId()));
        }

        return reservations;
    }

    @Override
    public Reservation getReservationParId(int id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> getReservationsByLogin(String login) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.demandeur.login = :login");
        List<Reservation> reservations = query.setParameter("login", login).getResultList();

        return reservations;
    }


    public boolean isAdmin(String login) {
        return em.find(Personne.class, login).isAdmin();
    }

    @Override
    public void ajouterGabarit(String libelleGabarit) throws DonneesInvalidesException {
        /* Validation */
        ArgsValidation.verifierChaine(libelleGabarit);

        Gabarit g = new Gabarit(libelleGabarit);
        em.persist(g);
    }

    @Override
    public void modifierGabarit(int idGabarit, String libelleGabarit) throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        /* Validation */
        ArgsValidation.verifierEntier(idGabarit);
        ArgsValidation.verifierChaine(libelleGabarit);

        /* On s'assure que les elements se trouvent bien dans la base de données */
        Gabarit g = em.find(Gabarit.class, idGabarit);

        if (g == null) {
            throw new IntrouvableDansLaBaseException();
        } else {
            g.setLibelle(libelleGabarit);
            em.persist(g);
        }
    }

    @Override

    public Etape findEtapeByTrajet(int idTrajet, int villeArrive) {
        String query = "SELECT  e FROM Etape e  WHERE e.trajet.id = :trajet AND e.villeArrivee.id = :villeArrive ";

        Etape etape = (Etape) em.createQuery(query)
                .setParameter("trajet", idTrajet)
                .setParameter("villeArrive", villeArrive).getSingleResult();

        return etape;
    }

    public void supprimerGabarit(int idGabarit) throws DonneesInvalidesException, IntrouvableDansLaBaseException {
         /* Validation */
        ArgsValidation.verifierEntier(idGabarit);

        /* On s'assure que les elements se trouvent bien dans la base de données */
        Gabarit gabarit = em.find(Gabarit.class, idGabarit);

        if (gabarit == null) {
            throw new IntrouvableDansLaBaseException();
        } else {
            //TODO: changer le cascade=CascadeType.[REMOVE] pour pouvoir modifier les vehicule de ce gabarit
            Query query = em.createQuery("DELETE FROM Gabarit g WHERE g.id like :gabarit");
            query.setParameter("gabarit", idGabarit);
            query.executeUpdate();
        }
    }

    @Override
    public void ajouterVille(String nomVille) throws DonneesInvalidesException {
        /* Validation */
        ArgsValidation.verifierChaine(nomVille);

        Ville v = new Ville(nomVille);
        em.persist(v);
    }

    @Override
    public void modifierVille(int idVille, String nomVille) throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        /* Validation */
        ArgsValidation.verifierEntier(idVille);
        ArgsValidation.verifierChaine(nomVille);

        /* On s'assure que les elements se trouvent bien dans la base de données */
        Ville v = em.find(Ville.class, idVille);

        if (v == null) {
            throw new IntrouvableDansLaBaseException();
        } else {
            v.setNom(nomVille);
            em.persist(v);
        }
    }

    @Override
    public void supprimerVille(int idVille) throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        /* Validation */
        ArgsValidation.verifierEntier(idVille);

        /* On s'assure que les elements se trouvent bien dans la base de données */
        Ville ville = em.find(Ville.class, idVille);

        if (ville == null) {
            throw new IntrouvableDansLaBaseException();
        } else {
            //TODO: changer le cascade=CascadeType.[REMOVE] pour pouvoir modifier les trajets contenant les villes
            Query query = em.createQuery("DELETE FROM Ville v WHERE v.id like :ville");
            query.setParameter("ville", idVille);
            query.executeUpdate();
        }
    }

    @Override

    public boolean adejaReserver(int idTrajet, String loginDemandeur) {

        Trajet trajet = em.find(Trajet.class, idTrajet);
        String query = "SELECT r FROM Reservation r WHERE r.etape.trajet.id = :trajet " +
                "AND r.demandeur.login = :loginDemandeur ";
        try {
            Reservation reservation = (Reservation) em.createQuery(query).setParameter("trajet", idTrajet)
                    .setParameter("loginDemandeur", loginDemandeur).getSingleResult();

            return true;
        } catch (NoResultException e) {
            return false;
        }

    }
    public List<Reservation> getReservationParPersonne(String idPersonne) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.demandeur.login = :idPersonne");
        query.setParameter("idPersonne", idPersonne);
        List<Reservation> listeReservation = query.getResultList();

        return listeReservation;

    }

    @Override
    public List<Avis> getAvisByIdPersonne(String login) {
        Personne p = em.find(Personne.class, login);
        List<Avis> avis = (List<Avis>) em.createQuery("SELECT a FROM Avis a where a.destinataire.login = :p")
                           .setParameter("p",login).getResultList();

        System.out.println(avis);
        return avis;
    }

    @Override
    public float getMoyenneDesAvis(String loginDestinataire) {
       String s = "SELECT avg(a.note) FROM Avis a WHERE a.destinataire.login = :login";
       Float moyenne = (Float) em.createQuery(s).setParameter("login",loginDestinataire).getSingleResult();

       return moyenne;

    }


}
