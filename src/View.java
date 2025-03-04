import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class View extends JFrame {
    private JButton login;
    private JButton register; // New Register button
    private JTextField usernameField;
    private JPasswordField passwordField;

    public View() {
        initializeComponents();
    }

    private void initializeComponents() {

        BackgroundPanel backgroundPanel = new BackgroundPanel("D:\\Downloads\\jazzbackground.jpg");
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Title label
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align
        titleLabel.setForeground(Color.WHITE);

        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(15);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(15); // Adjust field size
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Login button
        login = new JButton("Login");
        login.setFont(new Font("Arial", Font.BOLD, 14));
        login.setBackground(Color.DARK_GRAY);
        login.setForeground(Color.WHITE);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Register button
        register = new JButton("Register");
        register.setFont(new Font("Arial", Font.BOLD, 14));
        register.setBackground(Color.GRAY);
        register.setForeground(Color.WHITE);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel
        backgroundPanel.add(titleLabel);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        backgroundPanel.add(usernameLabel);
        backgroundPanel.add(usernameField);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        backgroundPanel.add(passwordLabel);
        backgroundPanel.add(passwordField);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        backgroundPanel.add(login);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        backgroundPanel.add(register); // Add the new Register button

        setContentPane(backgroundPanel);
        setTitle("Login Form with Background");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Getters for controller
    public JButton getLoginButton() {
        return login;
    }

    public JButton getRegisterButton() { // Getter for the Register button
        return register;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String filePath) {
            try {
                backgroundImage = ImageIO.read(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Could not load background image.");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public void close() {
        dispose();
    }
}
