package entities;

import javax.persistence.*;

/**
 * Created by alphalion27 on 01/11/17.
 */

@Entity
public class Avis {

    public static final float NOTE_MAX = 5;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private float note;
    private String message;

    @ManyToOne
    private Personne emetteur;

    @ManyToOne
    private Personne destinataire;

    @ManyToOne
    private Etape etape;

    public Avis(float note, String message, Personne emetteur, Personne destinataire, Etape etape) {
        this.note = note;
        this.message = message;
        this.emetteur = emetteur;
        this.destinataire = destinataire;
        this.etape = etape;
    }

    public Avis() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Personne getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(Personne emetteur) {
        this.emetteur = emetteur;
    }

    public Personne getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Personne destinataire) {
        this.destinataire = destinataire;
    }

    public Etape getEtape() {
        return etape;
    }

    public void setEtape(Etape etape) {
        this.etape = etape;
    }

    public float getNote() {
        return note;
    }

}
