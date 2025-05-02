package services;

import models.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PharmacieService {
    private List<Medicament> stockMedicaments;
    private List<Client> clients;
    private List<Fournisseur> fournisseurs;
    private List<Commande> commandes;
    private List<Ordonnance> ventes;

    public PharmacieService() {
        this.stockMedicaments = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.fournisseurs = new ArrayList<>();
        this.commandes = new ArrayList<>();
        this.ventes = new ArrayList<>();

        // Initialize with sample data
        initialiserDonneesTest();
    }

    // Initialize test data
    private void initialiserDonneesTest() {
        // Add some suppliers
        Fournisseur f1 = new Fournisseur("Pharma Plus", "123 Rue de la Santé, Alger", "021123456");
        Fournisseur f2 = new Fournisseur("MediStock", "45 Boulevard des Médicaments, Oran", "041789012");
        ajouterFournisseur(f1);
        ajouterFournisseur(f2);

        // Add some medications
        Medicament m1 = new MedicamentComprime("Paracétamol", "PAR001", 100,
                LocalDate.now().plusMonths(12), true, 2.50, 20);
        Medicament m2 = new MedicamentSirop("Sirop pour la toux", "SIR001", 50,
                LocalDate.now().plusMonths(6), false, 5.75, 150);
        Medicament m3 = new MedicamentInjectable("Vaccin antigrippal", "VAC001", 30,
                LocalDate.now().plusMonths(3), false, 15.00);
        ajouterMedicament(m1);
        ajouterMedicament(m2);
        ajouterMedicament(m3);

        // Add some clients
        Client c1 = new Client("Belaid", "Karim", "0661234567");
        Client c2 = new Client("Hadj", "Fatima", "0772345678");
        c2.setCarteAssurance(new CarteAssuranceMaladie("123456789", true, 0.70)); // 70% coverage
        ajouterClient(c1);
        ajouterClient(c2);
    }

    // Medication management
    public void ajouterMedicament(Medicament medicament) {
        stockMedicaments.add(medicament);
    }

    public boolean supprimerMedicament(String reference) {
        return stockMedicaments.removeIf(med -> med.getReference().equals(reference));
    }

    public Medicament trouverMedicament(String reference) {
        return stockMedicaments.stream()
                .filter(med -> med.getReference().equals(reference))
                .findFirst()
                .orElse(null);
    }

    public List<Medicament> listerMedicaments() {
        return new ArrayList<>(stockMedicaments);
    }

    public List<Medicament> listerMedicamentsProchesPeremption() {
        return stockMedicaments.stream()
                .filter(Medicament::expireBientot)
                .collect(Collectors.toList());
    }

    // Client management
    public void ajouterClient(Client client) {
        clients.add(client);
    }

    public boolean supprimerClient(String telephone) {
        return clients.removeIf(client -> client.getNumeroTelephone().equals(telephone));
    }

    public Client trouverClient(String telephone) {
        return clients.stream()
                .filter(client -> client.getNumeroTelephone().equals(telephone))
                .findFirst()
                .orElse(null);
    }

    public List<Client> listerClients() {
        return new ArrayList<>(clients);
    }

    // Supplier management
    public void ajouterFournisseur(Fournisseur fournisseur) {
        fournisseurs.add(fournisseur);
    }

    public boolean supprimerFournisseur(String telephone) {
        return fournisseurs.removeIf(f -> f.getNumeroTelephone().equals(telephone));
    }

    public Fournisseur trouverFournisseur(String telephone) {
        return fournisseurs.stream()
                .filter(f -> f.getNumeroTelephone().equals(telephone))
                .findFirst()
                .orElse(null);
    }

    public List<Fournisseur> listerFournisseurs() {
        return new ArrayList<>(fournisseurs);
    }

    // Order management
    public Commande creerCommande(Fournisseur fournisseur) {
        Commande commande = new Commande(fournisseur);
        commandes.add(commande);
        return commande;
    }

    public void livrerCommande(Commande commande) {
        if (!commande.isEstLivree()) {
            // Update stock for each medication in the order
            for (LigneMedicament ligne : commande.getLignes()) {
                ligne.getMedicament().augmenterStock(ligne.getQuantite());
            }
            commande.setEstLivree(true);
        }
    }

    public List<Commande> listerCommandes() {
        return new ArrayList<>(commandes);
    }

    // Sales management
    public Ordonnance creerOrdonnance(Client client, LocalDate datePrescription) {
        Ordonnance ordonnance = new Ordonnance(datePrescription, client);
        return ordonnance;
    }

    public boolean validerOrdonnance(Ordonnance ordonnance) {
        // Check if all medications are available in sufficient quantity
        for (LigneMedicament ligne : ordonnance.getLignes()) {
            Medicament med = ligne.getMedicament();
            if (med.getQuantiteStock() < ligne.getQuantite() || med.estPerime()) {
                return false;
            }
        }
        return true;
    }

    public boolean vendreOrdonnance(Ordonnance ordonnance) {
        if (validerOrdonnance(ordonnance)) {
            // Update stock for each medication
            for (LigneMedicament ligne : ordonnance.getLignes()) {
                ligne.getMedicament().reduireStock(ligne.getQuantite());
            }

            // Calculate price
            ordonnance.calculerPrix();

            // Add to sales history
            ventes.add(ordonnance);

            // Add to client history
            ordonnance.getClient().ajouterOrdonnance(ordonnance);

            return true;
        }
        return false;
    }

    public List<Ordonnance> listerVentes() {
        return new ArrayList<>(ventes);
    }

    public List<Ordonnance> listerHistoriqueClient(Client client) {
        return client.getHistorique();
    }
}