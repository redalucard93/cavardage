package entities;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by alphalion27 on 01/11/17.
 */
@Entity
public class Gabarit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String libelle;

    public Gabarit() {
    }

    public Gabarit(String libelle) {
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
