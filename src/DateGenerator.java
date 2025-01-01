import java.time.*;
import java.util.*;

public class DateGenerator {
    private final int numberOfDays;
    private final ArrayList<LocalDate> dates;

    public DateGenerator() {
        LocalDate today = LocalDate.now();
        numberOfDays = 366 - today.getDayOfYear();
        dates = new ArrayList<>(numberOfDays);

        for (int i = 0; i < numberOfDays; i++) {
            LocalDate day = today.plusDays(i);
            dates.add(day);
        }
    }

    public ArrayList<LocalDate> getDates() {
        return dates;
    }

}
