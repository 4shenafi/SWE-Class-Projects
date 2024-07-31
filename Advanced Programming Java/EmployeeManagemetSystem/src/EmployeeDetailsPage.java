import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EmployeeDetailsPage extends JPanel {
    private JFrame parentFrame;
    private int employeeId;
    private JTextField idField, nameField, emailField, phoneField, addressField, genderField, positionField,
            departmentField, salaryField, ratingField;

    public EmployeeDetailsPage(JFrame parentFrame, int employeeId) {
        this.parentFrame = parentFrame;
        this.employeeId = employeeId;
        setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize components
        initializeComponents();

        // Add components to detailsPanel using GridBagConstraints
        addLabelAndTextField(detailsPanel, gbc, "ID:", idField);
        addLabelAndTextField(detailsPanel, gbc, "Name:", nameField);
        addLabelAndTextField(detailsPanel, gbc, "Email:", emailField);
        addLabelAndTextField(detailsPanel, gbc, "Phone:", phoneField);
        addLabelAndTextField(detailsPanel, gbc, "Address:", addressField);
        addLabelAndTextField(detailsPanel, gbc, "Gender:", genderField);
        addLabelAndTextField(detailsPanel, gbc, "Position:", positionField);
        addLabelAndTextField(detailsPanel, gbc, "Department:", departmentField);
        addLabelAndTextField(detailsPanel, gbc, "Salary:", salaryField);
        addLabelAndTextField(detailsPanel, gbc, "Performance Rating:", ratingField);

        JButton backButton = new JButton("Back to Employee List");
        backButton.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new EmployeeListPage(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        add(backButton, BorderLayout.SOUTH);

        // Fetch and display employee details
        fetchEmployeeDetails();

        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void fetchEmployeeDetails() {
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Set text values
                idField.setText(String.valueOf(employeeId));
                nameField.setText(resultSet.getString("name"));
                emailField.setText(resultSet.getString("email"));
                phoneField.setText(resultSet.getString("phone"));
                addressField.setText(resultSet.getString("address"));
                genderField.setText(resultSet.getString("gender"));
                positionField.setText(resultSet.getString("position"));
                departmentField.setText(resultSet.getString("department"));
                salaryField.setText(String.valueOf(resultSet.getDouble("salary")));
                ratingField.setText(String.valueOf(resultSet.getDouble("performance_rating")));
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Employee details not found.");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Error fetching employee details.");
        }
    }

    private void initializeComponents() {
        idField = new JTextField(20);
        idField.setEditable(false);
        nameField = new JTextField(20);
        nameField.setEditable(false);
        emailField = new JTextField(20);
        emailField.setEditable(false);
        phoneField = new JTextField(20);
        phoneField.setEditable(false);
        addressField = new JTextField(20);
        addressField.setEditable(false);
        genderField = new JTextField(20);
        genderField.setEditable(false);
        positionField = new JTextField(20);
        positionField.setEditable(false);
        departmentField = new JTextField(20);
        departmentField.setEditable(false);
        salaryField = new JTextField(20);
        salaryField.setEditable(false);
        ratingField = new JTextField(20);
        ratingField.setEditable(false);
    }

    private void addLabelAndTextField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);

        gbc.gridy++;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/company";
        String user = "root";
        String password = "@Anti7452"; // replace with your actual database password
        return DriverManager.getConnection(url, user, password);
    }
}
