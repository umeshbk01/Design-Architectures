package ZoomCarLLD.modals;

import java.util.List;

public interface InventoryManagement<T extends Vehicle> {
    void addVehicle(T vehicle, Store store);
    void removeVehicle(T vehicle, Store store);
    List<T> listAllVechicles(Store store);
}
