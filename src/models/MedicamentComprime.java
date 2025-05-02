package models;

import java.time.LocalDate;

public class MedicamentComprime extends Medicament {
    private int nombreUnites;

    public MedicamentComprime(String nom, String reference, int quantiteStock,
                              LocalDate datePeremption, boolean estGenerique,
                              double prix, int nombreUnites) {
        super(nom, reference, quantiteStock, datePeremption, estGenerique, prix);
        this.nombreUnites = nombreUnites;
    }

    public int getNombreUnites() { return nombreUnites; }
    public void setNombreUnites(int nombreUnites) { this.nombreUnites = nombreUnites; }

    @Override
    public String toString() {
        return super.toString() + ", Comprimé: " + nombreUnites + " unités";
    }
}