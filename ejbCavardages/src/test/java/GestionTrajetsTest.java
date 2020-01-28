import ejbs.Facade;
import ejbs.IFacade;
import entities.*;
import exceptions.DonneesInvalidesException;
import exceptions.NombreDePlacesIncorrectException;
import exceptions.PasDeVehiculePossedeException;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.omg.CORBA.PERSIST_STORE;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.capture;

/**
 * Created by alphalion27 on 01/11/17.
 */
public class GestionTrajetsTest {

    IFacade facade;
    EntityManager em;
    Query q;

    String login;
    int idVilleDepart;
    LocalDateTime heureDepart;
    List<Integer> idVillesArrivees;
    List<LocalDateTime> heuresArrivees;
    List<Float> prix;
    int nbrPlaces;

    @Before
    public void init() {
        facade = new Facade();
        em = EasyMock.strictMock(EntityManager.class);
        q = EasyMock.strictMock(Query.class);
        facade.setEm(em);


        login = "login";
        idVilleDepart = 1;
        idVillesArrivees = new ArrayList<>();
        idVillesArrivees.add(6);
        idVillesArrivees.add(10);
        idVillesArrivees.add(2);
        idVillesArrivees.add(27);

        heureDepart = LocalDateTime.now().plusDays(1);

        heuresArrivees = new ArrayList<>();
        heuresArrivees.add(heureDepart.plusHours(2));
        heuresArrivees.add(heureDepart.plusHours(3));
        heuresArrivees.add(heureDepart.plusHours(4));
        heuresArrivees.add(heureDepart.plusHours(5));

        nbrPlaces = 5;

        prix = new ArrayList<>();
        prix.add(new Float(2.5));
        prix.add(new Float(3));
        prix.add(new Float(3.5));
        prix.add(new Float(4.7));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // Scénario: heure de départ antérieure à la date du jour
    @Test
    public void rechercheTrajetParametreNonValide() throws DonneesInvalidesException {
        thrown.expect(DonneesInvalidesException.class);

        String s = "2015-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime d = LocalDateTime.parse(s, formatter);

        facade.rechercherTrajets(1, 2, d);
    }

    // Scénario: ville de départ = ville d'arrivée
    @Test
    public void rechercheTrajetVilleEgales() throws DonneesInvalidesException {
        thrown.expect(DonneesInvalidesException.class);

        LocalDateTime d = LocalDateTime.now().plusDays(1);
        facade.rechercherTrajets(1, 1, d);
    }

    // Scénario: Aucun trajet trouvé dans la base de données
    @Test
    public void rechercheTrajetRetourneNull() throws DonneesInvalidesException {
        thrown = ExpectedException.none();

        int idVilleDepart = 1;
        int idVilleArrivee = 2;
        LocalDateTime d = LocalDateTime.now().plusDays(1);

        String query = "SELECT t FROM Trajet t JOIN  Etape  e ON t.id = e.trajet.id JOIN Ville v ON e.villeArrivee.id = v.id WHERE e.villeArrivee.id = :villeDest AND t.villeDepart.id = :villeDep AND t.heureDepart >= :heureDep";


        EasyMock.expect(em.createQuery(query)).andReturn(q);
        EasyMock.expect(q.setParameter("villeDep", idVilleDepart)).andReturn(q);
        EasyMock.expect(q.setParameter("villeDest", idVilleArrivee)).andReturn(q);
        EasyMock.expect(q.setParameter("heureDep", d)).andReturn(q);
        EasyMock.expect(q.getResultList()).andReturn(null);
        EasyMock.replay(em);
        EasyMock.replay(q);

        List<Trajet> trajets = facade.rechercherTrajets(idVilleDepart, idVilleArrivee, d);
        EasyMock.verify();
        Assert.assertNull(trajets);
    }

    //Scénario: 3 trajets trouvés dans la base de données
    @Test
    public void rechercheTrajetRetourneResultats() throws DonneesInvalidesException {
        thrown = ExpectedException.none();

        int idVilleDepart = 1;
        int idVilleArrivee = 2;
        LocalDateTime d = LocalDateTime.now().plusDays(1);
        List<Trajet> trajets = new ArrayList<>();
        trajets.add(new Trajet());
        trajets.add(new Trajet());
        trajets.add(new Trajet());

        String query = "SELECT t FROM Trajet t JOIN  Etape  e ON t.id = e.trajet.id JOIN Ville v ON e.villeArrivee.id = v.id WHERE e.villeArrivee.id = :villeDest AND t.villeDepart.id = :villeDep AND t.heureDepart >= :heureDep";


        EasyMock.expect(em.createQuery(query)).andReturn(q);
        EasyMock.expect(q.setParameter("villeDep", idVilleDepart)).andReturn(q);
        EasyMock.expect(q.setParameter("villeDest", idVilleArrivee)).andReturn(q);
        EasyMock.expect(q.setParameter("heureDep", d)).andReturn(q);
        EasyMock.expect(q.getResultList()).andReturn(trajets);
        EasyMock.replay(em);
        EasyMock.replay(q);

        List<Trajet> trajetsRecherches = facade.rechercherTrajets(idVilleDepart, idVilleArrivee, d);
        EasyMock.verify();
        Assert.assertEquals(trajets.size(), trajetsRecherches.size());
    }

    //Scenario: Donnees invalides
    @Test
    public void proposerTrajetDonneesInvalide() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(DonneesInvalidesException.class);

        idVillesArrivees.add(-5);

        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
    }

