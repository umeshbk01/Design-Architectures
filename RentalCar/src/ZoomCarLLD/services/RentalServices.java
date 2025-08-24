package ZoomCarLLD.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import RentalCarLLD.Constants.PaymentMode;
import ZoomCarLLD.constants.BookingStatus;
import ZoomCarLLD.constants.PaymentStatus;
import ZoomCarLLD.constants.VehicleStatus;
import ZoomCarLLD.modals.BikeInventory;
import ZoomCarLLD.modals.Booking;
import ZoomCarLLD.modals.CarInventory;
import ZoomCarLLD.modals.InventoryManagement;
import ZoomCarLLD.modals.Location;
import ZoomCarLLD.modals.Payment;
import ZoomCarLLD.modals.Store;
import ZoomCarLLD.modals.User;
import ZoomCarLLD.modals.Vehicle;

public class RentalServices {
    private final CarInventory carInventory;
    private final BikeInventory bikeInventory;
    private final List<Booking> bookings;

    public RentalServices() {
        this.carInventory = new CarInventory();
        this.bikeInventory = new BikeInventory();
        this.bookings = new ArrayList<>();
    }

    public List<Vehicle> searchAvailableVehicles(Location location, LocalDateTime startDate, LocalDateTime endDate) {
        List<Vehicle> available = new ArrayList<>();
        for (Store store : location.getStores()) {
            for (Vehicle v : store.getVehicles()) {
                if (v.status != VehicleStatus.AVAILABLE) continue;
                if (isAvailable(v, startDate, endDate)) available.add(v);
            }
        }
        return available;
    }

    boolean isAvailable(Vehicle vehicle, LocalDateTime startDate, LocalDateTime endDate) {
        for (Booking b : bookings) {
            if (b.vehicle.equals(vehicle) && b.status == BookingStatus.CONFIRMED) {
                // overlap check
                if (startDate.isBefore(b.end) && endDate.isAfter(b.start)) return false;
            }
        }
        return true;
    }

    public Optional<Booking> bookVehicle(User user, Vehicle vehicle, Store store,
                           LocalDateTime start, LocalDateTime end, ZoomCarLLD.constants.PaymentMode paymentMode) {
        if (!isAvailable(vehicle, start, end)) {
            throw new RuntimeException("Vehicle not available for selected time!");
        }
        String bookingId = UUID.randomUUID().toString();
        double cost = vehicle.calculateCost(start, end);
        Payment payment = new Payment();
        Payment.PaymentResult res = payment.process(cost, paymentMode);
        if (res.status != PaymentStatus.SUCCESS) {
            System.out.println("[Booking] Payment failed. Aborting booking.");
            return Optional.empty();
        }

        Booking booking = new Booking(bookingId, user, vehicle, store, start, end, cost);
        booking.transactionId = res.transactionId;
        bookings.add(booking);
        System.out.println("Booking done: " + booking);
        
        return Optional.of(booking);
    }

    private Optional<Booking> getBookingById(String bookingId) {
        for (Booking b : bookings) if (b.bookingId.equals(bookingId)) return Optional.of(b);
        return Optional.empty();
    }

    public CarInventory getCarInventory() { return carInventory; }
    public BikeInventory getBikeInventory() { return bikeInventory; }

    /* Expose bookings for inspection */
    public List<Booking> getBookings() { return Collections.unmodifiableList(bookings); }

    public boolean completeBooking(String bookingId, int kmsDrivenDuringTrip) {
        Optional<Booking> opt = getBookingById(bookingId);
        if (opt.isEmpty()) {
            System.out.printf("[Complete] Booking %s not found.%n", bookingId);
            return false;
        }
        Booking b = opt.get();
        if (b.status != BookingStatus.CONFIRMED) {
            System.out.printf("[Complete] Booking %s is not in CONFIRMED state (current=%s).%n", bookingId, b.status);
            return false;
        }
        b.complete(kmsDrivenDuringTrip);
        // vehicle.status remains AVAILABLE (unless under maintenance)
        System.out.printf("[Complete] Booking %s completed. Vehicle %s kms updated by %d. New kms=%d%n",
                bookingId, b.vehicle.id, kmsDrivenDuringTrip, b.vehicle.kmsDriven);
        return true;
    }

    /* Cancel a booking: mark CANCELLED and make vehicle available (time-window based) */
    public boolean cancelBooking(String bookingId) {
        Optional<Booking> opt = getBookingById(bookingId);
        if (opt.isEmpty()) {
            System.out.printf("[Cancel] Booking %s not found.%n", bookingId);
            return false;
        }
        Booking b = opt.get();
        if (b.status != BookingStatus.CONFIRMED) {
            System.out.printf("[Cancel] Booking %s not cancellable (current=%s).%n", bookingId, b.status);
            return false;
        }
        b.cancel();
        System.out.printf("[Cancel] Booking %s cancelled.%n", bookingId);
        return true;
    }
}
