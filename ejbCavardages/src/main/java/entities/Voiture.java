package entities;

import javax.persistence.*;

/**
 * Created by alphalion27 on 01/11/17.
 */

@Entity
public class Voiture {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String modele;
    private int nbrPlaces;

    @ManyToOne
    private Gabarit gabarit;

    public Voiture() {
    }

    public Voiture(String modele, Gabarit gabarit, int nbP) {
        this.modele = modele;
        this.gabarit = gabarit;
        this.nbrPlaces = nbP;
    }

    @Override
    public boolean equals(Object o){
        Voiture v = (Voiture)o;
        return id == v.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Gabarit getGabarit() {
        return gabarit;
    }

    public void setGabarit(Gabarit gabarit) {
        this.gabarit = gabarit;
    }

    public int getNbrPlaces() {
        return nbrPlaces;
    }

    public void setNbrPlaces(int nbrPlaces) {
        this.nbrPlaces = nbrPlaces;
    }
}
