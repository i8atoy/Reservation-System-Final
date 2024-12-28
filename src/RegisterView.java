import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;

    public RegisterView() {
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        JLabel usernameLabel = createStyledLabel("Username:", labelFont);
        JLabel firstNameLabel = createStyledLabel("First Name:", labelFont);
        JLabel lastNameLabel = createStyledLabel("Last Name:", labelFont);
        JLabel passwordLabel = createStyledLabel("Password:", labelFont);
        JLabel confirmPasswordLabel = createStyledLabel("Confirm Password:", labelFont);

        usernameField = createStyledTextField(fieldFont);
        firstNameField = createStyledTextField(fieldFont);
        lastNameField = createStyledTextField(fieldFont);
        passwordField = createStyledPasswordField(fieldFont);
        confirmPasswordField = createStyledPasswordField(fieldFont);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        registerButton.setBackground(new Color(70, 130, 180)); // Steel blue
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(new RoundedBorder(10));

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        mainPanel.add(firstNameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        mainPanel.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        mainPanel.add(lastNameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        mainPanel.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        mainPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        mainPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, gbc);

        setVisible(true);
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(new Color(25, 25, 112));
        return label;
    }

    private JTextField createStyledTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private JPasswordField createStyledPasswordField(Font font) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(font);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return passwordField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getFirstNameField() {
        return firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    static class RoundedBorder extends AbstractBorder {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = radius + 1;
            return insets;
        }
    }
}
