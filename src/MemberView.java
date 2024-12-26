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
        initializeLookAndFeel();
        initializeComponents();
    }

    private void initializeLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeComponents() {
        setTitle("Database Selection View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultTableModel firstModel = new DefaultTableModel();
        DefaultTableModel secondModel = new DefaultTableModel();
        DefaultTableModel thirdModel = new DefaultTableModel();

        musicians = createStyledTable(firstModel);
        userBand = createStyledTable(secondModel);
        availableDates = createStyledTable(thirdModel);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tablesPanel.add(new JScrollPane(musicians));
        tablesPanel.add(new JScrollPane(userBand));
        tablesPanel.add(new JScrollPane(availableDates));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        confirmMusician = createStyledButton("Confirm Musician", Color.GREEN);
        deleteMusician = createStyledButton("Delete Musician", Color.RED);
        confirm = createStyledButton("Confirm Band and Date", Color.CYAN);
        seeReservation = createStyledButton("See Reservations", Color.CYAN);

        buttonPanel.add(confirmMusician);
        buttonPanel.add(deleteMusician);
        buttonPanel.add(confirm);
        buttonPanel.add(seeReservation);

        add(tablesPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(60, 63, 65));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setGridColor(Color.LIGHT_GRAY);
        return table;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        button.setPreferredSize(new Dimension(220, 40));
        return button;
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
