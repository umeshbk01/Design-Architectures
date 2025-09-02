package LLDDeliveryPartner.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import LLDDeliveryPartner.constants.PartnerStatus;
import LLDDeliveryPartner.models.DeliveryPartner;
import LLDDeliveryPartner.models.DeliveryRequest;

public class DeliveryService {
    private final List<DeliveryPartner> partners = new ArrayList<>();
    private final Map<Integer, DeliveryPartner> assignments = new HashMap<>();

    public void addPartner(DeliveryPartner partner) {
        partners.add(partner);
        System.out.println("Added partner: " + partner);
    }

    public void createAssignment(DeliveryRequest request){
        System.out.println("Received new delivery request: " + request);
        Optional<DeliveryPartner> partnerOpt = partners.stream()
                .filter(p -> p.getStatus() == PartnerStatus.AVAILABLE)
                .findFirst();

        if (partnerOpt.isPresent()) {
            DeliveryPartner partner = partnerOpt.get();
            partner.setStatus(PartnerStatus.ASSIGNED);
            assignments.put(request.getId(), partner);
            System.out.println("Assigned " + partner.getName() + " to request " + request.getId());
        } else {
            System.out.println("No available partners for request: " + request.getId());
        }
    }

    public void startTrip(int requestId){
        DeliveryPartner partner = assignments.get(requestId);
        if (partner != null) {
            partner.setStatus(PartnerStatus.ON_TRIP);
            System.out.println("Delivery started for request " + requestId + " with partner " + partner.getName());
        } else {
            System.out.println("No partner assigned for request: " + requestId);
        }
    }

    public void completeTrip(int requestId) {
        DeliveryPartner partner = assignments.get(requestId);
        if (partner != null && partner.getStatus() == PartnerStatus.ON_TRIP) {
            partner.setStatus(PartnerStatus.AVAILABLE);
            assignments.remove(requestId);
            System.out.println("Partner " + partner.getName() + " completed trip for request " + requestId);
        } else {
            System.out.println("Cannot complete trip for request " + requestId);
        }
    }
}
