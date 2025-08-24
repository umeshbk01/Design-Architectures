package ZoomCarLLD.modals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ZoomCarLLD.constants.BookingStatus;

public class Booking {
    public final String bookingId;
    final User user;
    public final Vehicle vehicle;
    final Store store;
    public final LocalDateTime start;
    public final LocalDateTime end;
    public BookingStatus status;
    final double totalCost;
    public String transactionId; // from payment
    public Integer kmsDrivenDuringTrip = null; // set on completion

    public Booking(String bookingId, User user, Vehicle vehicle, Store store,
            LocalDateTime start, LocalDateTime end, double totalCost) {
        this.bookingId = bookingId;
        this.user = user;
        this.vehicle = vehicle;
        this.store = store;
        this.start = start;
        this.end = end;
        this.status = BookingStatus.CONFIRMED;
        this.totalCost = totalCost;
    }

    public void complete(int kmsDrivenDuringTrip) {
        if (status == BookingStatus.CONFIRMED) {
            this.status = BookingStatus.COMPLETED;
            this.kmsDrivenDuringTrip = kmsDrivenDuringTrip;
            this.vehicle.kmsDriven += kmsDrivenDuringTrip;
        }
    }

    public void cancel() {
        if (status == BookingStatus.CONFIRMED) {
            this.status = BookingStatus.CANCELLED;
        }
    }

    @Override
    public String toString() {
        return String.format("Booking#%s | User:%s | Vehicle:%s | Store:%s | From:%s | To:%s | Status:%s | Cost:%.2f | Txn:%s | kmsDuringTrip:%s",
                bookingId, user.name, vehicle.toString(), store.name, start, end, status, totalCost,
                transactionId == null ? "N/A" : transactionId,
                kmsDrivenDuringTrip == null ? "N/A" : kmsDrivenDuringTrip.toString());
    }
}
