package ui;

import models.*;
import services.PharmacieService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PharmacieGUI extends JFrame {
    private PharmacieService service;

    private JTabbedPane tabbedPane;
    private JPanel panelClients;
    private JPanel panelFournisseurs;
    private JPanel panelMedicaments;
    private JPanel panelCommandes;
    private JPanel panelVentes;

    private Color lightPink = new Color(255, 230, 240); // Light pink for backgrounds
    private Color buttonPink = new Color(255, 182, 193); // Pink for buttons

    public PharmacieGUI() {
        service = new PharmacieService();

        setTitle("Gestion de Pharmacie");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Initialize panels
        initClientsPanel();
        initFournisseursPanel();
        initMedicamentsPanel();
        initCommandesPanel();
        initVentesPanel();

        // Add panels to tabbed pane
        tabbedPane.addTab("Clients", panelClients);
        tabbedPane.addTab("Fournisseurs", panelFournisseurs);
        tabbedPane.addTab("Médicaments", panelMedicaments);
        tabbedPane.addTab("Commandes", panelCommandes);
        tabbedPane.addTab("Ventes", panelVentes);

        // Add tabbed pane to frame
        add(tabbedPane);

        // Set the background color of the frame
        getContentPane().setBackground(lightPink);

// Set the background color for all tabs
        setTabColors();

        setLocationRelativeTo(null);
    }
    private void setTabColors() {
        // Set background for all tab panels
        panelClients.setBackground(lightPink);
        panelFournisseurs.setBackground(lightPink);
        panelMedicaments.setBackground(lightPink);
        panelCommandes.setBackground(lightPink);
        panelVentes.setBackground(lightPink);
    }

    private void initClientsPanel() {
        panelClients = new JPanel(new BorderLayout());
        panelClients.setBackground(lightPink);

        // List of clients
        DefaultListModel<Client> listModel = new DefaultListModel<>();
        JList<Client> clientList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(clientList);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(lightPink);
        JButton btnAdd = new JButton("Ajouter");
        JButton btnEdit = new JButton("Modifier");
        JButton btnDelete = new JButton("Supprimer");
        JButton btnHistory = new JButton("Historique");

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnEdit);
        buttonsPanel.add(btnDelete);
        buttonsPanel.add(btnHistory);

        panelClients.add(scrollPane, BorderLayout.CENTER);
        panelClients.add(buttonsPanel, BorderLayout.SOUTH);

        // Load clients
        refreshClientList(listModel);

        // Add client button action
        btnAdd.addActionListener(e -> {
            JTextField fieldNom = new JTextField();
            JTextField fieldPrenom = new JTextField();
            JTextField fieldTelephone = new JTextField();

            Object[] message = {
                    "Nom:", fieldNom,
                    "Prénom:", fieldPrenom,
                    "Téléphone:", fieldTelephone
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Ajouter un client", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Client client = new Client(fieldNom.getText(), fieldPrenom.getText(), fieldTelephone.getText());
                service.ajouterClient(client);
                refreshClientList(listModel);
            }
        });

        // Delete client button action
        btnDelete.addActionListener(e -> {
            Client selectedClient = clientList.getSelectedValue();
            if (selectedClient != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Êtes-vous sûr de vouloir supprimer ce client?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    service.supprimerClient(selectedClient.getNumeroTelephone());
                    refreshClientList(listModel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            }
        });

        // Edit client button action
        btnEdit.addActionListener(e -> {
            Client selectedClient = clientList.getSelectedValue();
            if (selectedClient != null) {
                JTextField fieldNom = new JTextField(selectedClient.getNom());
                JTextField fieldPrenom = new JTextField(selectedClient.getPrenom());
                JTextField fieldTelephone = new JTextField(selectedClient.getNumeroTelephone());

                Object[] message = {
                        "Nom:", fieldNom,
                        "Prénom:", fieldPrenom,
                        "Téléphone:", fieldTelephone
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Modifier un client", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    selectedClient.setNom(fieldNom.getText());
                    selectedClient.setPrenom(fieldPrenom.getText());
                    selectedClient.setNumeroTelephone(fieldTelephone.getText());
                    refreshClientList(listModel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            }
        });
        // Show client history button action
        btnHistory.addActionListener(e -> {
            Client selectedClient = clientList.getSelectedValue();
            if (selectedClient != null) {
                List<Ordonnance> history = service.listerHistoriqueClient(selectedClient);

                if (history.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ce client n'a pas d'historique d'achat.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Ordonnance o : history) {
                        sb.append(o.toString()).append("\n\n");
                    }

                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane1 = new JScrollPane(textArea);
                    scrollPane1.setPreferredSize(new Dimension(500, 400));

                    JOptionPane.showMessageDialog(this, scrollPane1,
                            "Historique de " + selectedClient.getPrenom() + " " + selectedClient.getNom(),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            }
        });
    }

    private void refreshClientList(DefaultListModel<Client> model) {
        model.clear();
        for (Client client : service.listerClients()) {
            model.addElement(client);
        }
    }

    private void initFournisseursPanel() {
        panelFournisseurs = new JPanel(new BorderLayout());

        // List of suppliers
        DefaultListModel<Fournisseur> listModel = new DefaultListModel<>();
        JList<Fournisseur> fournisseurList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(fournisseurList);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(lightPink);
        JButton btnAdd = new JButton("Ajouter");
        JButton btnEdit = new JButton("Modifier");
        JButton btnDelete = new JButton("Supprimer");

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnEdit);
        buttonsPanel.add(btnDelete);

        panelFournisseurs.add(scrollPane, BorderLayout.CENTER);
        panelFournisseurs.add(buttonsPanel, BorderLayout.SOUTH);

        // Load suppliers
        refreshFournisseurList(listModel);

        // Add supplier button action
        btnAdd.addActionListener(e -> {
            JTextField fieldNom = new JTextField();
            JTextField fieldAdresse = new JTextField();
            JTextField fieldTelephone = new JTextField();

            Object[] message = {
                    "Nom:", fieldNom,
                    "Adresse:", fieldAdresse,
                    "Téléphone:", fieldTelephone
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Ajouter un fournisseur", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Fournisseur fournisseur = new Fournisseur(fieldNom.getText(), fieldAdresse.getText(), fieldTelephone.getText());
                service.ajouterFournisseur(fournisseur);
                refreshFournisseurList(listModel);
            }
        });

        // Delete supplier button action
        btnDelete.addActionListener(e -> {
            Fournisseur selectedFournisseur = fournisseurList.getSelectedValue();
            if (selectedFournisseur != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Êtes-vous sûr de vouloir supprimer ce fournisseur?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    service.supprimerFournisseur(selectedFournisseur.getNumeroTelephone());
                    refreshFournisseurList(listModel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un fournisseur.");
            }
        });

        // Edit supplier button action
        btnEdit.addActionListener(e -> {
            Fournisseur selectedFournisseur = fournisseurList.getSelectedValue();
            if (selectedFournisseur != null) {
                JTextField fieldNom = new JTextField(selectedFournisseur.getNom());
                JTextField fieldAdresse = new JTextField(selectedFournisseur.getAdresse());
                JTextField fieldTelephone = new JTextField(selectedFournisseur.getNumeroTelephone());

                Object[] message = {
                        "Nom:", fieldNom,
                        "Adresse:", fieldAdresse,
                        "Téléphone:", fieldTelephone
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Modifier un fournisseur", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    selectedFournisseur.setNom(fieldNom.getText());
                    selectedFournisseur.setAdresse(fieldAdresse.getText());
                    selectedFournisseur.setNumeroTelephone(fieldTelephone.getText());
                    refreshFournisseurList(listModel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un fournisseur.");
            }
        });
    }

    private void refreshFournisseurList(DefaultListModel<Fournisseur> model) {
        model.clear();
        for (Fournisseur fournisseur : service.listerFournisseurs()) {
            model.addElement(fournisseur);
        }
    }

    private void initMedicamentsPanel() {
        panelMedicaments = new JPanel(new BorderLayout());

        // List of medications
        DefaultListModel<Medicament> listModel = new DefaultListModel<>();
        JList<Medicament> medicamentList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(medicamentList);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(lightPink);
        JButton btnAdd = new JButton("Ajouter");
        JButton btnDelete = new JButton("Supprimer");
        JButton btnExpiring = new JButton("Péremption proche");

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnDelete);
        buttonsPanel.add(btnExpiring);

        panelMedicaments.add(scrollPane, BorderLayout.CENTER);
        panelMedicaments.add(buttonsPanel, BorderLayout.SOUTH);

        // Load medications
        refreshMedicamentList(listModel);

        // Add medication button action
        btnAdd.addActionListener(e -> {
            JTextField fieldNom = new JTextField();
            JTextField fieldReference = new JTextField();
            JTextField fieldQuantite = new JTextField();
            JTextField fieldPrix = new JTextField();
            JTextField fieldPeremption = new JTextField("YYYY-MM-DD");

            String[] types = {"Comprimé", "Sirop", "Injectable"};
            JComboBox<String> comboType = new JComboBox<>(types);

            JCheckBox checkGenerique = new JCheckBox("Médicament générique");

            JTextField fieldNombreUnites = new JTextField();
            JTextField fieldContenance = new JTextField();

            Object[] message = {
                    "Nom:", fieldNom,
                    "Référence:", fieldReference,
                    "Quantité en stock:", fieldQuantite,
                    "Prix:", fieldPrix,
                    "Date de péremption (YYYY-MM-DD):", fieldPeremption,
                    "Type:", comboType,
                    "", checkGenerique
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Ajouter un médicament", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String nom = fieldNom.getText();
                    String reference = fieldReference.getText();
                    int quantite = Integer.parseInt(fieldQuantite.getText());
                    double prix = Double.parseDouble(fieldPrix.getText());
                    LocalDate peremption = LocalDate.parse(fieldPeremption.getText());
                    boolean estGenerique = checkGenerique.isSelected();
                    String type = (String) comboType.getSelectedItem();

                    Medicament medicament = null;

                    if ("Comprimé".equals(type)) {
                        String input = JOptionPane.showInputDialog(this, "Nombre d'unités par boîte:", "");
                        if (input != null) {
                            int nombreUnites = Integer.parseInt(input);
                            medicament = new MedicamentComprime(nom, reference, quantite, peremption, estGenerique, prix, nombreUnites);
                        }
                    } else if ("Sirop".equals(type)) {
                        String input = JOptionPane.showInputDialog(this, "Contenance en ml:", "");
                        if (input != null) {
                            int contenance = Integer.parseInt(input);
                            medicament = new MedicamentSirop(nom, reference, quantite, peremption, estGenerique, prix, contenance);
                        }
                    } else if ("Injectable".equals(type)) {
                        medicament = new MedicamentInjectable(nom, reference, quantite, peremption, estGenerique, prix);
                    }

                    if (medicament != null) {
                        service.ajouterMedicament(medicament);
                        refreshMedicamentList(listModel);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
                }
            }
        });

        // Delete medication button action
        btnDelete.addActionListener(e -> {
            Medicament selectedMedicament = medicamentList.getSelectedValue();
            if (selectedMedicament != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Êtes-vous sûr de vouloir supprimer ce médicament?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    service.supprimerMedicament(selectedMedicament.getReference());
                    refreshMedicamentList(listModel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament.");
            }
        });

        // Show expiring medications button action
        btnExpiring.addActionListener(e -> {
            List<Medicament> expiringMeds = service.listerMedicamentsProchesPeremption();

            if (expiringMeds.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun médicament proche de la péremption.");
            } else {
                listModel.clear();
                for (Medicament medicament : expiringMeds) {
                    listModel.addElement(medicament);
                }
                JOptionPane.showMessageDialog(this, "Affichage des médicaments proches de la péremption.");
            }
        });
    }

    private void refreshMedicamentList(DefaultListModel<Medicament> model) {
        model.clear();
        for (Medicament medicament : service.listerMedicaments()) {
            model.addElement(medicament);
        }
    }

    private void initCommandesPanel() {
        panelCommandes = new JPanel(new BorderLayout());

        // List of orders
        DefaultListModel<Commande> listModel = new DefaultListModel<>();
        JList<Commande> commandeList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(commandeList);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(lightPink);
        JButton btnCreate = new JButton("Nouvelle commande");
        JButton btnDeliver = new JButton("Marquer comme livrée");
        JButton btnView = new JButton("Voir détails");

        buttonsPanel.add(btnCreate);
        buttonsPanel.add(btnDeliver);
        buttonsPanel.add(btnView);

        panelCommandes.add(scrollPane, BorderLayout.CENTER);
        panelCommandes.add(buttonsPanel, BorderLayout.SOUTH);

        // Load orders
        refreshCommandeList(listModel);

        // Create order button action
        btnCreate.addActionListener(e -> {
            List<Fournisseur> fournisseurs = service.listerFournisseurs();
            if (fournisseurs.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun fournisseur disponible. Veuillez d'abord ajouter un fournisseur.");
                return;
            }

            Fournisseur[] fournisseursArray = fournisseurs.toArray(new Fournisseur[0]);
            Fournisseur selectedFournisseur = (Fournisseur) JOptionPane.showInputDialog(
                    this, "Sélectionnez un fournisseur:", "Nouvelle commande",
                    JOptionPane.QUESTION_MESSAGE, null, fournisseursArray, fournisseursArray[0]);

            if (selectedFournisseur != null) {
                Commande commande = service.creerCommande(selectedFournisseur);

                boolean ajouterAutre = true;
                while (ajouterAutre) {
                    List<Medicament> medicaments = service.listerMedicaments();
                    if (medicaments.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Aucun médicament disponible.");
                        break;
                    }

                    Medicament[] medicamentsArray = medicaments.toArray(new Medicament[0]);
                    Medicament selectedMedicament = (Medicament) JOptionPane.showInputDialog(
                            this, "Sélectionnez un médicament:", "Ajouter médicament",
                            JOptionPane.QUESTION_MESSAGE, null, medicamentsArray, medicamentsArray[0]);

                    if (selectedMedicament != null) {
                        String input = JOptionPane.showInputDialog(this, "Quantité:", "");
                        if (input != null) {
                            try {
                                int quantite = Integer.parseInt(input);
                                commande.ajouterMedicament(selectedMedicament, quantite);

                                int option = JOptionPane.showConfirmDialog(this,
                                        "Voulez-vous ajouter un autre médicament?",
                                        "Ajouter un autre", JOptionPane.YES_NO_OPTION);

                                ajouterAutre = (option == JOptionPane.YES_OPTION);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "Quantité invalide");
                            }
                        } else {
                            ajouterAutre = false;
                        }
                    } else {
                        ajouterAutre = false;
                    }
                }

                refreshCommandeList(listModel);
            }
        });

        // Mark order as delivered button action
        btnDeliver.addActionListener(e -> {
            Commande selectedCommande = commandeList.getSelectedValue();
            if (selectedCommande != null) {
                if (!selectedCommande.isEstLivree()) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Marquer cette commande comme livrée?",
                            "Confirmation", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        service.livrerCommande(selectedCommande);
                        refreshCommandeList(listModel);
                        JOptionPane.showMessageDialog(this, "Commande livrée. Le stock a été mis à jour.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Cette commande est déjà marquée comme livrée.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une commande.");
            }
        });

        // View order details button action
        btnView.addActionListener(e -> {
            Commande selectedCommande = commandeList.getSelectedValue();
            if (selectedCommande != null) {
                JTextArea textArea = new JTextArea(selectedCommande.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane1 = new JScrollPane(textArea);
                scrollPane1.setPreferredSize(new Dimension(500, 400));

                JOptionPane.showMessageDialog(this, scrollPane1,
                        "Détails de la commande #" + selectedCommande.getId(),
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une commande.");
            }
        });
    }

    private void refreshCommandeList(DefaultListModel<Commande> model) {
        model.clear();
        for (Commande commande : service.listerCommandes()) {
            model.addElement(commande);
        }
    }

    private void initVentesPanel() {
        panelVentes = new JPanel(new BorderLayout());

        // List of sales
        DefaultListModel<Ordonnance> listModel = new DefaultListModel<>();
        JList<Ordonnance> ordonnanceList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(ordonnanceList);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(lightPink);
        JButton btnNewSale = new JButton("Nouvelle vente");
        JButton btnView = new JButton("Voir détails");

        // Style all buttons
        styleButton(btnNewSale);
        styleButton(btnView);

        buttonsPanel.add(btnNewSale);
        buttonsPanel.add(btnView);

        panelVentes.add(scrollPane, BorderLayout.CENTER);
        panelVentes.add(buttonsPanel, BorderLayout.SOUTH);

        // Load sales
        refreshOrdonnanceList(listModel);

        // Create new sale button action
        btnNewSale.addActionListener(e -> {
            List<Client> clients = service.listerClients();
            if (clients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun client disponible. Veuillez d'abord ajouter un client.");
                return;
            }

            Client[] clientsArray = clients.toArray(new Client[0]);
            Client selectedClient = (Client) JOptionPane.showInputDialog(
                    this, "Sélectionnez un client:", "Nouvelle vente",
                    JOptionPane.QUESTION_MESSAGE, null, clientsArray, clientsArray[0]);

            if (selectedClient != null) {
                // Ask for prescription date
                String dateStr = JOptionPane.showInputDialog(this,
                        "Date de prescription (YYYY-MM-DD):",
                        LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

                if (dateStr != null) {
                    try {
                        LocalDate datePrescription = LocalDate.parse(dateStr);
                        Ordonnance ordonnance = service.creerOrdonnance(selectedClient, datePrescription);

                        boolean ajouterAutre = true;
                        while (ajouterAutre) {
                            List<Medicament> medicaments = service.listerMedicaments();
                            if (medicaments.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Aucun médicament disponible.");
                                break;
                            }

                            Medicament[] medicamentsArray = medicaments.toArray(new Medicament[0]);
                            Medicament selectedMedicament = (Medicament) JOptionPane.showInputDialog(
                                    this, "Sélectionnez un médicament:", "Ajouter médicament",
                                    JOptionPane.QUESTION_MESSAGE, null, medicamentsArray, medicamentsArray[0]);

                            if (selectedMedicament != null) {
                                JTextField fieldQuantite = new JTextField();
                                JTextField fieldDosage = new JTextField();
                                JTextField fieldFrequence = new JTextField();
                                JTextField fieldDuree = new JTextField();

                                Object[] message = {
                                        "Quantité:", fieldQuantite,
                                        "Dosage (ex: 1 comprimé):", fieldDosage,
                                        "Fréquence (ex: 3 fois par jour):", fieldFrequence,
                                        "Durée du traitement (en jours):", fieldDuree
                                };

                                int option = JOptionPane.showConfirmDialog(this, message, "Ajouter médicament", JOptionPane.OK_CANCEL_OPTION);
                                if (option == JOptionPane.OK_OPTION) {
                                    try {
                                        int quantite = Integer.parseInt(fieldQuantite.getText());
                                        String dosage = fieldDosage.getText();
                                        String frequence = fieldFrequence.getText();
                                        int duree = Integer.parseInt(fieldDuree.getText());

                                        ordonnance.ajouterMedicament(selectedMedicament, quantite, dosage, frequence, duree);

                                        int optionAjouter = JOptionPane.showConfirmDialog(this,
                                                "Voulez-vous ajouter un autre médicament?",
                                                "Ajouter un autre", JOptionPane.YES_NO_OPTION);

                                        ajouterAutre = (optionAjouter == JOptionPane.YES_OPTION);
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(this, "Valeur numérique invalide");
                                    }
                                } else {
                                    ajouterAutre = false;
                                }
                            } else {
                                ajouterAutre = false;
                            }
                        }

                        // Validate and finalize the sale
                        if (!ordonnance.getLignes().isEmpty()) {
                            if (service.validerOrdonnance(ordonnance)) {
                                service.vendreOrdonnance(ordonnance);
                                refreshOrdonnanceList(listModel);
                                JOptionPane.showMessageDialog(this, "Vente effectuée avec succès.");
                            } else {
                                JOptionPane.showMessageDialog(this,
                                        "La vente n'a pas pu être validée. Vérifiez le stock des médicaments.",
                                        "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Format de date invalide: " + ex.getMessage());
                    }
                }
            }
        });

        // View sale details button action
        btnView.addActionListener(e -> {
            Ordonnance selectedOrdonnance = ordonnanceList.getSelectedValue();
            if (selectedOrdonnance != null) {
                JTextArea textArea = new JTextArea(selectedOrdonnance.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane1 = new JScrollPane(textArea);
                scrollPane1.setPreferredSize(new Dimension(500, 400));

                JOptionPane.showMessageDialog(this, scrollPane1,
                        "Détails de la vente #" + selectedOrdonnance.getId(),
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une vente.");
            }
        });
    }

    private void refreshOrdonnanceList(DefaultListModel<Ordonnance> model) {
        model.clear();
        for (Ordonnance ordonnance : service.listerVentes()) {
            model.addElement(ordonnance);
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(buttonPink);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
    }
}