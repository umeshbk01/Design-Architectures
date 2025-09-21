package ElevatorStrategyLLD.controllers;

import ElevatorStrategyLLD.constants.Direction;

public class ExternalRequestController implements ElevatorRequest {
    public int floorNumber;
    public Direction direction;
    public ExternalRequestController(int floor, Direction direction) {
        this.floorNumber = floor;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "HallRequest[floor=" + floorNumber + ", dir=" + direction + "]";
    }
}
