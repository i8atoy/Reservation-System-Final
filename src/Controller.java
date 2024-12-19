import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        // Initialize action listeners
        initializeListeners();
    }

    private void initializeListeners() {
        // Add action listener to login button
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get username and password from view
                String username = view.getUsernameField().getText();
                String password = new String(view.getPasswordField().getPassword());

                // Verify login through model
                if (model.verifyLogin(username, password)) {
                    JOptionPane.showMessageDialog(view, "Login Successful!");
                    String userRole = model.getUserRole(username);

                    switch (userRole.toUpperCase()){
                        case "ADMIN":
                            JOptionPane.showMessageDialog(view, "You are an administrator!");
                            //new AdminView();
                            break;
                        case "MEMBER":
                            JOptionPane.showMessageDialog(view, "You are a member!");
                            new UserView();
                            break;
                        default:
                            JOptionPane.showMessageDialog(view, "Cannot process role or invalid role");
                            break;
                    }

                } else {
                    JOptionPane.showMessageDialog(view,
                            "Invalid username or password",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}