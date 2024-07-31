import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportPage extends JPanel {
    private JFrame parentFrame;
    private JTextArea reportTextArea;

    public ReportPage(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Admin Reports", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0)); // Add padding to the title label
        add(titleLabel, BorderLayout.NORTH);

        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font and size for the report text
        reportTextArea.setLineWrap(true);
        reportTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding to the scroll pane
        add(scrollPane, BorderLayout.CENTER);

        generateReport(); // Fetch data from database and generate report

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size for the button
        backButton.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new DashboardPage(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private void generateReport() {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("Report Date: ").append(getCurrentDate()).append("\n\n");

        try (Connection connection = getConnection()) {
            int totalEmployees = calculateTotalEmployees(connection);
            int totalDepartments = calculateTotalDepartments(connection);
            double averageSalary = calculateAverageSalary(connection);
            String topDepartments = getTopDepartmentsByEmployeeCount(connection);

            reportContent.append("Total Employees: ").append(totalEmployees).append("\n");
            reportContent.append("Total Departments: ").append(totalDepartments).append("\n");
            reportContent.append("Average Employee Salary: $").append(String.format("%.2f", averageSalary)).append("\n\n");
            reportContent.append("Top Departments by Employee Count:\n").append(topDepartments);

            reportTextArea.setText(reportContent.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            reportTextArea.setText("Error fetching data from the database.");
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return dateFormat.format(new Date());
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Company";
        String user = "root";
        String password = "@Anti7452";
        return DriverManager.getConnection(url, user, password);
    }

    private int calculateTotalEmployees(Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) AS totalEmployees FROM employees";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("totalEmployees");
            }
        }
        return 0;
    }

    private int calculateTotalDepartments(Connection connection) throws SQLException {
        String query = "SELECT COUNT(DISTINCT department) AS totalDepartments FROM employees";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("totalDepartments");
            }
        }
        return 0;
    }

    private double calculateAverageSalary(Connection connection) throws SQLException {
        String query = "SELECT AVG(salary) AS averageSalary FROM employees";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getDouble("averageSalary");
            }
        }
        return 0;
    }

    private String getTopDepartmentsByEmployeeCount(Connection connection) throws SQLException {
        String query = "SELECT department, COUNT(*) AS employeeCount FROM employees " +
                "GROUP BY department ORDER BY employeeCount DESC LIMIT 5";
        StringBuilder departments = new StringBuilder();
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            int rank = 1;
            while (resultSet.next()) {
                departments.append(rank++).append(". ")
                        .append(resultSet.getString("department")).append(": ")
                        .append(resultSet.getInt("employeeCount")).append(" employees\n");
            }
        }
        return departments.toString();
    }
}
