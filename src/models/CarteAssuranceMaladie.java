package models;

public class CarteAssuranceMaladie {
    private String numeroSecuriteSociale;
    private boolean estActive;
    private double tauxReduction; // percentage (0.0 to 1.0)

    public CarteAssuranceMaladie(String numeroSecuriteSociale, boolean estActive, double tauxReduction) {
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.estActive = estActive;
        this.tauxReduction = tauxReduction;
    }

    // Getters and setters
    public String getNumeroSecuriteSociale() { return numeroSecuriteSociale; }
    public void setNumeroSecuriteSociale(String numeroSecuriteSociale) { this.numeroSecuriteSociale = numeroSecuriteSociale; }

    public boolean isEstActive() { return estActive; }
    public void setEstActive(boolean estActive) { this.estActive = estActive; }

    public double getTauxReduction() { return tauxReduction; }
    public void setTauxReduction(double tauxReduction) { this.tauxReduction = tauxReduction; }

    // Calculate discount amount based on total price
    public double calculerReduction(double prixTotal) {
        return estActive ? prixTotal * tauxReduction : 0.0;
    }
}