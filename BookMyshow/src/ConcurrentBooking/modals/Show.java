package ConcurrentBooking.modals;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Show {
    public final String id;
    public final String movieName;
    public final Screen screen;
    public final LocalDateTime startTime;

    public final Map<String, Seat> seatMap = new ConcurrentHashMap<>();
    public final Lock showLock = new ReentrantLock();

    public Show(String id, String movieName, Screen screen, LocalDateTime startTime) {
        this.id = id; this.movieName = movieName; this.screen = screen; this.startTime = startTime;
        // deep copy seats for show
        for (Map.Entry<String, Seat> e : screen.seats.entrySet()) {
            seatMap.put(e.getKey(), new Seat(e.getKey()));
        }
    }
}
