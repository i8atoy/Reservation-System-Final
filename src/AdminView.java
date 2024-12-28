import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminView extends JFrame {
    private JButton confirm;
    private JButton deny;
    private JTable memberListTable;
    private JTable pendingReservationsTable;
    private JTable confirmedReservationsTable;

    public AdminView() {
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Admin View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        memberListTable = new JTable(new DefaultTableModel());
        JScrollPane listScrollPane = new JScrollPane(memberListTable);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("User List"));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        pendingReservationsTable = new JTable(new DefaultTableModel());
        JScrollPane topScrollPane = new JScrollPane(pendingReservationsTable);
        topScrollPane.setBorder(BorderFactory.createTitledBorder("Pending reservations"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        confirm = new JButton("Confirm reservation");
        deny = new JButton("Deny reservation");
        buttonPanel.add(confirm);
        buttonPanel.add(deny);

        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        confirmedReservationsTable = new JTable(new DefaultTableModel());
        JScrollPane bottomScrollPane = new JScrollPane(confirmedReservationsTable);
        bottomScrollPane.setBorder(BorderFactory.createTitledBorder("Confirmed reservations"));

        JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topScrollPane, bottomScrollPane);
        verticalSplit.setResizeWeight(0.6);

        rightPanel.add(verticalSplit, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, rightPanel);
        splitPane.setResizeWeight(0.3);

        add(splitPane);
        setVisible(true);
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
}