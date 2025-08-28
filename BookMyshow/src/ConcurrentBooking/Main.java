package ConcurrentBooking;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ConcurrentBooking.modals.Booking;
import ConcurrentBooking.modals.Screen;
import ConcurrentBooking.modals.Seat;
import ConcurrentBooking.modals.Show;
import ConcurrentBooking.modals.User;
import ConcurrentBooking.services.AuthService;
import ConcurrentBooking.services.BookingService;
import ConcurrentBooking.services.PaymentGateway;
import ConcurrentBooking.services.SeatService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Setup screen and show
        Screen screen = new Screen("screen-1", 3, 6); // rows A,B,C with 6 seats each
        Show show = new Show("show-1", "Interstellar", screen, LocalDateTime.now().plusHours(3));
        Map<String, Show> shows = new HashMap<>();
        shows.put(show.id, show);

        // Services
        SeatService seatService = new SeatService(shows, 10); // holds expire after 10s for demo
        PaymentGateway paymentGateway = new PaymentGateway();
        BookingService bookingService = new BookingService(seatService, paymentGateway);
        AuthService authService = new AuthService();

        // Users and tokens
        User alice = new User("u-1", "Alice", "USER");
        User bob = new User("u-2", "Bob", "USER");
        String tAlice = authService.login(alice);
        String tBob = authService.login(bob);

        System.out.println("Tokens: Alice=" + tAlice + " Bob=" + tBob);

        // Show seat map
        System.out.println("\nInitial seat map:");
        printSeatMap(seatService.getSeatMap(show.id));

        // We'll attempt to book the same 2 seats concurrently
        Set<String> desired = new HashSet<>(Arrays.asList("A-1", "A-2"));
        Runnable aliceTask = () -> {
            System.out.println("\n[Alice] trying to book seats " + desired);
            Optional<Booking> b = bookingService.book(alice.userId, show.id, desired, "pay-" + UUID.randomUUID(), 500.0);
            System.out.println("[Alice] result: " + (b.isPresent() ? "SUCCESS " + b.get().bookingId : "FAILED"));
        };

        Runnable bobTask = () -> {
            System.out.println("\n[Bob] trying to book seats " + desired);
            // Introduce a little delay so concurrency interleaves
            try { Thread.sleep(50); } catch (InterruptedException e) {}
            Optional<Booking> b = bookingService.book(bob.userId, show.id, desired, "pay-" + UUID.randomUUID(), 500.0);
            System.out.println("[Bob] result: " + (b.isPresent() ? "SUCCESS " + b.get().bookingId : "FAILED"));
        };

        // Run concurrently
        ExecutorService ex = Executors.newFixedThreadPool(4);
        ex.submit(aliceTask);
        ex.submit(bobTask);

        // Wait for threads to finish
        ex.shutdown();
        ex.awaitTermination(30, TimeUnit.SECONDS);

        // Wait a bit (allow TTL holds to expire if any)
        System.out.println("\nWaiting 12s to allow holds to expire if not confirmed...");
        Thread.sleep(12_000);

        System.out.println("\nSeat map after attempts:");
        printSeatMap(seatService.getSeatMap(show.id));

        seatService.shutdown();
        System.out.println("\nDemo complete.");
    }

    private static void printSeatMap(Map<String, Seat> seatMap) {
        StringBuilder sb = new StringBuilder();
        seatMap.entrySet().forEach(e -> {
            sb.append(e.getKey()).append(":").append(e.getValue().state).append("  ");
        });
        System.out.println(sb.toString());
    }
}
