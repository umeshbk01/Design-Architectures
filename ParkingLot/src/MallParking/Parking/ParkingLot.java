package MallParking.Parking;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MallParking.Ticket.Ticket;
import MallParking.Vehicles.Vehicle;

import MallParking.Constants.*;


public class ParkingLot {
    private final List<ParkingSpot> spots;
    private final Map<Integer, Ticket> activeTickets;
    private final List<Entrance> entrances;
    private final List<Exit> exits;

    public ParkingLot(List<Entrance> entrances, List<Exit> exits, List<ParkingSpot> spots) {
        this.entrances = entrances;
        this.exits = exits;
        this.spots = spots;
        this.activeTickets = new HashMap<>();
    }

    public ParkingSpot findNearestSpot(VehicleType type, int entranceId) {
        return spots.stream()
                .filter(spot -> spot.isFree() && spot.getType() == type)
                .min(Comparator.comparingInt(spot -> spot.getDistanceFromEntrance(entranceId)))
                .orElse(null);
    }

    public Ticket parkVehicle(Vehicle vehicle, int entranceId) {
        ParkingSpot spot = findNearestSpot(vehicle.getType(), entranceId);
        if (spot == null) {
            System.out.println("No available spot for " + vehicle.getType());
            return null;
        }
        spot.occupySpot();
        Ticket ticket = new Ticket(vehicle);
        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    public double calculateCost(Ticket ticket) {
        long duration = (ticket.getExitTime().getTime() - ticket.getArrivalTime().getTime()) / (1000 * 60 * 60); // Hours
        double rate = ticket.getVehicle().getType() == VehicleType.TWO_WHEELER ? 10.0 : 20.0;
        return Math.max(1, duration) * rate;
    }

    public void exitVehicle(int ticketId, PaymentMode paymentMode) {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            System.out.println("Invalid ticket ID");
            return;
        }

        ticket.setExitTime(new Date());
        double cost = calculateCost(ticket);
        ticket.setCost(cost);

        // Free the parking spot
        ParkingSpot spot = spots.stream()
                .filter(s -> !s.isFree() && s.getType() == ticket.getVehicle().getType())
                .findFirst()
                .orElse(null);
        if (spot != null) {
            spot.freeSpot();
        }

        activeTickets.remove(ticketId);
        System.out.println("Payment of Rs. " + cost + " received via " + paymentMode);
    }

    public List<Exit> getExits() {
        return exits;
    }
    
}