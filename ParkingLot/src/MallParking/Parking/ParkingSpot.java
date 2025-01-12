package MallParking.Parking;

import java.util.Map;

import MallParking.Constants.VehicleType;

public class ParkingSpot {
    private final int spotId;
    private final VehicleType type;
    private boolean isFree;
    private final Map<Integer, Integer> distancesFromEntrances;
    
    public ParkingSpot(int spotId, VehicleType type, Map<Integer, Integer> distancesFromEntrances) {
        this.spotId = spotId;
        this.type = type;
        this.isFree = true;
        this.distancesFromEntrances = distancesFromEntrances;
    }

    public int getSpotId() {
        return spotId;
    }

    public VehicleType getType() {
        return type;
    }

    public boolean isFree() {
        return isFree;
    }

    public void occupySpot() {
        this.isFree = false;
    }

    public void freeSpot() {
        this.isFree = true;
    }

    public int getDistanceFromEntrance(int entranceId) {
        return distancesFromEntrances.getOrDefault(entranceId, Integer.MAX_VALUE);
    }

}