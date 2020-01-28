package entities;

import javax.persistence.*;

/**
 * Created by alphalion27 on 01/11/17.
 */

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String message;
    private int nbrPlaces;
    private Etat etat;

    @ManyToOne
    private Personne demandeur;

    @ManyToOne
    private Etape etape;

    public enum Etat {ATTENTE, ACCEPTEE, REFUSEE};

    public Reservation() {
    }

    public Reservation(String message, int nbrPlaces, Personne demandeur, Etape etape) {
        this.message = message;
        this.nbrPlaces = nbrPlaces;
        this.demandeur = demandeur;
        this.etape = etape;
        this.etat = Etat.ATTENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNbrPlaces() {
        return nbrPlaces;
    }

    public void setNbrPlaces(int nbrPlaces) {
        this.nbrPlaces = nbrPlaces;
    }

    public Personne getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Personne demandeur) {
        this.demandeur = demandeur;
    }

    public Etape getEtape() {
        return etape;
    }

    public void setEtape(Etape etape) {
        this.etape = etape;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }
}
