package LLDDeliveryPartner.models;

public class DeliveryRequest {
    private static int counter = 1;
    private final int id;
    private final String customerName;
    private final String pickup;
    private final String drop;

     public DeliveryRequest(String customerName, String pickup, String drop) {
        this.id = counter++;
        this.customerName = customerName;
        this.pickup = pickup;
        this.drop = drop;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDrop() {
        return drop;
    }

    @Override
    public String toString() {
        return "DeliveryRequest{" +
                "id=" + id +
                ", customer='" + customerName + '\'' +
                ", pickup='" + pickup + '\'' +
                ", drop='" + drop + '\'' +
                '}';
    }
}
