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
                            //new AdminView();
                            break;
                        case "MEMBER":
                            //closes and disposes of initial login view
                            view.close();
                            List<Musician> musicians = new ArrayList<>();
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
    }
    public void loadTableData(MemberView memberView) {
        DefaultTableModel musiciansData = model.getMusicians();
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
                }

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(memberView, "Please select a date.");
                    return;
                }

                String dateString = availableDatesTable.getValueAt(selectedRow, 0).toString();
                String availability = availableDatesTable.getValueAt(selectedRow, 1).toString();

                LocalDate selectedDate = LocalDate.parse(dateString);

                if ("Yes".equalsIgnoreCase(availability)) {
                    boolean succes = model.insertPendingReservations(userDetails.getId(), selectedDate);
                    if(succes) {
                        JOptionPane.showMessageDialog(memberView, "Reservation confirmed");
                    }else{
                        JOptionPane.showMessageDialog(memberView, "Reservation failed");
                    }
                } else {
                    JOptionPane.showMessageDialog(memberView, "The selected date " + selectedDate + " is already reserved. Please choose another date.");
                    return;
                }

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



}