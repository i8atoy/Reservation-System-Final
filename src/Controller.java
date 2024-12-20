import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Controller {
    private View view;
    private Model model;


    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;


        initializeListeners();
    }

    private void initializeListeners() {
        //action listener to login button
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
                            //closes and disposes of initial login view
                            view.close();
                            List<Musician> musicians = new ArrayList<>();
                            Band band = new Band(musicians);
                            MemberView memberView = new MemberView();
                            loadTableData(memberView);
                            initializeMemberListeners(memberView, band);
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
    public void loadTableData(MemberView memberView) {


        DefaultTableModel musiciansData = model.getMusicians();
        memberView.getMusicians().setModel(musiciansData);

    }

    private static void initializeMemberListeners(MemberView memberView, Band band) {
        memberView.getConfirmMusician().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = memberView.getMusicians().getSelectedRow();
                if (selectedRow != -1) {

                    String name = (String) memberView.getMusicians().getValueAt(selectedRow, 0);
                    String instrument = (String) memberView.getMusicians().getValueAt(selectedRow, 1);
                    int rating = (int) memberView.getMusicians().getValueAt(selectedRow, 2);

                    Musician musician = new Musician(name, instrument, rating);

                    band.addMusician(musician);
                    //updates every button press.
                    updateUserBandTable(memberView, band);
                }
            }
        });
        memberView.getDeleteMusician().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = memberView.getUserBand().getSelectedRow();
                if (selectedRow != -1) {

                    String name = (String) memberView.getMusicians().getValueAt(selectedRow, 0);
                    String instrument = (String) memberView.getMusicians().getValueAt(selectedRow, 1);
                    int rating = (int) memberView.getMusicians().getValueAt(selectedRow, 2);

                    //references same musician created when adding
                    Musician musician = band.getMusicians().get(selectedRow);

                    band.deleteMusician(musician);

                    updateUserBandTable(memberView, band);
                }
            }
        });

    }

    private static void updateUserBandTable(MemberView memberView, Band band) {
        DefaultTableModel bandModel = new DefaultTableModel();
        bandModel.addColumn("Name");
        bandModel.addColumn("Instrument");
        bandModel.addColumn("Rating");


        for (Musician musician : band.getMusicians()) {
            bandModel.addRow(new Object[]{
                    musician.getName(),
                    musician.getInstrument(),
                    musician.getRating()
            });
        }

        memberView.getUserBand().setModel(bandModel);
    }
}