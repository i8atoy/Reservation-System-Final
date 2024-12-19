import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MemberView extends JFrame {
    private JTable musicians;
    private JTable userBand;
    private JTable availableDates;
    private JButton confirmMusician;
    private JButton confirm;

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


        JPanel buttonPanel = new JPanel(new FlowLayout());
        confirmMusician = new JButton("Confirm Musician");
        confirm = new JButton("Confirm Your Band and Date");

        buttonPanel.add(confirmMusician);
        buttonPanel.add(confirm);


        add(tablesPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JTable getFirstTable() {
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
}