import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyReservationsView extends JFrame {
    private JTable reservationsTable;

    public MyReservationsView() {
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Reservations View");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


        DefaultTableModel tableModel = new DefaultTableModel();
        reservationsTable = new JTable(tableModel);
        reservationsTable.setRowHeight(30);
        reservationsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        reservationsTable.getTableHeader().setBackground(new Color(60, 63, 65));
        reservationsTable.getTableHeader().setForeground(Color.WHITE);
        reservationsTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        reservationsTable.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        add(scrollPane, BorderLayout.CENTER);


        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTable getMyReservationsTable() {
        return reservationsTable;
    }

}
