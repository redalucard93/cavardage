import ejbs.Facade;
import ejbs.IFacade;
import entities.*;
import exceptions.*;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertSame;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.newCapture;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by alphalion27 on 01/11/17.
 */
public class GestionUtilisateurTest {

    IFacade facade;
    EntityManager em;
    Query q;


    @Before
    public void init() {
        facade = new Facade();
        em = EasyMock.strictMock(EntityManager.class);
        q = EasyMock.strictMock(Query.class);
        facade.setEm(em);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void inscriptionParametresNull() throws LoginExistantException, DonneesInvalidesException {
        thrown.expect(DonneesInvalidesException.class);
        facade.inscription("login", null, "nom", "prenom", null, "adresse");
    }


    @Test
    public void inscriptionLoginExistant() throws LoginExistantException, DonneesInvalidesException {
        thrown.expect(LoginExistantException.class);

        Personne p = new Personne();
        p.setLogin("login");
        EasyMock.expect(em.find(Personne.class, "login")).andReturn(p);
        EasyMock.replay(em);

        facade.inscription("login", "password", "nom", "prenom", LocalDate.parse("1994-09-27"), "adresse");
        EasyMock.verify();
    }

    @Test
    public void inscriptionNormale() throws LoginExistantException, DonneesInvalidesException {
        thrown = ExpectedException.none();
        LocalDate date = LocalDate.parse("1994-09-27");

        Personne p = new Personne("login", "password", "nom", "prenom", date, "adresse");

        Capture<Personne> capturePersonne = newCapture();
        EasyMock.expect(em.find(Personne.class, "login")).andReturn(null);
        em.persist(capture(capturePersonne));
        EasyMock.replay(em);

        facade.inscription("login", "password", "nom", "prenom", date, "adresse");

        assertEquals(capturePersonne.getValue(), p);
        EasyMock.verify();

    }

    //Scenario : parametres en entrée non valides connexion
    @Test
    public void connexionParametresNonValides() throws DonneesInvalidesException, IdentifiantsIncorrectsException {
        thrown.expect(DonneesInvalidesException.class);

        facade.connexion(null, null);
    }

    //Scenario : Login inexistant en base de données
    @Test
    public void connexionLoginInexistant() throws DonneesInvalidesException, IdentifiantsIncorrectsException {
        thrown.expect(IdentifiantsIncorrectsException.class);

        String login = "login";
        String password = "password";

        EasyMock.expect(em.find(Personne.class, login)).andReturn(null);
        EasyMock.replay(em);

        facade.connexion(login, "password");
        EasyMock.verify();
    }

    //Scenario : Login existant mauvais password
    @Test
    public void connexionMauvaisPassword() throws DonneesInvalidesException, IdentifiantsIncorrectsException {
        thrown.expect(IdentifiantsIncorrectsException.class);

        String login = "login";
        String password = "password";
        Personne p = new Personne();
        p.setLogin(login);
        p.setPassword(password);

        EasyMock.expect(em.find(Personne.class, login)).andReturn(p);
        EasyMock.replay(em);

        facade.connexion(login, "mauvaisPassword");
        EasyMock.verify();
    }

    //Scénario: connexion fonctionne correctement
    @Test
    public void connexionCorrecte() throws DonneesInvalidesException, IdentifiantsIncorrectsException {
        thrown = ExpectedException.none();

        String login = "login";
        String password = "password";
        Personne p = new Personne();
        p.setLogin(login);
        p.setPassword(password);

        EasyMock.expect(em.find(Personne.class, login)).andReturn(p);
        EasyMock.replay(em);

        facade.connexion(login, password);
        EasyMock.verify();
    }

  /**  //Scenario: LaisserAvis données invalides
    @Test
    public void laisserAvisDonneesInvalides() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown.expect(DonneesInvalidesException.class);

        try {
            facade.laisserAvis(null, null, 5, "", 0);
        } catch (AvisDejaLaisseException e) {
            e.printStackTrace();
        }
    }

    //Scénario: Etape introuvable dans la base
    @Test
    public void laisserAvisEtapeIntrouvable() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown.expect(IntrouvableDansLaBaseException.class);

        EasyMock.expect(em.find(Personne.class, "loginE")).andReturn(new Personne());
        EasyMock.expect(em.find(Personne.class, "loginD")).andReturn(new Personne());
        EasyMock.expect(em.find(Etape.class, 5)).andReturn(null);

        EasyMock.replay(em);

        try {
            facade.laisserAvis("loginE", "loginD", 5, "message", 3);
        } catch (AvisDejaLaisseException e) {
            e.printStackTrace();
        }
        EasyMock.verify(em);
    }

    //Scenario : Note > maxNote
    @Test
    public void laisserAvisNoteMax() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown.expect(DonneesInvalidesException.class);

        try {
            facade.laisserAvis("loginE", "loginD", 5, "message", Avis.NOTE_MAX + 1);
        } catch (AvisDejaLaisseException e) {
            e.printStackTrace();
        }
    }

    //Scénario: Voyage non terminé
    @Test
    public void laisserAvisVoyageNonTermine() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown.expect(VoyageNonTermineException.class);

        Etape e = new Etape();
        e.setHeureArrivee(LocalDateTime.now().minusMinutes(1));
        EasyMock.expect(em.find(Personne.class, "loginE")).andReturn(new Personne());
        EasyMock.expect(em.find(Personne.class, "loginD")).andReturn(new Personne());
        EasyMock.expect(em.find(Etape.class, 5)).andReturn(e);

        EasyMock.replay(em);

        try {
            facade.laisserAvis("loginE", "loginD", 5, "message", 3);
        } catch (AvisDejaLaisseException e1) {
            e1.printStackTrace();
        }
        EasyMock.verify(em);
    }

    //Scénario: Laisser avis correct
    @Test
    public void laisserAvisCorrect() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown = ExpectedException.none();
        Capture<Avis> captureAvis = EasyMock.newCapture();

        Etape e = new Etape();
        e.setHeureArrivee(LocalDateTime.now().plusMinutes(1));

        EasyMock.expect(em.find(Personne.class, "loginE")).andReturn(new Personne());
        EasyMock.expect(em.find(Personne.class, "loginD")).andReturn(new Personne());
        EasyMock.expect(em.find(Etape.class, 5)).andReturn(e);
        em.persist(EasyMock.capture(captureAvis));

        EasyMock.replay(em);

        try {
            facade.laisserAvis("loginE", "loginD", 5, "message", 3);
        } catch (AvisDejaLaisseException e1) {
            e1.printStackTrace();
        }
        EasyMock.verify(em);
    }

    //Scénario liste Avis login non valide
    @Test
    public void listeAvisDonneesInvalides() throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        thrown.expect(DonneesInvalidesException.class);

        facade.listeAvis("");
    }

    //Scénario: Personne introuvable dans la base
    @Test
    public void listeAvisPersonneIntrouvable() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown.expect(IntrouvableDansLaBaseException.class);

        EasyMock.expect(em.find(Personne.class, "login")).andReturn(null);
        EasyMock.replay(em);

        facade.listeAvis("login");
        EasyMock.verify(em);
    }

    //Scénario: Aucun avis dans la base
    @Test
    public void listeAvisAucunAvis() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown = ExpectedException.none();

        Personne p = new Personne("login", "password", "nom", "prenom", LocalDate.now(), "adresse");

        EasyMock.expect(em.find(Personne.class, "login")).andReturn(p);
        String query = "SELECT a FROM Avis a WHERE a.destinataire=?1";
        EasyMock.expect(em.createQuery(query)).andReturn(q);
        EasyMock.expect(q.setParameter(1, p)).andReturn(q);
        EasyMock.expect(q.getResultList()).andReturn(null);

        EasyMock.replay(em, q);
        facade.listeAvis("login");
        EasyMock.verify(em, q);
    }

    //Scénario: 3 avis dans la base
    @Test
    public void listeAvisCorrect() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown = ExpectedException.none();

        List<Avis> l = new ArrayList<>();
        l.add(new Avis());
        l.add(new Avis());
        l.add(new Avis());

        Personne p = new Personne("login", "password", "nom", "prenom", LocalDate.now(), "adresse");

        EasyMock.expect(em.find(Personne.class, "login")).andReturn(p);
        String query = "SELECT a FROM Avis a WHERE a.destinataire=?1";
        EasyMock.expect(em.createQuery(query)).andReturn(q);
        EasyMock.expect(q.setParameter(1, p)).andReturn(q);
        EasyMock.expect(q.getResultList()).andReturn(l);

        EasyMock.replay(em, q);

        Assert.assertEquals(facade.listeAvis("login").size(), 3);
        EasyMock.verify(em, q);

    }

    //Scénario données invalides
    @Test
    public void declarerVehiculeDonneesInvalides() throws DonneesInvalidesException, IntrouvableDansLaBaseException {
        thrown.expect(DonneesInvalidesException.class);

        facade.declarerVehicule("login", null, 1, 2);
    }

    //Scénario: GABARIT introuvable dans la base
    @Test
    public void declarerVehiculeGabaritIntrouvable() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown.expect(IntrouvableDansLaBaseException.class);

        EasyMock.expect(em.find(Personne.class, "login")).andReturn(new Personne());
        EasyMock.expect(em.find(Gabarit.class, 3)).andReturn(null);
        EasyMock.replay(em);

        facade.declarerVehicule("login", "moele", 3, 2);
        EasyMock.verify(em);
    }

    //Scénario: Données correctes
    @Test
    public void declarerVehiculeDonneesCorrectes() throws DonneesInvalidesException, VoyageNonTermineException, IntrouvableDansLaBaseException {
        thrown = ExpectedException.none();

        Capture<Voiture> captureVoiture = newCapture();
        Personne p = new Personne();

        EasyMock.expect(em.find(Personne.class, "login")).andReturn(p);
        EasyMock.expect(em.find(Gabarit.class, 3)).andReturn(new Gabarit());
        em.persist(EasyMock.capture(captureVoiture));
        EasyMock.replay(em);

        facade.declarerVehicule("login", "modele", 3, 2);
        EasyMock.verify(em);

        Assert.assertEquals(p.getVoiture(), captureVoiture.getValue());
    }
**/

}
