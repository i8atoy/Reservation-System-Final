import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Model {
    private static Connection connection;


    public Model() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:postgresql://localhost:5432/ReservationSystem";
            String user = "postgres";
            String password = "loldude12";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }

    public boolean verifyLogin(String username, String password) {
        String sql = "SELECT password " +
                "FROM user_auth " +
                "WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                String storedHash = rs.getString("password");

                PasswordAuthentication passwordAuth = new PasswordAuthentication();
                return passwordAuth.authenticate(password.toCharArray(), storedHash);
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserDetails getUserDetails(String username) {
        String sql = "SELECT id, role FROM users WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role");
                return new UserDetails(id, role);
            }
            return null; // User not found
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DefaultTableModel getMusicians(){
        String sql = "SELECT name, instrument, rating FROM musicians";
        DefaultTableModel model = new DefaultTableModel();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return model;
    }





}