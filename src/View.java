import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JButton login;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel Menu;

    public View() {
        setContentPane(Menu);
        setTitle("Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Getter methods for components
    public JButton getLoginButton() {
        return login;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}