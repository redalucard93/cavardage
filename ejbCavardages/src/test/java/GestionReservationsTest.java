import com.sun.org.apache.regexp.internal.RE;
import ejbs.Facade;
import ejbs.IFacade;
import entities.Etape;
import entities.Personne;
import entities.Reservation;
import entities.Trajet;
import exceptions.DonneesInvalidesException;
import exceptions.IntrouvableDansLaBaseException;
import exceptions.PasAssezDePlacesException;
import exceptions.TrajetDejaProposeException;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.newCapture;

/**
 * Created by alphalion27 on 01/11/17.
 */
public class GestionReservationsTest {

    IFacade facade;
    EntityManager em;
    Query q;

    String login;
    int idEtape;
    int nbrPlaces;
    String message;
    int idReservation;

    @Before
    public void init() {
        facade = new Facade();
        em = EasyMock.strictMock(EntityManager.class);
        q = EasyMock.strictMock(Query.class);
        facade.setEm(em);

        login = "login";
        idEtape = 5;
        nbrPlaces = 2;
        message = "";
        idReservation = 3;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //Scénario: Une des variables en entrée non valides
    @Test
    public void reserverTrajetVariablesNonValides() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException, TrajetDejaProposeException {
        thrown.expect(DonneesInvalidesException.class);

        int idEtape = -5;
        int nbrPlaces = 2;
        String message = "";

        facade.reserverTrajet(login, idEtape, nbrPlaces, message);
    }

    //Scénario: Personne introuvable dans la base
    @Test
    public void reserverTrajetPersonneIntrouvable() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException, TrajetDejaProposeException {
        thrown.expect(IntrouvableDansLaBaseException.class);

        EasyMock.expect(em.find(Personne.class, login)).andReturn(null);
        EasyMock.expect(em.find(Etape.class, idEtape)).andReturn(new Etape());
        EasyMock.replay(em);

        facade.reserverTrajet(login, idEtape, nbrPlaces, message);

        EasyMock.verify();
    }

    //Scénario: Etape introuvable dans la base
    @Test
    public void reserverTrajetEtapeIntrouvable() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException, TrajetDejaProposeException {
        thrown.expect(IntrouvableDansLaBaseException.class);


        EasyMock.expect(em.find(Personne.class, login)).andReturn(new Personne());
        EasyMock.expect(em.find(Etape.class, idEtape)).andReturn(null);
        EasyMock.replay(em);

        facade.reserverTrajet(login, idEtape, nbrPlaces, message);

        EasyMock.verify();
    }

    //Scénario: Trajet introuvable dans la base
    @Test
    public void reserverTrajetTrajetIntrouvable() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException, TrajetDejaProposeException {
        thrown.expect(IntrouvableDansLaBaseException.class);

        Etape e = new Etape();
        String query = new String("FROM Trajet t WHERE ?1 MEMBER OF t.etapes");


        EasyMock.expect(em.find(Personne.class, login)).andReturn(new Personne());
        EasyMock.expect(em.find(Etape.class, idEtape)).andReturn(e);
        EasyMock.expect(em.createQuery(query)).andReturn(q);
        EasyMock.expect(q.setParameter(1, e)).andReturn(q);
        EasyMock.expect(q.getSingleResult()).andReturn(null);

        EasyMock.replay(em);
        EasyMock.replay(q);

        facade.reserverTrajet(login, idEtape, nbrPlaces, message);

        EasyMock.verify();

    }

    //Scénario: Pas assez de places disponibles dans le trajet
    @Test
    public void reserverTrajetPasAssezDePlaces() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException, TrajetDejaProposeException {
        thrown.expect(PasAssezDePlacesException.class);

        int nbrPlaces = 100;

        Etape e = new Etape();
        Trajet t = new Trajet();
        t.setNbrPLacesDisponibles(3);

        String query = new String("FROM Trajet t WHERE ?1 MEMBER OF t.etapes");


        EasyMock.expect(em.find(Personne.class, login)).andReturn(new Personne());
        EasyMock.expect(em.find(Etape.class, idEtape)).andReturn(e);
        EasyMock.expect(em.createQuery(query)).andReturn(q);
        EasyMock.expect(q.setParameter(1, e)).andReturn(q);
        EasyMock.expect(q.getSingleResult()).andReturn(t);

        EasyMock.replay(em);
        EasyMock.replay(q);

        facade.reserverTrajet(login, idEtape, nbrPlaces, message);

        EasyMock.verify();

    }

    //Scénario: Reservation bien ajoutée
    @Test
    public void reserverTrajetCorrect() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException, TrajetDejaProposeException {
        thrown = ExpectedException.none();

        Personne p = new Personne();
        Personne p2 = new Personne();
        p2.setLogin("toto");
        p.setLogin(login);

        Etape e = new Etape();

        Trajet t = new Trajet();
        t.setNbrPLacesDisponibles(10);

        t.setConducteur(p2);
        String query = new String("FROM Trajet t WHERE ?1 MEMBER OF t.etapes");
        Capture<Reservation> captureReservation = newCapture();



        EasyMock.expect(em.find(Personne.class, login)).andReturn(new Personne());
        EasyMock.expect(em.find(Etape.class, idEtape)).andReturn(e);
        EasyMock.expect(em.createQuery(query)).andReturn(q);
        EasyMock.expect(q.setParameter(1, e)).andReturn(q);
        EasyMock.expect(q.getSingleResult()).andReturn(t);
        em.persist(capture(captureReservation));

        EasyMock.replay(em);
        EasyMock.replay(q);

        facade.reserverTrajet(login, idEtape, nbrPlaces, message);

        EasyMock.verify();
    }

    //Scénario: Données invalides
    @Test
    public void accepterReservationDonneesInvalides() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException {
        thrown.expect(DonneesInvalidesException.class);

        facade.accepterReservation(-5);
    }

    //Scénario : Réservation introuvable dans la base de données
    @Test
    public void accepterReservationIntrouvableDansLaBase() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException {
        thrown.expect(IntrouvableDansLaBaseException.class);

        EasyMock.expect(em.find(Reservation.class, idReservation)).andReturn(null);
        EasyMock.replay(em);

        facade.accepterReservation(idReservation);
        EasyMock.verify(em);
    }

    //Scénario : Plus assez de places dans le trajet
    @Test
    public void accepterReservationPasAssezDePlaces() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException {
        thrown.expect(PasAssezDePlacesException.class);
        Reservation r = new Reservation();
        r.setNbrPlaces(10);
        Trajet t = new Trajet();
        t.setNbrPLacesDisponibles(2);

        EasyMock.expect(em.find(Reservation.class, idReservation)).andReturn(r);
        String query= "SELECT t FROM Trajet t WHERE ?1 MEMBER OF t.etapes";
        EasyMock.expect(em.createQuery(query)).andReturn(q);
        EasyMock.expect(q.setParameter(1, r.getEtape())).andReturn(q);
        EasyMock.expect(q.getSingleResult()).andReturn(t);

        EasyMock.replay(em);
        EasyMock.replay(q);

        facade.accepterReservation(idReservation);
        EasyMock.verify(em, q);
    }

    /*
    //Scénario : Acceptation correcte
    @Test
    public void accepterReservationCorrect() throws DonneesInvalidesException, PasAssezDePlacesException, IntrouvableDansLaBaseException {
        thrown = ExpectedException.none();
        Reservation r = new Reservation();
        r.setEtape(new Etape());
        r.setNbrPlaces(2);
        Trajet t = new Trajet();
        t.setNbrPLacesDisponibles(10);
        List<Reservation> listeR = new ArrayList<Reservation>();
        listeR.add(new Reservation());
        listeR.add(new Reservation());
        listeR.add(new Reservation());
        Capture<List<Reservation>> captureListeR = EasyMock.newCapture();

        EasyMock.expect(em.find(Reservation.class, idReservation)).andReturn(r);
        String query= "SELECT t FROM Trajet t WHERE ?1 MEMBER OF t.etapes";
        EasyMock.expect(em.createQuery(query)).andReturn(q);
        EasyMock.expect(q.setParameter(1, r.getEtape())).andReturn(q);
        EasyMock.expect(q.getSingleResult()).andReturn(t);

        String query2 = "SELECT r, t FROM Reservation r , Trajet t WHERE t = :trajet AND r.etape MEMBER OF t.etapes" +
                " AND r.nbrPlaces > ?2";
        EasyMock.expect(em.createQuery(query2)).andReturn(q);
        EasyMock.expect(q.setParameter("trajet", t)).andReturn(q);
        EasyMock.expect(q.setParameter(2, t.getNbrPLacesDisponibles()-r.getNbrPlaces())).andReturn(q);
        EasyMock.expect(q.getResultList()).andReturn(listeR);

        em.remove(EasyMock.capture(captureListeR));


        EasyMock.replay(em);
        EasyMock.replay(q);

        facade.accepterReservation(idReservation);
        EasyMock.verify(em, q);
    }

*/

    // Scenario : Données invalides
    @Test
    public void refuserReservationDonneesInvalides() throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        thrown.expect(DonneesInvalidesException.class);

        facade.refuserReservation(-10);
    }

