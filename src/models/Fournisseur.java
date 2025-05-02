package models;

public class Fournisseur {
    private String nom;
    private String adresse;
    private String numeroTelephone;

    public Fournisseur(String nom, String adresse, String numeroTelephone) {
        this.nom = nom;
        this.adresse = adresse;
        this.numeroTelephone = numeroTelephone;
    }

    // Getters and setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getNumeroTelephone() { return numeroTelephone; }
    public void setNumeroTelephone(String numeroTelephone) { this.numeroTelephone = numeroTelephone; }

    @Override
    public String toString() {
        return "Fournisseur: " + nom + ", Adresse: " + adresse + ", Tél: " + numeroTelephone;
    }
}