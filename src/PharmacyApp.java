import ui.PharmacieGUI;

public class PharmacyApp {
    public static void main(String[] args) {

        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        java.awt.EventQueue.invokeLater(() -> {
            PharmacieGUI app = new PharmacieGUI();
            app.setVisible(true);
        });
    }
}