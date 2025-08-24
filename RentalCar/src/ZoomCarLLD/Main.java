package ZoomCarLLD;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import ZoomCarLLD.constants.PaymentMode;
import ZoomCarLLD.modals.Bike;
import ZoomCarLLD.modals.Booking;
import ZoomCarLLD.modals.Car;
import ZoomCarLLD.modals.Location;
import ZoomCarLLD.modals.Store;
import ZoomCarLLD.modals.User;
import ZoomCarLLD.modals.Vehicle;
import ZoomCarLLD.services.RentalServices;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Car/Bike Rental System (Complete) Demo ===");

        // Setup locations & stores
        Location bengaluru = new Location("loc-1", "Bengaluru");
        Store koramangala = new Store("store-1", "Koramangala Store", "Koramangala 5th Block");
        Store indiranagar = new Store("store-2", "Indiranagar Store", "Indiranagar 100ft Road");
        bengaluru.stores.add(koramangala);
        bengaluru.stores.add(indiranagar);

        Location hyderabad = new Location("loc-2", "Hyderabad");
        Store btm = new Store("store-3", "BTM Store", "BTM Layout");
        hyderabad.stores.add(btm);

        RentalServices rental = new RentalServices();

        // Vehicles with pricing & specs
        Vehicle c1 = new Car("car-1", "Hyundai", "i20", "KA01AB1234", 2000.0, 200.0, 5, 30000);
        Vehicle c2 = new Car("car-2", "Tata", "Nexon", "KA02XY5678", 2500.0, 250.0, 5, 25000);
        Vehicle c3 = new Car("car-3", "Maruti", "Baleno", "KA03ZZ9012", 1800.0, 180.0, 5, 40000);

        Bike b1 = new Bike("bike-1", "Honda", "Activa", "KA04BC1111", 500.0, 50.0, 2, 12000);

        // Add to stores via inventory
        rental.getCarInventory().addVehicle((Car) c1, koramangala);
        rental.getCarInventory().addVehicle((Car) c2, koramangala);
        rental.getCarInventory().addVehicle((Car) c3, indiranagar);
        rental.getBikeInventory().addVehicle(b1, koramangala);

        // Users
        User umesh = new User("user-1", "Umesh Kumar", "DL-12345", "9999999999");
        User raghav = new User("user-2", "Raghav Rao", "DL-54321", "8888888888");

        // Time windows
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start1 = now.plusHours(2);
        LocalDateTime end1 = start1.plusDays(2).plusHours(5); // 2 days + 5 hours

        System.out.println("\n-- Search available vehicles at Bengaluru for window --");
        List<Vehicle> avail = rental.searchAvailableVehicles(bengaluru, start1, end1);
        avail.forEach(v -> System.out.println("  " + v));

        System.out.println("\n-- Booking 1: Umesh books car-1 --");
        Optional<Booking> booking1 = rental.bookVehicle(umesh, c1, koramangala, start1, end1, PaymentMode.UPI);
        booking1.ifPresent(b -> System.out.println("[Booked] " + b));

        System.out.println("\n-- Attempt double-book: Raghav tries to book car-1 overlapping (should fail) --");
        LocalDateTime start2 = start1.plusHours(12);
        LocalDateTime end2 = start2.plusDays(1);
        Optional<Booking> booking2 = rental.bookVehicle(raghav, c1, koramangala, start2, end2, PaymentMode.UPI);
        if (booking2.isEmpty()) System.out.println("[Expected] Double-book prevented.");

        System.out.println("\n-- Booking 3: Raghav books car-2 (different car) --");
        Optional<Booking> booking3 = rental.bookVehicle(raghav, c2, koramangala, start2, end2, PaymentMode.UPI);
        booking3.ifPresent(b -> System.out.println("[Booked] " + b));

        System.out.println("\n-- Availability at Koramangala for same window after bookings --");
        List<Vehicle> availAfter = rental.searchAvailableVehicles(bengaluru, start1, end1);
        availAfter.forEach(v -> System.out.println("  " + v));

        // Complete booking1 (simulate trip finished)
        if (booking1.isPresent()) {
            System.out.println("\n-- Completing booking1 (car-1) with 150 kms driven --");
            boolean completed = rental.completeBooking(booking1.get().bookingId, 150);
            System.out.println("Completed: " + completed);
            System.out.println("Car-1 new kms: " + c1.kmsDriven);
        }

        // Cancel booking3
        if (booking3.isPresent()) {
            System.out.println("\n-- Cancelling booking3 (car-2) --");
            boolean cancelled = rental.cancelBooking(booking3.get().bookingId);
            System.out.println("Cancelled: " + cancelled);
        }

        System.out.println("\n-- Final Bookings Summary --");
        rental.getBookings().forEach(System.out::println);

        System.out.println("\n-- Final availability at Koramangala for original window --");
        List<Vehicle> finalAvail = rental.searchAvailableVehicles(bengaluru, start1, end1);
        finalAvail.forEach(v -> System.out.println("  " + v));

        System.out.println("\n=== End of Demo ===");
    }
}
