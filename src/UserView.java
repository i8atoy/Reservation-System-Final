import javax.swing.*;
import java.awt.*;

public class UserView extends JFrame {
    private JTable table;
    private JButton addMusicianToBandButton;
    private JTable table1;
    private JButton confirmBandButton;
    private JPanel userView;

    public UserView(){
        setContentPane(userView);
        setTitle("Member View ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
