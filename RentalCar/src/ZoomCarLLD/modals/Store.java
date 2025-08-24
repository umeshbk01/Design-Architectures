package ZoomCarLLD.modals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Store {
    final String id;
    final String name;
    final String address;
    final List<Vehicle> vehicles = new ArrayList<>();

    public Store(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, address);
    }
}
