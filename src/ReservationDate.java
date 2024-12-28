import java.time.LocalDate;

public class ReservationDate{
    private LocalDate reservationDate;
    private boolean reserved;


    public ReservationDate(LocalDate reservationDate, boolean isReserved){
        this.reservationDate = reservationDate;
        this.reserved = isReserved;

    }
    public LocalDate getReservationDate(){
        return reservationDate;
    }
    public boolean getReserved(){
        return reserved;
    }

    public void setReserved(boolean input) {
        reserved = input;
    }

}
