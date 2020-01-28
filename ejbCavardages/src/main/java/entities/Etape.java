package entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alphalion27 on 01/11/17.
 */
@Entity
public class Etape {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private float prix;
    private LocalDateTime heureArrivee;

    @ManyToMany
    private List<Personne> voyageurs;

    @ManyToOne
    private Ville villeArrivee;

    @ManyToOne
    private Trajet trajet;

    public Etape(float prix, LocalDateTime heureArrivee, Ville villeArrivee) {
        this.prix = prix;
        this.heureArrivee = heureArrivee;
        this.villeArrivee = villeArrivee;
        this.voyageurs = new ArrayList<>();
    }

    public Etape() {
        this.voyageurs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public LocalDateTime getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(LocalDateTime heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public List<Personne> getVoyageurs() {
        return voyageurs;
    }

    public void setVoyageurs(List<Personne> voyageurs) {
        this.voyageurs = voyageurs;
    }

    public Ville getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(Ville villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }
}