    // Scenario : Introuvable dans la base
    @Test
    public void refuserReservationIntrouvableDansLaBase() throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        thrown.expect(IntrouvableDansLaBaseException.class);

        EasyMock.expect(em.find(Reservation.class, idReservation)).andReturn(null);
        EasyMock.replay(em);

        facade.refuserReservation(idReservation);
        EasyMock.verify(em);
    }

    // Scenario : Refus correct
    @Test
    public void refuserReservationCorrect() throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        thrown = ExpectedException.none();
        Reservation r = new Reservation();
        r.setId(idReservation);

        EasyMock.expect(em.find(Reservation.class, idReservation)).andReturn(r);

        EasyMock.replay(em);

        facade.refuserReservation(idReservation);
        EasyMock.verify(em);

        Assert.assertEquals(Reservation.Etat.REFUSEE, r.getEtat());

    }

    // Scenario : Données invalides
    @Test
    public void annulerReservationDonneesInvalides() throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        thrown.expect(DonneesInvalidesException.class);

        facade.annulerReservation(-10);
    }

    // Scenario : Introuvable dans la base
    @Test
    public void annulerReservationIntrouvableDansLaBase() throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        thrown.expect(IntrouvableDansLaBaseException.class);

        EasyMock.expect(em.find(Reservation.class, idReservation)).andReturn(null);
        EasyMock.replay(em);

        facade.annulerReservation(idReservation);
        EasyMock.verify(em);
    }


    // Scenario : Annulation correcte
    @Test
    public void annulerReservationCorrect() throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        thrown = ExpectedException.none();
        Capture<Reservation> captureR = EasyMock.newCapture();
        Reservation r = new Reservation();
        r.setId(idReservation);

        EasyMock.expect(em.find(Reservation.class, idReservation)).andReturn(r);
        em.remove(EasyMock.capture(captureR));

        EasyMock.replay(em);

        facade.annulerReservation(idReservation);
        EasyMock.verify(em);

        Assert.assertEquals(idReservation, captureR.getValue().getId());
    }






}
