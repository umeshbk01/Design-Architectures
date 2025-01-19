package RentalCarLLD;

import java.util.ArrayList;
import java.util.List;

import RentalCarLLD.Constants.VehicleType;
import RentalCarLLD.Models.Bill;
import RentalCarLLD.Models.Car;
import RentalCarLLD.Models.Location;
import RentalCarLLD.Models.Payment;
import RentalCarLLD.Models.Reservation;
import RentalCarLLD.Models.Store;
import RentalCarLLD.Models.User;
import RentalCarLLD.Models.Vehicle;
import RentalCarLLD.Models.VehicleRentalSystem;

public class Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setUserId(1);

        users.add(user1);

        List<Vehicle> vehicles = new ArrayList<>();

        Vehicle vehicle1 = new Car();
        vehicle1.setVehicleID(1);
        vehicle1.setVehicleType(VehicleType.CAR);

        Vehicle vehicle2 = new Car();
        vehicle1.setVehicleID(2);
        vehicle1.setVehicleType(VehicleType.CAR);

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);


        List<Store> stores = new ArrayList<>();
        Store store1 = new Store();
        store1.storeId=1;
        store1.setVehicles(vehicles);

        stores.add(store1);

        VehicleRentalSystem rentalSystem = new VehicleRentalSystem(stores, users);

        User user = users.get(0);

        //1. user search store based on location
        Location location = new Location(403012, "Bangalore", "Karnataka", "India");
        Store store = rentalSystem.getStore(location);

        //2. get All vehicles you are interested in (based upon different filters)
        List<Vehicle> storeVehicles = store.getVehicles(VehicleType.CAR);

        Reservation reservation = store.createReservation(storeVehicles.get(0), users.get(0));

        Bill bill = new Bill(reservation);

        //5. make payment
        Payment payment = new Payment();
        payment.payBill(bill);

       //6. trip completed, submit the vehicle and close the reservation
        store.completeReservation(reservation.reservationId);


    }
}
