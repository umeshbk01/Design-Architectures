package LLDDeliveryPartner.models;

import LLDDeliveryPartner.constants.PartnerStatus;

public class DeliveryPartner {
    private static int counter = 1;
    private final int id;
    private final String name;
    private PartnerStatus status;

    public DeliveryPartner(String name) {
        this.id = counter++;
        this.name = name;
        this.status = PartnerStatus.AVAILABLE;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PartnerStatus getStatus() {
        return status;
    }

    public void setStatus(PartnerStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
