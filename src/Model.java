import java.sql.*;

public class Model {
    private static Connection connection;

    // Constructor to establish database connection
    public Model() {
        connectToDatabase();
    }

    // Method to connect to PostgreSQL database
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
        String sql = "SELECT ua.password, u.role " +
                "FROM users u " +
                "JOIN user_auth ua ON u.username = ua.username " +
                "WHERE u.username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Retrieve the stored hashed password
                String storedHash = rs.getString("password");

                // Use your PasswordAuthentication to verify the password
                PasswordAuthentication passwordAuth = new PasswordAuthentication();
                return passwordAuth.authenticate(password.toCharArray(), storedHash);
            }
            return false; // User not found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserRole(String username) {
        String sql = "SELECT role FROM users WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
            return null; // User not found
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}