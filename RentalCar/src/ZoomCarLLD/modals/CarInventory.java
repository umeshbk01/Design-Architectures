package ZoomCarLLD.modals;

import java.util.ArrayList;
import java.util.List;

public class CarInventory implements InventoryManagement<Car> {

    @Override
    public void addVehicle(Car vehicle, Store store) {
        store.vehicles.add(vehicle);
    }

    @Override
    public void removeVehicle(Car car, Store store) {
        store.vehicles.remove(car);
    }

    @Override
    public List<Car> listAllVechicles(Store store) {
        List<Car> res = new ArrayList<>();
        for (Vehicle v : store.vehicles) if (v instanceof Car) res.add((Car) v);
        return res;
    }
    
}
