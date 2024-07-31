import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JPanel {
    private ImageIcon image;
    private Image backgroundImage;

    public WelcomePage() {
        image = new ImageIcon(getClass().getResource("/image.jpg"));
        backgroundImage = image.getImage();

        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Make contentPanel transparent

        JLabel titleLabel = new JLabel("Welcome to Employee Managment System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("white", Font.BOLD, 40));
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Welcome Page");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new WelcomePage());
                frame.setSize(800, 600); // Set a preferred size
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
