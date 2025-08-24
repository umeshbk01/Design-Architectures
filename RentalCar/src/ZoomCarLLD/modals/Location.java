package ZoomCarLLD.modals;

import java.util.ArrayList;
import java.util.List;

public class Location {
    final String id;
    final String city;
    public final List<Store> stores = new ArrayList<>();

    public Location(String id, String city) {
        this.id = id;
        this.city = city;
    }
    public List<Store> getStores() {
        return stores;
    }
}
