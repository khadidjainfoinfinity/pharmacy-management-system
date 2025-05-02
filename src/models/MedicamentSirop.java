package models;

import java.time.LocalDate;

public class MedicamentSirop extends Medicament {
    private int contenanceML;

    public MedicamentSirop(String nom, String reference, int quantiteStock,
                           LocalDate datePeremption, boolean estGenerique,
                           double prix, int contenanceML) {
        super(nom, reference, quantiteStock, datePeremption, estGenerique, prix);
        this.contenanceML = contenanceML;
    }

    public int getContenanceML() { return contenanceML; }
    public void setContenanceML(int contenanceML) { this.contenanceML = contenanceML; }

    @Override
    public String toString() {
        return super.toString() + ", Sirop: " + contenanceML + " ml";
    }
}