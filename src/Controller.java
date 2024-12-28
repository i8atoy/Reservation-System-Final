import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;

public class Controller {
    private View view;
    private Model model;
    private static UserDetails userDetails;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;


        initializeListeners();
    }

    private void initializeListeners() {
        //action listener to login button
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get username and password from view
                String username = view.getUsernameField().getText();
                String password = new String(view.getPasswordField().getPassword());
                //init user details
                userDetails = model.getUserDetails(username);

                // Verify login through model
                if (model.verifyLogin(username, password)) {
                    JOptionPane.showMessageDialog(view, "Login Successful!");

                    switch (userDetails.getRole().toUpperCase()){
                        case "ADMIN":
                            view.close();
                            AdminView adminView = new AdminView();
                            loadAdminData(adminView);
                            initializeAdminListeners(adminView);
                            break;
                        case "MEMBER":
                            //closes and disposes of initial login view
                            view.close();
                            ArrayList<Musician> musicians = new ArrayList<>();
                            Band band = new Band(musicians);
                            MemberView memberView = new MemberView();
                            loadTableData(memberView);
                            initializeMemberListeners(memberView, band);
                            break;
                        default:
                            JOptionPane.showMessageDialog(view, "Cannot process role or invalid role");
                            break;
                    }

                } else {
                    JOptionPane.showMessageDialog(view,
                            "Invalid username or password",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        view.getRegisterButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegisterView registerView = new RegisterView();
                initializeRegisterListeners(registerView);
            }
        });
    }

    private void initializeRegisterListeners(RegisterView registerView) {



        registerView.getRegisterButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = registerView.getUsernameField().getText();
                String firstName = registerView.getFirstNameField().getText();
                String lastName = registerView.getLastNameField().getText();
                String password =  new String(registerView.getPasswordField().getPassword());
                String confirmPassword = new String(registerView.getConfirmPasswordField().getPassword());

                if(password.equals(confirmPassword)) {
                    boolean success = model.register(username, password, firstName, lastName);
                    if(success) {
                        JOptionPane.showMessageDialog(registerView, "Registration Successful!");
                        registerView.dispose();
                    }else{
                        JOptionPane.showMessageDialog(registerView, "Registration Failed!");
                    }
                }else{
                    JOptionPane.showMessageDialog(registerView, "Passwords do not match");
                }
            }


        });
    }

    public void loadTableData(MemberView memberView) {
        DefaultTableModel musiciansData = model.getTableData("SELECT name, instrument, rating FROM musicians");
        memberView.getMusicians().setModel(musiciansData);
        ArrayList<ReservationDate> reservationDates = model.initializeReservedDate();
        updateAvailableDates(memberView, reservationDates);
    }

    private void initializeMemberListeners(MemberView memberView, Band band) {
        memberView.getConfirmMusician().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = memberView.getMusicians().getSelectedRow();
                if (selectedRow != -1) {

                    String name = (String) memberView.getMusicians().getValueAt(selectedRow, 0);
                    String instrument = (String) memberView.getMusicians().getValueAt(selectedRow, 1);
                    int rating = (int) memberView.getMusicians().getValueAt(selectedRow, 2);

                    Musician musician = new Musician(name, instrument, rating);

                    band.addMusician(musician);
                    //updates every button press.
                    updateUserBandTable(memberView, band);
                }
            }
        });
        memberView.getDeleteMusician().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = memberView.getUserBand().getSelectedRow();
                if (selectedRow != -1) {

                    String name = (String) memberView.getMusicians().getValueAt(selectedRow, 0);
                    String instrument = (String) memberView.getMusicians().getValueAt(selectedRow, 1);
                    int rating = (int) memberView.getMusicians().getValueAt(selectedRow, 2);

                    //references same musician created when adding
                    Musician musician = band.getMusicians().get(selectedRow);

                    band.deleteMusician(musician);

                    updateUserBandTable(memberView, band);
                }
            }
        });
        memberView.getConfirmBothTables().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isBandValid = band.isValidBand(band.getBandRating(), band.getMusicians());
                JTable availableDatesTable = memberView.getAvailableDates();
                int selectedRow = availableDatesTable.getSelectedRow();

                if(!isBandValid){
                    JOptionPane.showMessageDialog(memberView, "Band is not valid.. Try again");
                    return;
                }

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(memberView, "Please select a date.");
                    return;
                }

                String dateString = availableDatesTable.getValueAt(selectedRow, 0).toString();
                String availability = availableDatesTable.getValueAt(selectedRow, 1).toString();

                LocalDate selectedDate = LocalDate.parse(dateString);

                if ("Yes".equalsIgnoreCase(availability)) {
                    boolean succes = model.insertPendingReservations(userDetails.getId(), selectedDate, band);
                    if(succes) {
                        JOptionPane.showMessageDialog(memberView, "Reservation confirmed");
                        loadTableData(memberView);
                    }else{
                        JOptionPane.showMessageDialog(memberView, "Reservation failed");
                    }
                } else {
                    JOptionPane.showMessageDialog(memberView, "The selected date " + selectedDate + " is already reserved. Please choose another date.");
                    return;
                }

            }

        });
        memberView.getSeeReservation().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SeeReservationsView seeReservationsView = new SeeReservationsView();
                seeReservationsView.getReservationsTable().setModel(model.getTableData("SELECT username, set_date FROM users join reservations on users.id = reservations.user_id WHERE status = 'confirmed'"));
            }
        });
        memberView.getLogout().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                View view = new View();
                Controller controller = new Controller(view, model);
                memberView.dispose();
            }
        });
        memberView.getMyReservations().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyReservationsView myReservationsView = new MyReservationsView();
                DefaultTableModel temp = model.loadMyReservation(userDetails.getId());
                myReservationsView.getMyReservationsTable().setModel(temp);
            }
        });


    }

    private static void updateUserBandTable(MemberView memberView, Band band) {
        DefaultTableModel bandModel = new DefaultTableModel();
        bandModel.addColumn("Name");
        bandModel.addColumn("Instrument");
        bandModel.addColumn("Rating");


        for (Musician musician : band.getMusicians()) {
            bandModel.addRow(new Object[]{
                    musician.getName(),
                    musician.getInstrument(),
                    musician.getRating()
            });
        }

        memberView.getUserBand().setModel(bandModel);
    }

    private static void updateAvailableDates(MemberView memberview, ArrayList<ReservationDate> reservationDates) {
        DefaultTableModel dateModel = new DefaultTableModel();
        dateModel.addColumn("Date");
        dateModel.addColumn("Available");

        for (ReservationDate reservationDate : reservationDates) {
            Object[] row = {
                    reservationDate.getReservationDate(),
                    reservationDate.getReserved() ? "No" : "Yes"
            };
            dateModel.addRow(row);
        }

        memberview.getAvailableDates().setModel(dateModel);
    }
    private void loadAdminData(AdminView adminView) {
        //get all users
        DefaultTableModel membersData = model.getTableData("SELECT username, role FROM users");
        adminView.getMemberListTable().setModel(membersData);

        //confirmed reservations
        DefaultTableModel confirmedReservationsData = model.getTableData("SELECT username, set_date FROM users join reservations on users.id = reservations.user_id where status = 'confirmed'");
        adminView.getConfirmedReservationsTable().setModel(confirmedReservationsData);

        //pending
        DefaultTableModel pendingReservationsData = model.getTableData("SELECT reservations.id, username, set_date, musicians, band_rating FROM users join reservations on users.id = reservations.user_id where status = 'pending'");
        adminView.getPendingReservationsTable().setModel(pendingReservationsData);
    }
    private void initializeAdminListeners(AdminView adminView) {
        adminView.getConfirm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected_row = adminView.getPendingReservationsTable().getSelectedRow();
                if (selected_row != -1) {
                    int reservation_id = (int) adminView.getPendingReservationsTable().getValueAt(selected_row, 0);
                    java.sql.Date SQLdate = (java.sql.Date) adminView.getPendingReservationsTable().getValueAt(selected_row, 2);
                    LocalDate date = SQLdate.toLocalDate();
                    boolean success = model.confirmReservation(reservation_id, date);
                    if(success) {
                        JOptionPane.showMessageDialog(adminView, "Reservation confirmed");
                        loadAdminData(adminView);
                    }else{
                        JOptionPane.showMessageDialog(adminView, "Reservation confirmation failed");
                    }
                }else{
                    JOptionPane.showMessageDialog(adminView, "Please select a reservation.");
                }
            }
        });
        adminView.getDeny().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selected_row = adminView.getPendingReservationsTable().getSelectedRow();
                if (selected_row != -1) {
                    int user_id = (int) adminView.getPendingReservationsTable().getValueAt(selected_row, 0);
                    java.sql.Date SQLdate = (java.sql.Date) adminView.getPendingReservationsTable().getValueAt(selected_row, 2);
                    LocalDate date = SQLdate.toLocalDate();
                    boolean success = model.rejectReservation(user_id, date);
                    if(success) {
                        JOptionPane.showMessageDialog(adminView, "Reservation rejected");
                        loadAdminData(adminView);
                    }else{
                        JOptionPane.showMessageDialog(adminView, "Reservation rejection failed");
                    }
                }else{
                    JOptionPane.showMessageDialog(adminView, "Please select a reservation.");
                }
            }
        });
        adminView.getLogout().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminView.dispose();
                View view = new View();
                Controller controller = new Controller(view, model);
            }
        });
        adminView.getBandManager().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemberView memberView = new MemberView();
                ArrayList<Musician> musicians = new ArrayList<>();
                Band band = new Band(musicians);
                loadTableData(memberView);
                initializeMemberListeners(memberView, band);
            }
        });
        adminView.getRefresh().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAdminData(adminView);
            }
        });

    }


}