package RentalCarLLD.Models;

import java.util.ArrayList;
import java.util.List;

import RentalCarLLD.Constants.VehicleType;

public class Store {
        int storeId;
    VehicleInventoryManagement inventoryManagement;
    Location storeLocation;


    public List<Vehicle> getVehicles(VehicleType vehicleType) {

        return inventoryManagement.getVehicles();
    }


    //addVehicles, update vehicles, use inventory management to update those.


    public void setVehicles(List<Vehicle> vehicles) {
        inventoryManagement = new VehicleInventoryManagement(vehicles);
    }

    public boolean completeReservation(int reservationID) {

        //take out the reservation from the list and call complete the reservation method.
        return true;
    }

    //update reservation

}
