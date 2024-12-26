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

    public ArrayList<ReservationDate> initializeReservedDate(){
        String sql = "Select set_date from reservations where set_date = ? ";
        DateGenerator dateGenerator = new DateGenerator();
        ArrayList<LocalDate> dates = dateGenerator.getDates();
        ArrayList<ReservationDate> reservationDates = new ArrayList<>();

        for (LocalDate date : dates) {
            boolean isReserved = false;
            try(PreparedStatement pstmt = connection.prepareStatement(sql)){
                pstmt.setDate(1, java.sql.Date.valueOf(date));

                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    isReserved = true;
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

    public boolean insertPendingReservations(int user_id, LocalDate pending_date, ArrayList<Musician> musicians, int rating) {
        String sql = "INSERT INTO pending_reservations (user_id, pending_date, band_members, band_rating) values (?, ?, ?, ?)";
        boolean success = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, user_id);
            pstmt.setDate(2, java.sql.Date.valueOf(pending_date));
            String allMusicians = "";
            for(Musician m : musicians){
                allMusicians = allMusicians + m.getName() + ", ";
            }
            pstmt.setString(3, allMusicians);
            pstmt.setInt(4, rating);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }

        }catch (SQLException e) {
            e.printStackTrace();

        }
        return success;
    }
    public DefaultTableModel getReservations(){
        String sql = "SELECT username, set_date FROM users join reservations on users.id = reservations.user_id";
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