    //Scenario: Nombre de villes différent du nombre d'heures
    @Test
    public void proposerTrajetNombreVillesDifferentNombreHeures() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(DonneesInvalidesException.class);

        idVillesArrivees.add(7);

        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
    }

    //Scenario: Nombre de villes différent du nombre de prix
    @Test
    public void proposerTrajetNombreVillesDifferentNombrePrix() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(DonneesInvalidesException.class);

        idVillesArrivees.add(7);

        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
    }

    //Scenario: La ville de départ est égale à l'une des villes d'arrivées
    @Test
    public void proposerTrajetDepartEgalArrivee() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(DonneesInvalidesException.class);

        idVillesArrivees.remove(1);
        idVillesArrivees.add(1);

        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
    }

    //Scenario: Egalité entre 2 villes d'arrivée
    @Test
    public void proposerTrajetEgaliteVillesArrivee() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(DonneesInvalidesException.class);

        idVillesArrivees.remove(1);
        idVillesArrivees.add(4);


        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
    }

    //Scenario: Une des heures d'arrivée antérieure heure de départ
    @Test
    public void proposerTrajetArriveeAnterieureDepart() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(DonneesInvalidesException.class);

        heuresArrivees.remove(1);
        heuresArrivees.add(heureDepart.minusDays(1));


        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
    }

    //Scenario: Une des heures d'arrivée(n+1) antérieure heure d'arrivee(n)
    @Test
    public void proposerTrajetArriveeNonOrdonnees() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(DonneesInvalidesException.class);

        heuresArrivees.remove(3);
        heuresArrivees.add(heuresArrivees.get(2).minusHours(1));


        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
    }

    //Scenario: La personne n'existe pas dans la base de données
    @Test
    public void proposerTrajetPersonneIntrouvable() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(DonneesInvalidesException.class);

        EasyMock.expect(em.find(Personne.class, login)).andReturn(null);
        EasyMock.replay(em);

        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
        EasyMock.verify();
    }

    //Scenario: La personne ne possede pas de vehicule
    @Test
    public void proposerTrajetPersonneSansVehicule() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(PasDeVehiculePossedeException.class);

        Personne p = new Personne();

        EasyMock.expect(em.find(Personne.class, login)).andReturn(p);
        EasyMock.replay(em);

        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
        EasyMock.verify();
    }

    //Scenario: Le nombre de places est supérieur à celui de la voiture
    @Test
    public void proposerTrajetNbrPlacesSuperieur() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown.expect(NombreDePlacesIncorrectException.class);

        Personne p = new Personne();
        Voiture v = new Voiture();
        Gabarit g = new Gabarit();
        v.setNbrPlaces(5);
        v.setGabarit(g);
        p.setVoiture(v);

        nbrPlaces = 10;

        EasyMock.expect(em.find(Personne.class, login)).andReturn(p);
        EasyMock.replay(em);

        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
        EasyMock.verify();
    }

    //Scenario: Fonctionnement correct
    @Test
    public void proposerTrajetCorrect() throws DonneesInvalidesException, PasDeVehiculePossedeException, NombreDePlacesIncorrectException {
        thrown = ExpectedException.none();

        Personne p = new Personne();
        Voiture v = new Voiture();
        Gabarit g = new Gabarit();
        v.setNbrPlaces(5);
        v.setGabarit(g);
        p.setVoiture(v);

        Capture<Etape> captureEtape = EasyMock.newCapture();
        Capture<Trajet> captureTrajet = EasyMock.newCapture();

        EasyMock.expect(em.find(Personne.class, login)).andReturn(p);
        for(int i : idVillesArrivees){
            EasyMock.expect(em.find(Ville.class, i)).andReturn(new Ville());
            em.persist(EasyMock.capture(captureEtape));
        }
        EasyMock.expect(em.find(Ville.class, idVilleDepart)).andReturn(new Ville());
        em.persist(EasyMock.capture(captureTrajet));

        EasyMock.replay(em);

        facade.proposerTrajet(login, idVilleDepart, idVillesArrivees, heureDepart, heuresArrivees, nbrPlaces, prix);
        EasyMock.verify();

    }

}
