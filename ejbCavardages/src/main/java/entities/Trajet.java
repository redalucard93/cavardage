package entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trajet {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private LocalDateTime heureDepart;

    private int nbrPLacesDisponibles;

    @ManyToOne
    private Personne conducteur;

    @ManyToOne
    private Ville villeDepart;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trajet", cascade = CascadeType.ALL)
    private List<Etape> etapes;

    public Trajet(LocalDateTime heureDepart, int nbrPLacesDisponibles, Personne conducteur, Ville villeDepart, List<Etape> etapes) {
        this.heureDepart = heureDepart;
        this.nbrPLacesDisponibles = nbrPLacesDisponibles;
        this.conducteur = conducteur;
        this.villeDepart = villeDepart;
        this.etapes = etapes;
    }

    public Trajet() {
        this.etapes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalDateTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public int getNbrPLacesDisponibles() {
        return nbrPLacesDisponibles;
    }

    public void setNbrPLacesDisponibles(int nbrPLacesDisponibles) {
        this.nbrPLacesDisponibles = nbrPLacesDisponibles;
    }

    public Personne getConducteur() {
        return conducteur;
    }

    public void setConducteur(Personne conducteur) {
        this.conducteur = conducteur;
    }

    public Ville getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(Ville villeDepart) {
        this.villeDepart = villeDepart;
    }

    public List<Etape> getEtapes() {
        return etapes;
    }

    public void setEtapes(List<Etape> etapes) {
        this.etapes = etapes;
    }

    public Etape getEtapeById(int idVilleArrive) {
        Etape result = null;
        for (Etape e : etapes) {
            if (e.getVilleArrivee().getId() == idVilleArrive){
                result = e;
            }
        }
        return result;
    }

    public Etape getEtapeFinal() {
        return this.getEtapes().get(this.getEtapes().size() - 1);
    }

}
