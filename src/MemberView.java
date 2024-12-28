import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    private JButton myReservations;
    private JButton logout;

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
        setTitle("Member View");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultTableModel firstModel = new DefaultTableModel();
        DefaultTableModel secondModel = new DefaultTableModel();
        DefaultTableModel thirdModel = new DefaultTableModel();

        musicians = createStyledTable(firstModel);
        userBand = createStyledTable(secondModel);
        availableDates = createStyledTable(thirdModel);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane musiciansScrollPane = new JScrollPane(musicians);
        JScrollPane userBandScrollPane = new JScrollPane(userBand);
        JScrollPane availableDatesScrollPane = new JScrollPane(availableDates);

        musiciansScrollPane.setBorder(createTitledBorder("Musicians"));
        userBandScrollPane.setBorder(createTitledBorder("User Band"));
        availableDatesScrollPane.setBorder(createTitledBorder("Available Dates"));

        tablesPanel.add(musiciansScrollPane);
        tablesPanel.add(userBandScrollPane);
        tablesPanel.add(availableDatesScrollPane);

        add(tablesPanel, BorderLayout.CENTER);

        confirmMusician = createStyledButton("Confirm Musician", new Color(50, 205, 50));
        deleteMusician = createStyledButton("Delete Musician", new Color(255, 69, 0));
        confirm = createStyledButton("Confirm Band and Date", new Color(70, 130, 180));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(confirmMusician);
        bottomPanel.add(deleteMusician);
        bottomPanel.add(confirm);

        add(bottomPanel, BorderLayout.SOUTH);

        seeReservation = createStyledButton("See Reservations", new Color(100, 149, 237));
        myReservations = createStyledButton("My Reservations", new Color(100, 149, 237));
        logout = createStyledButton("Logout", new Color(178, 34, 34));

        JPanel sidePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        sidePanel.add(seeReservation);
        sidePanel.add(myReservations);
        sidePanel.add(logout);

        JPanel sideWrapper = new JPanel(new BorderLayout());
        sideWrapper.add(sidePanel, BorderLayout.NORTH);
        sideWrapper.setPreferredSize(new Dimension(200, 0));

        add(sideWrapper, BorderLayout.EAST);

        setSize(1200, 600);
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
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setSelectionForeground(Color.BLACK);
        return table;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return button;
    }

    private TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(25, 25, 112)
        );
        border.setTitlePosition(TitledBorder.ABOVE_TOP);
        return border;
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

    public JButton getMyReservations() {
        return myReservations;
    }

    public JButton getLogout() {
        return logout;
    }

}
