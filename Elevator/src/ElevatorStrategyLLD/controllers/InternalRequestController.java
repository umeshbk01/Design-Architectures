package ElevatorStrategyLLD.controllers;

public class InternalRequestController implements ElevatorRequest {
    public int floorNumber;
    public InternalRequestController(int floor) {
        this.floorNumber = floor;
    }
    @Override
    public String toString() {
        return "CarRequest[floor=" + floorNumber + "]";
    }
}
