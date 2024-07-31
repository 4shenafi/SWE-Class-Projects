import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardPage extends JPanel {
    private JFrame parentFrame;
    private JPanel sidebarPanel;
    private JPanel contentPanel;

    public DashboardPage(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // Sidebar panel with buttons
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridLayout(12, 1, 15, 0));
        JButton employeeListButton = createStyledButton("Employee List");
        JButton addEmployeeButton = createStyledButton("Add Employee");
        JButton reportButton = createStyledButton("Reports");
        JButton logoutButton = new JButton("Logout");

        employeeListButton.setBackground(new Color(85, 115, 211));
        addEmployeeButton.setBackground(new Color(85, 115, 211));
        reportButton.setBackground(new Color(85, 115, 211));
        logoutButton.setBackground(Color.red);
        logoutButton.setForeground(Color.white);

        sidebarPanel.add(employeeListButton);
        sidebarPanel.add(addEmployeeButton);
        sidebarPanel.add(reportButton);
        sidebarPanel.add(logoutButton);

        // Content panel for displaying content
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new WelcomePage(), BorderLayout.CENTER);

        // Add sidebar and content panel to the main panel
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Button actions
        employeeListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayEmployeeList();
            }
        });

        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAddEmployee();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayReports();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(parentFrame,
                        "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    // Handle logout logic here, navigate to login page
                    parentFrame.getContentPane().removeAll();
                    parentFrame.add(new LoginPage(parentFrame));
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(59, 89, 182));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(39, 66, 153));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(85, 115, 211));
            }
        });

        return button;
    }

    private void displayEmployeeList() {
        contentPanel.removeAll();
        contentPanel.add(new EmployeeListPage(parentFrame), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void displayAddEmployee() {
        contentPanel.removeAll();
        contentPanel.add(new AddEditEmployeePage(parentFrame, null), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void displayReports() {
        contentPanel.removeAll();
        contentPanel.add(new ReportPage(parentFrame), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Dashboard Page");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new DashboardPage(frame));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
