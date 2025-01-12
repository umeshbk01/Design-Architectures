package MallParking;
import java.util.*;

import MallParking.Parking.TwoWheelerSpot;
import MallParking.Ticket.Ticket;
import MallParking.Vehicles.Vehicle;
import MallParking.Parking.Entrance;
import MallParking.Parking.Exit;
import MallParking.Parking.FourWheelerSpot;
import MallParking.Parking.ParkingLot;
import MallParking.Parking.ParkingSpot;
import MallParking.Parking.ParkingSpotFactory;

import MallParking.Constants.*;


public class Main {

    public static void main(String[] args) {
        Entrance entrance1 = new Entrance(1);
        Entrance entrance2 = new Entrance(2);

        Exit exit1 = new Exit(1);
        Exit exit2 = new Exit(2);

        List<Entrance> entrances = Arrays.asList(entrance1, entrance2);
        List<Exit> exits = Arrays.asList(exit1, exit2);

        List<ParkingSpot> spots = new ArrayList<>();
        ParkingSpotFactory twoWheelerFactory = new TwoWheelerSpot();
        ParkingSpotFactory fourWheelerFactory = new FourWheelerSpot();

        for (int i = 1; i <= 5; i++) {
            Map<Integer, Integer> distances = new HashMap<>();
            distances.put(1, i);
            distances.put(2, 10 - i);
            spots.add(twoWheelerFactory.createParkingSpot(i, distances));
        }

        for (int i = 6; i <= 10; i++) {
            Map<Integer, Integer> distances = new HashMap<>();
            distances.put(1, i - 5);
            distances.put(2, 15 - i);
            spots.add(fourWheelerFactory.createParkingSpot(i, distances));
        }

        ParkingLot parkingLot = new ParkingLot(entrances, exits, spots);

        // Example usage
        Vehicle bike = new Vehicle("KA-01-B1234", VehicleType.TWO_WHEELER);
        Ticket bikeTicket = parkingLot.parkVehicle(bike, 1);

        Vehicle car = new Vehicle("KA-02-C5678", VehicleType.FOUR_WHEELER);
        Ticket carTicket = parkingLot.parkVehicle(car, 2);

        try {
            Thread.sleep(2000); // Simulate time lapse
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        parkingLot.exitVehicle(bikeTicket.getTicketId(), PaymentMode.CARD);
        parkingLot.exitVehicle(carTicket.getTicketId(), PaymentMode.CASH);

        System.out.println("Exits: " + parkingLot.getExits().size());
    }
}