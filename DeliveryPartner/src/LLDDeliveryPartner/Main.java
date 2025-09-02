package LLDDeliveryPartner;

import LLDDeliveryPartner.models.DeliveryPartner;
import LLDDeliveryPartner.models.DeliveryRequest;
import LLDDeliveryPartner.services.DeliveryService;

public class Main {
    public static void main(String[] args) {
        DeliveryService service = new DeliveryService();

        // Register partners
        service.addPartner(new DeliveryPartner("Alice"));
        service.addPartner(new DeliveryPartner("Bob"));

        // Create delivery requests
        DeliveryRequest r1 = new DeliveryRequest("Umesh", "MG Road", "Whitefield");
        DeliveryRequest r2 = new DeliveryRequest("Rahul", "HSR", "Koramangala");

        // Assign requests
        service.createAssignment(r1);
        service.createAssignment(r2);

        // Start and complete trips
        service.startTrip(r1.getId());
        service.completeTrip(r1.getId());

        // Now Bob should be free
        service.startTrip(r2.getId());
        service.completeTrip(r2.getId());
    }
}
