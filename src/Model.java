import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

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

    public boolean register(String username, String password, String firstName, String lastName) {
        String usersSql = "INSERT INTO users (username, first_name, last_name, role) VALUES (?, ?, ?, 'Member')";
        String authSql = "INSERT INTO user_auth (username, password) VALUES (?, ?)";

        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

        String hashedPassword = passwordAuthentication.hash(password.toCharArray());

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement usersStmt = connection.prepareStatement(usersSql)) {
                usersStmt.setString(1, username);
                usersStmt.setString(2, firstName);
                usersStmt.setString(3, lastName);
                usersStmt.executeUpdate();
            }

            try (PreparedStatement authStmt = connection.prepareStatement(authSql)) {
                authStmt.setString(1, username);
                authStmt.setString(2, hashedPassword); // Consider hashing the password before storing
                authStmt.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {

                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    public ArrayList<ReservationDate> initializeReservedDate() {
        String sql = "Select set_date, status from reservations where set_date = ? ";
        DateGenerator dateGenerator = new DateGenerator();
        ArrayList<LocalDate> dates = dateGenerator.getDates();
        ArrayList<ReservationDate> reservationDates = new ArrayList<>();

        for (LocalDate date : dates) {
            boolean isReserved = false;
            String status = "pending";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setDate(1, java.sql.Date.valueOf(date));

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    status = rs.getString("status");

                    isReserved = "confirmed".equalsIgnoreCase(status);

                }

                reservationDates.add(new ReservationDate(date, isReserved));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reservationDates;
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

    public DefaultTableModel getTableData(String query) {
        String sql = query;
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


    public boolean insertPendingReservations(int user_id, LocalDate reservation_date, Band band) {
        String sql = "INSERT INTO reservations (user_id, set_date, musicians, band_rating) VALUES (?, ?, ?, ?)";
        boolean success = false;
        //status is by default pending
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, user_id);
            pstmt.setDate(2, java.sql.Date.valueOf(reservation_date));
            String musicians = "";
            for (int i = 0; i < band.getMusicians().size(); i++) {
                musicians += band.getMusicians().get(i).getName() + ", ";
            }
            pstmt.setString(3, musicians);
            pstmt.setInt(4, band.getBandRating());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return success;
    }

    public boolean confirmReservation(int reservation_id, LocalDate set_date) {
        String sql = "UPDATE reservations SET status = 'confirmed' WHERE id = ? AND set_date = ?";
        boolean success = false;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservation_id);
            pstmt.setDate(2, java.sql.Date.valueOf(set_date));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return success;
    }

    public boolean rejectReservation(int reservation_id, LocalDate set_date) {
        String sql = "UPDATE reservations SET status = 'rejected' WHERE id = ? AND set_date = ?";
        boolean success = false;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservation_id);
            pstmt.setDate(2, java.sql.Date.valueOf(set_date));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return success;
    }

    public DefaultTableModel loadMyReservation(int id) {
        String sql = "SELECT set_date, status, musicians, band_rating FROM reservations WHERE user_id = ?";
        DefaultTableModel model = new DefaultTableModel(new String[]{"Set Date", "Status", "Musicians", "Band Rating"}, 0);

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String setDate = rs.getString("set_date");
                    String status = rs.getString("status");
                    String musicians = rs.getString("musicians");
                    String bandRating = rs.getString("band_rating");

                    model.addRow(new Object[]{setDate, status, musicians, bandRating});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public DefaultTableModel loadReservations(LocalDate currentDate, String compare) {
        String sql = "SELECT username, set_date FROM users join reservations on users.id = reservations.user_id where status = 'confirmed' and set_date " + compare + "?";
        DefaultTableModel model = new DefaultTableModel(new String[]{"Username", "Reservation Date"}, 0);

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(currentDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String setString = rs.getString("Username");
                    java.util.Date setDate = rs.getDate("set_date");

                    model.addRow(new Object[]{setString, setDate});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean deleteReservation(LocalDate set_date) {
        String sql = "DELETE from reservations where set_date = ?";
        boolean success = false;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(set_date));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return success;
    }

}