import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProjectFormPanel extends JPanel {

    // Fields
    private final JTextField txtProjectName = new JTextField(20);
    private final JTextField txtTeamLeader  = new JTextField(20);
    private final JTextField txtStartDate   = new JTextField(20);

    private final JComboBox<String> cmbTeamSize = new JComboBox<>(
            new String[]{"1-3", "4-6", "7-10", "10+"}
    );

    private final JComboBox<String> cmbProjectType = new JComboBox<>(
            new String[]{"Web", "Mobile", "Desktop", "API"}
    );

    // Buttons
    private final JButton btnSave  = new JButton("Save");
    private final JButton btnClear = new JButton("Clear");

    // Record time format (you can change if you want)
    private static final DateTimeFormatter RECORD_TIME_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ProjectFormPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0
        addRow(gbc, 0, "Project Name:", txtProjectName);

        // Row 1
        addRow(gbc, 1, "Team Leader:", txtTeamLeader);

        // Row 2
        addRow(gbc, 2, "Team Size:", cmbTeamSize);

        // Row 3
        addRow(gbc, 3, "Project Type:", cmbProjectType);

        // Row 4
        addRow(gbc, 4, "Start Date (DD/MM/YYYY):", txtStartDate);

        // Row 5 - Buttons
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(btnSave, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        add(btnClear, gbc);

        // Button Actions
        btnClear.addActionListener(e -> clearForm());

        btnSave.addActionListener(e -> handleSave());
    }

    private void handleSave() {
        if (!validateForm()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String projectName = txtProjectName.getText().trim();
        String teamLeader  = txtTeamLeader.getText().trim();
        String teamSize    = String.valueOf(cmbTeamSize.getSelectedItem());
        String projectType = String.valueOf(cmbProjectType.getSelectedItem());
        String startDate   = txtStartDate.getText().trim();

        String recordTime = LocalDateTime.now().format(RECORD_TIME_FMT);

        String entry =
                "=== Project Entry ===\n" +
                        "Project Name : " + projectName + "\n" +
                        "Team Leader : " + teamLeader + "\n" +
                        "Team Size : " + teamSize + "\n" +
                        "Project Type : " + projectType + "\n" +
                        "Start Date : " + startDate + "\n" +
                        "Record Time : " + recordTime + "\n" +
                        "====================\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("projects.txt", true))) {
            writer.write(entry);

            JOptionPane.showMessageDialog(
                    this,
                    "Saved successfully to projects.txt",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Optional: clear after save
            clearForm();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save. Error: " + ex.getMessage(),
                    "File I/O Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void addRow(GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        add(field, gbc);
    }

    private void clearForm() {
        txtProjectName.setText("");
        txtTeamLeader.setText("");
        txtStartDate.setText("");

        cmbTeamSize.setSelectedIndex(0);
        cmbProjectType.setSelectedIndex(0);
    }

    private boolean validateForm() {
        String projectName = txtProjectName.getText().trim();
        String teamLeader  = txtTeamLeader.getText().trim();
        String startDate   = txtStartDate.getText().trim();

        // combo boxes always have a selection in this setup
        return !projectName.isEmpty()
                && !teamLeader.isEmpty()
                && !startDate.isEmpty();
    }
}