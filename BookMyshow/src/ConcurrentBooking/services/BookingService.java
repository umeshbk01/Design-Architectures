package ConcurrentBooking.services;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import ConcurrentBooking.constants.BookingStatus;
import ConcurrentBooking.modals.Booking;

public class BookingService {
    private final SeatService seatService;
    private final PaymentGateway paymentGateway;
    private final Map<String, Booking> bookings = new ConcurrentHashMap<>();

    public BookingService(SeatService seatService, PaymentGateway paymentGateway) {
        this.seatService = seatService;
        this.paymentGateway = paymentGateway;
    }

    public Optional<Booking> book(String userId, String showId, Set<String> seatIds, String paymentRequestId, double amount){
        Optional<String> holdOpt = seatService.holdSeats(showId, userId, seatIds);
        if (holdOpt.isEmpty()) {
            System.out.println("[BookingService] Could not hold seats; aborting.");
            return Optional.empty();
        }
        String holdId = holdOpt.get();
        Optional<String> txnOpt = paymentGateway.processPayment(paymentRequestId, amount);
        if (txnOpt.isEmpty()) {
            // Payment failed -> release hold and abort
            System.out.println("[BookingService] Payment failed for request " + paymentRequestId + " -> releasing hold");
            seatService.releaseHold(holdId);
            return Optional.empty();
        }
        String txnId = txnOpt.get();

        // 3) Confirm hold => book seats
        boolean confirmed = seatService.confirmHold(holdId);
        if (!confirmed) {
            // seat confirmation failed (rare) -> refund (simulation) and abort
            System.out.println("[BookingService] Confirm hold failed after payment. Attempting compensation...");
            // In real world: trigger refund using txnId (not implemented here)
            return Optional.empty();
        }
        Booking booking = new Booking("bk-" + UUID.randomUUID(), userId, showId, seatIds, amount);
        booking.txnId = txnId;
        booking.status = BookingStatus.CONFIRMED;
        bookings.put(booking.bookingId, booking);
        System.out.println("[BookingService] Booking successful: " + booking);
        return Optional.of(booking);
    }

    public Optional<Booking> getBooking(String bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }
}