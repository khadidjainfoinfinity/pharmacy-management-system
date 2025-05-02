package models;

import java.time.LocalDate;

public abstract class Medicament {
    protected String nom;
    protected String reference;
    protected int quantiteStock;
    protected LocalDate datePeremption;
    protected boolean estGenerique;
    protected double prix;

    public Medicament(String nom, String reference, int quantiteStock,
                      LocalDate datePeremption, boolean estGenerique, double prix) {
        this.nom = nom;
        this.reference = reference;
        this.quantiteStock = quantiteStock;
        this.datePeremption = datePeremption;
        this.estGenerique = estGenerique;
        this.prix = prix;
    }

    // Getters and setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public int getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }

    public LocalDate getDatePeremption() { return datePeremption; }
    public void setDatePeremption(LocalDate datePeremption) { this.datePeremption = datePeremption; }

    public boolean isEstGenerique() { return estGenerique; }
    public void setEstGenerique(boolean estGenerique) { this.estGenerique = estGenerique; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    // Check if medication is expired
    public boolean estPerime() {
        return LocalDate.now().isAfter(datePeremption);
    }

    // Check if medication will expire soon (within 30 days)
    public boolean expireBientot() {
        LocalDate dateLimite = LocalDate.now().plusDays(30);
        return datePeremption.isBefore(dateLimite) && !estPerime();
    }

    // Reduce stock when medication is sold
    public boolean reduireStock(int quantite) {
        if (quantite <= quantiteStock) {
            quantiteStock -= quantite;
            return true;
        }
        return false;
    }

    // Increase stock when medication is ordered
    public void augmenterStock(int quantite) {
        quantiteStock += quantite;
    }

    @Override
    public String toString() {
        return "Médicament: " + nom + " (Réf: " + reference + "), Stock: " + quantiteStock +
                ", Péremption: " + datePeremption + ", " + (estGenerique ? "Générique" : "Spécifique") +
                ", Prix: " + prix + "€";
    }
}