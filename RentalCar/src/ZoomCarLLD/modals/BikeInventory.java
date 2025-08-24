package ZoomCarLLD.modals;

import java.util.ArrayList;
import java.util.List;

public class BikeInventory implements InventoryManagement<Bike> {
    @Override
    public void addVehicle(Bike vehicle, Store store) {
        store.vehicles.add(vehicle);
    }

    @Override
    public void removeVehicle(Bike vehicle, Store store) {
        store.vehicles.remove(vehicle);
    }

    @Override
    public List<Bike> listAllVechicles(Store store) {
        List<Bike> res = new ArrayList<>();
        for (Vehicle v : store.vehicles) if (v instanceof Bike) res.add((Bike) v);
        return res;
    }
    
}
