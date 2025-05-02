package models;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private CarteAssuranceMaladie carteAssurance;
    private List<Ordonnance> historique;

    public Client(String nom, String prenom, String numeroTelephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.historique = new ArrayList<>();
    }

    // Getters and setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getNumeroTelephone() { return numeroTelephone; }
    public void setNumeroTelephone(String numeroTelephone) { this.numeroTelephone = numeroTelephone; }

    public CarteAssuranceMaladie getCarteAssurance() { return carteAssurance; }
    public void setCarteAssurance(CarteAssuranceMaladie carteAssurance) { this.carteAssurance = carteAssurance; }

    public List<Ordonnance> getHistorique() { return historique; }

    // Add a new prescription to history
    public void ajouterOrdonnance(Ordonnance ordonnance) {
        historique.add(ordonnance);
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (Tél: " + numeroTelephone + ")";
    }
}
