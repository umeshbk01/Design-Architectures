package MallParking.Parking;

import java.util.Map;

public interface ParkingSpotFactory {
    ParkingSpot createParkingSpot(int spotId, Map<Integer, Integer> distancesFromEntrances);
}