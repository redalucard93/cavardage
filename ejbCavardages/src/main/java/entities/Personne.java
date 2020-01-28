package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Personne {

    @Id
    private String login;
    private String password;
    private String nom;
    private String prenom;

    private LocalDate dateDeNaissance;

    private String adresse;
    private boolean admin;

   @OneToOne
    private Voiture voiture;

    @OneToMany(mappedBy = "emetteur")
    private List<Avis> avisEmetteur = new ArrayList<>();

    @OneToMany(mappedBy = "destinataire")
    private List<Avis> avisDestinataire = new ArrayList<>();

    public Personne(){

    }

    public Personne(String login, String password, String nom, String prenom, LocalDate dateDeNaissance, String adresse) {
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.admin = false;
    }

    @Override
    public boolean equals(Object o){
        Personne p = (Personne)o;
        return (p.getLogin().equals(login)
                && p.getPassword().equals(password)
                && p.getNom().equals(nom)
                && p.getPrenom().equals(prenom)
                && p.getDateDeNaissance().equals(dateDeNaissance)
                && p.getAdresse().equals(adresse));
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String name) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Avis> getAvisEmetteur() {
        return avisEmetteur;
    }

    public void setAvisEmetteur(List<Avis> avisEmetteur) {
        this.avisEmetteur = avisEmetteur;
    }

    public List<Avis> getAvisDestinataire() {
        return avisDestinataire;
    }

    public void setAvisDestinataire(List<Avis> avisDestinataire) {
        this.avisDestinataire = avisDestinataire;
    }
}
