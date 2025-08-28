package ConcurrentBooking.modals;

import java.util.Set;

import ConcurrentBooking.constants.BookingStatus;

public class Booking {
    public final String bookingId;
    public final String userId;
    public final String showId;
    public final Set<String> seatIds;
    public final double amount;
    public BookingStatus status;
    public String txnId;

    public Booking(String bookingId, String userId, String showId, Set<String> seatIds, double amount) {
        this.bookingId = bookingId; this.userId = userId; this.showId = showId; this.seatIds = seatIds; this.amount = amount;
        this.status = BookingStatus.CONFIRMED;
    }

    @Override
    public String toString() {
        return String.format("Booking[%s] user=%s show=%s seats=%s amount=%.2f status=%s txn=%s",
                bookingId, userId, showId, seatIds, amount, status, txnId);
    }
}
