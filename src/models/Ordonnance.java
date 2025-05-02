package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ordonnance {
    private static int compteur = 0;

    private int id;
    private LocalDate datePrescription;
    private List<LigneMedicament> lignes;
    private Client client;
    private double prixTotal;
    private double prixApresReduction;

    public Ordonnance(LocalDate datePrescription, Client client) {
        this.id = ++compteur;
        this.datePrescription = datePrescription;
        this.client = client;
        this.lignes = new ArrayList<>();
        this.prixTotal = 0.0;
        this.prixApresReduction = 0.0;
    }

    // Getters and setters
    public int getId() { return id; }

    public LocalDate getDatePrescription() { return datePrescription; }
    public void setDatePrescription(LocalDate datePrescription) { this.datePrescription = datePrescription; }

    public List<LigneMedicament> getLignes() { return lignes; }

    public Client getClient() { return client; }

    public double getPrixTotal() { return prixTotal; }

    public double getPrixApresReduction() { return prixApresReduction; }

    // Add a medication to the prescription
    public void ajouterMedicament(Medicament medicament, int quantite, String dosage, String frequence, int dureeTraitement) {
        lignes.add(new LigneMedicament(medicament, quantite, dosage, frequence, dureeTraitement));
    }

    // Calculate total price and apply insurance discount if applicable
    public void calculerPrix() {
        prixTotal = 0.0;
        for (LigneMedicament ligne : lignes) {
            prixTotal += ligne.calculerPrixTotal();
        }

        prixApresReduction = prixTotal;

        // Apply insurance discount if the client has a valid insurance card
        if (client.getCarteAssurance() != null && client.getCarteAssurance().isEstActive()) {
            double reduction = client.getCarteAssurance().calculerReduction(prixTotal);
            prixApresReduction = prixTotal - reduction;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ordonnance #").append(id).append("\n");
        sb.append("Client: ").append(client.toString()).append("\n");
        sb.append("Date de prescription: ").append(datePrescription).append("\n");
        sb.append("Médicaments prescrits:\n");

        for (LigneMedicament ligne : lignes) {
            sb.append(" - ").append(ligne.toString()).append("\n");
        }

        sb.append("Prix total: ").append(prixTotal).append("€\n");

        if (prixTotal != prixApresReduction) {
            sb.append("Prix après réduction: ").append(prixApresReduction).append("€");
        }

        return sb.toString();
    }
}
