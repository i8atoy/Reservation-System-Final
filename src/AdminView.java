import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminView extends JFrame {
    private JButton confirm;
    private JButton deny;
    private JTable memberListTable;
    private JTable pendingReservationsTable;
    private JTable confirmedReservationsTable;
    private JButton logout;
    private JButton bandManager;
    private JButton refresh;

    public AdminView() {
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
        setTitle("Admin View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        memberListTable = createStyledTable(new DefaultTableModel());
        JScrollPane listScrollPane = new JScrollPane(memberListTable);
        listScrollPane.setBorder(createTitledBorder("User List"));

        logout = createStyledButton("Logout", Color.red);
        refresh = createStyledButton("Refresh", new Color(100, 149, 237));


        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.add(listScrollPane, BorderLayout.CENTER);
        leftPanel.add(logout, BorderLayout.SOUTH);
        leftPanel.add(refresh, BorderLayout.NORTH);

        pendingReservationsTable = createStyledTable(new DefaultTableModel());
        JScrollPane topScrollPane = new JScrollPane(pendingReservationsTable);
        topScrollPane.setBorder(createTitledBorder("Pending Reservations"));

        confirmedReservationsTable = createStyledTable(new DefaultTableModel());
        JScrollPane bottomScrollPane = new JScrollPane(confirmedReservationsTable);
        bottomScrollPane.setBorder(createTitledBorder("Confirmed Reservations"));

        JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topScrollPane, bottomScrollPane);
        verticalSplit.setResizeWeight(0.6);

        confirm = createStyledButton("Confirm Reservation", new Color(50, 205, 50));
        deny = createStyledButton("Deny Reservation", new Color(255, 69, 0));
        bandManager = createStyledButton("Band Manager", new Color(100, 149, 237));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(confirm);
        buttonPanel.add(deny);
        buttonPanel.add(bandManager);

        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(verticalSplit, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.3);
        add(splitPane, BorderLayout.CENTER);

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
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        button.setPreferredSize(new Dimension(200, 40));
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

    public JButton getConfirm() {
        return confirm;
    }

    public JButton getDeny() {
        return deny;
    }

    public JTable getMemberListTable() {
        return memberListTable;
    }

    public JTable getPendingReservationsTable() {
        return pendingReservationsTable;
    }

    public JTable getConfirmedReservationsTable() {
        return confirmedReservationsTable;
    }
    public JButton getLogout() {
        return logout;
    }
    public JButton getBandManager() {
        return bandManager;
    }
    public JButton getRefresh() {
        return refresh;
    }

}
