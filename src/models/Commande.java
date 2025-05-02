package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Commande {
    private static int compteur = 0;

    private int id;
    private Fournisseur fournisseur;
    private List<LigneMedicament> lignes;
    private LocalDate dateCommande;
    private boolean estLivree;

    public Commande(Fournisseur fournisseur) {
        this.id = ++compteur;
        this.fournisseur = fournisseur;
        this.lignes = new ArrayList<>();
        this.dateCommande = LocalDate.now();
        this.estLivree = false;
    }

    // Getters and setters
    public int getId() { return id; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public List<LigneMedicament> getLignes() { return lignes; }

    public LocalDate getDateCommande() { return dateCommande; }

    public boolean isEstLivree() { return estLivree; }
    public void setEstLivree(boolean estLivree) { this.estLivree = estLivree; }

    // Add a medication to the order
    public void ajouterMedicament(Medicament medicament, int quantite) {
        lignes.add(new LigneMedicament(medicament, quantite));
    }

    // Calculate total price of the order
    public double calculerPrixTotal() {
        double total = 0.0;
        for (LigneMedicament ligne : lignes) {
            total += ligne.calculerPrixTotal();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Commande #").append(id).append("\n");
        sb.append("Fournisseur: ").append(fournisseur.getNom()).append("\n");
        sb.append("Date: ").append(dateCommande).append("\n");
        sb.append("Statut: ").append(estLivree ? "Livrée" : "En attente").append("\n");
        sb.append("Médicaments commandés:\n");

        for (LigneMedicament ligne : lignes) {
            sb.append(" - ").append(ligne.toString()).append("\n");
        }

        sb.append("Prix total: ").append(calculerPrixTotal()).append("€");
        return sb.toString();
    }
}
