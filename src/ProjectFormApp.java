import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ProjectFormApp {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Software Project Registration Form");
            frame.setSize(500, 450);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new ProjectFormPanel());

            frame.setVisible(true);
        });
    }
}