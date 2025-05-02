package models;

public class LigneMedicament {
    private Medicament medicament;
    private int quantite;
    private String dosage;
    private String frequence;
    private int dureeTraitement; // in days

    // Constructor for order lines (no dosage, frequency or treatment duration)
    public LigneMedicament(Medicament medicament, int quantite) {
        this.medicament = medicament;
        this.quantite = quantite;
        this.dosage = null;
        this.frequence = null;
        this.dureeTraitement = 0;
    }

    // Constructor for prescription lines (with dosage, frequency and treatment duration)
    public LigneMedicament(Medicament medicament, int quantite, String dosage, String frequence, int dureeTraitement) {
        this.medicament = medicament;
        this.quantite = quantite;
        this.dosage = dosage;
        this.frequence = frequence;
        this.dureeTraitement = dureeTraitement;
    }

    // Getters and setters
    public Medicament getMedicament() { return medicament; }
    public void setMedicament(Medicament medicament) { this.medicament = medicament; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getFrequence() { return frequence; }
    public void setFrequence(String frequence) { this.frequence = frequence; }

    public int getDureeTraitement() { return dureeTraitement; }
    public void setDureeTraitement(int dureeTraitement) { this.dureeTraitement = dureeTraitement; }

    // Calculate total price for this line
    public double calculerPrixTotal() {
        return medicament.getPrix() * quantite;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(medicament.getNom()).append(" - Quantité: ").append(quantite);

        if (dosage != null) {
            sb.append(", Dosage: ").append(dosage);
            sb.append(", Fréquence: ").append(frequence);
            sb.append(", Durée: ").append(dureeTraitement).append(" jours");
        }

        return sb.toString();
    }
}