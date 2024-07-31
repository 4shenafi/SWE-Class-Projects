import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel {
    private JFrame parentFrame;
    private int loginAttempts = 0;

    public LoginPage(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Set padding and spacing
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(new JLabel("Username:"), gbc);

        // Username field
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        add(usernameField, gbc);

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        // Password field
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(59, 89, 182));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(loginButton, gbc);

        // Add hover effect to the button
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(47, 79, 152));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(59, 89, 182));
            }
        });

        // Login button action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if ("java".equals(username) && "group3".equals(password)) {
                    // If successful, switch to the dashboard page
                    parentFrame.getContentPane().removeAll();
                    parentFrame.add(new DashboardPage(parentFrame));
                    parentFrame.revalidate();
                    parentFrame.repaint();
                } else {
                    // Increment login attempts
                    loginAttempts++;

                    if (loginAttempts >= 3) {
                        // Close the application
                        JOptionPane.showMessageDialog(parentFrame,
                                "Too many failed login attempts. Closing the application.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    } else {
                        // Show error message
                        JOptionPane.showMessageDialog(parentFrame,
                                "Invalid username or password. Attempts left: " + (3 - loginAttempts), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Adjust panel size
        setPreferredSize(new Dimension(400, 200));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Login Page");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new LoginPage(frame));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
