package ConcurrentBooking.modals;

import java.util.LinkedHashMap;
import java.util.Map;

public class Screen {
    public final String id;
    public final Map<String, Seat> seats;

    public Screen(String id, int rows, int perRow) {
        this.id = id;
        this.seats = new LinkedHashMap<>();
        for (int r=0; r<rows; r++) {
            char row = (char)('A' + r);
            for (int c=1; c<=perRow; c++) {
                seats.put(row + "-" + c, new Seat(row + "-" + c));
            }
        }
    }
}
