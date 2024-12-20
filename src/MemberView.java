import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MemberView extends JFrame {
    private JTable musicians;
    private JTable userBand;
    private JTable availableDates;
    private JButton confirmMusician;
    private JButton confirm;
    private JButton deleteMusician;
    private JButton seeReservation;

    public MemberView() {
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Database Selection View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultTableModel firstModel = new DefaultTableModel();
        DefaultTableModel secondModel = new DefaultTableModel();
        DefaultTableModel thirdModel = new DefaultTableModel();

        musicians = new JTable(firstModel);
        userBand = new JTable(secondModel);
        availableDates = new JTable(thirdModel);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        tablesPanel.add(new JScrollPane(musicians));
        tablesPanel.add(new JScrollPane(userBand));
        tablesPanel.add(new JScrollPane(availableDates));


        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));





        confirmMusician = new JButton("Confirm Musician");
        confirm = new JButton("Confirm Your Band and Date");
        deleteMusician = new JButton("Delete Musician");
        seeReservation = new JButton("See Reservations");


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(0, 0, 0, 10);

        confirmMusician.setBackground(Color.GREEN);
        confirmMusician.setForeground(Color.BLACK);
        buttonPanel.add(confirmMusician, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(0, 10, 0, 0);

        deleteMusician.setBackground(Color.RED);
        deleteMusician.setForeground(Color.BLACK);
        buttonPanel.add(deleteMusician, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.6;
        gbc.insets = new Insets(0, 0, 0, 0);

        confirm.setBackground(Color.cyan);
        buttonPanel.add(confirm, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(0, 0, 0, 10);

        seeReservation.setBackground(Color.CYAN);
        buttonPanel.add(seeReservation, gbc);

        add(tablesPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JTable getMusicians() {
        return musicians;
    }

    public JTable getUserBand() {
        return userBand;
    }

    public JTable getAvailableDates() {
        return availableDates;
    }

    public JButton getConfirmMusician() {
        return confirmMusician;
    }

    public JButton getConfirmBothTables() {
        return confirm;
    }

    public JButton getDeleteMusician() {
        return deleteMusician;
    }

    public JButton getSeeReservation() {
        return seeReservation;
    }
}