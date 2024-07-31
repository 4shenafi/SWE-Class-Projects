import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddEditEmployeePage extends JPanel {
    private JFrame parentFrame;
    private JTextField idField, nameField, emailField, phoneField, addressField, positionField, salaryField;
    private JComboBox<String> genderComboBox, departmentComboBox;
    private JComboBox<Double> ratingComboBox;
    private JButton saveButton, cancelButton;
    private Integer employeeId;

    public AddEditEmployeePage(JFrame parentFrame, Integer employeeId) {
        this.parentFrame = parentFrame;
        this.employeeId = employeeId;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Styling the fields and labels
        Font labelFont = new Font("Tahoma", Font.BOLD, 12);
        Font fieldFont = new Font("Tahoma", Font.PLAIN, 12);

        // ID Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createStyledLabel("ID:", labelFont), gbc);
        idField = createStyledTextField(fieldFont);
        idField.setEnabled(false);
        gbc.gridx = 1;
        add(idField, gbc);

        // Name Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(createStyledLabel("Name:", labelFont), gbc);
        nameField = createStyledTextField(fieldFont);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Email Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(createStyledLabel("Email:", labelFont), gbc);
        emailField = createStyledTextField(fieldFont);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Phone Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(createStyledLabel("Phone:", labelFont), gbc);
        phoneField = createStyledTextField(fieldFont);
        gbc.gridx = 1;
        add(phoneField, gbc);

        // Address Field
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(createStyledLabel("Address:", labelFont), gbc);
        addressField = createStyledTextField(fieldFont);
        gbc.gridx = 1;
        add(addressField, gbc);

        // Gender Combo Box
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(createStyledLabel("Gender:", labelFont), gbc);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        gbc.gridx = 1;
        add(genderComboBox, gbc);

        // Position Field
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(createStyledLabel("Position:", labelFont), gbc);
        positionField = createStyledTextField(fieldFont);
        gbc.gridx = 1;
        add(positionField, gbc);

        // Salary Field
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(createStyledLabel("Salary:", labelFont), gbc);
        salaryField = createStyledTextField(fieldFont);
        gbc.gridx = 1;
        add(salaryField, gbc);

        // Department Combo Box
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(createStyledLabel("Department:", labelFont), gbc);
        departmentComboBox = new JComboBox<>(new String[]{"HR", "IT", "Finance", "Marketing"});
        gbc.gridx = 1;
        add(departmentComboBox, gbc);

        // Performance Rating Combo Box
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(createStyledLabel("Performance Rating:", labelFont), gbc);
        ratingComboBox = new JComboBox<>(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0});
        gbc.gridx = 1;
        add(ratingComboBox, gbc);

        // Buttons
        saveButton = createStyledButton("Save", new Color(85, 115, 211));
        cancelButton = createStyledButton("Cancel", new Color(211, 85, 85));

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);

        if (employeeId != null) {
            loadEmployeeData(employeeId);
            idField.setEnabled(false); // Disable ID field when editing an employee
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.getContentPane().removeAll();
                parentFrame.add(new EmployeeListPage(parentFrame));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JTextField createStyledTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setBorder(BorderFactory.createCompoundBorder(
                textField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return textField;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private void loadEmployeeData(int employeeId) {
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                idField.setText(String.valueOf(resultSet.getInt("id")));
                nameField.setText(resultSet.getString("name"));
                emailField.setText(resultSet.getString("email"));
                phoneField.setText(resultSet.getString("phone"));
                addressField.setText(resultSet.getString("address"));
                genderComboBox.setSelectedItem(resultSet.getString("gender"));
                positionField.setText(resultSet.getString("position"));
                salaryField.setText(String.valueOf(resultSet.getDouble("salary")));
                departmentComboBox.setSelectedItem(resultSet.getString("department"));
                ratingComboBox.setSelectedItem(resultSet.getDouble("performance_rating"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveEmployee() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String position = positionField.getText();
        String department = (String) departmentComboBox.getSelectedItem();
        double salary = Double.parseDouble(salaryField.getText());
        double rating = (double) ratingComboBox.getSelectedItem();

        try {
            Connection connection = getConnection();
            PreparedStatement statement;

            if (employeeId == null) {
                String query = "INSERT INTO employees (name, email, phone, address, gender, position, department, salary, performance_rating) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, phone);
                statement.setString(4, address);
                statement.setString(5, gender);
                statement.setString(6, position);
                statement.setString(7, department);
                statement.setDouble(8, salary);
                statement.setDouble(9, rating);
            } else {
                String query = "UPDATE employees SET name = ?, email = ?, phone = ?, address = ?, gender = ?, " +
                        "position = ?, department = ?, salary = ?, performance_rating = ? WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, phone);
                statement.setString(4, address);
                statement.setString(5, gender);
                statement.setString(6, position);
                statement.setString(7, department);
                statement.setDouble(8, salary);
                statement.setDouble(9, rating);
                statement.setInt(10, employeeId);
            }

            statement.executeUpdate();
            connection.close();

            parentFrame.getContentPane().removeAll();
            parentFrame.add(new EmployeeListPage(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Company";
        String user = "root";
        String password = "@Anti7452";
        return DriverManager.getConnection(url, user, password);
    }
}
