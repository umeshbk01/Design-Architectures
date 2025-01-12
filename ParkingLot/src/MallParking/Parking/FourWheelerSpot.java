package MallParking.Parking;

import java.util.Map;

import MallParking.Constants.VehicleType;


public class FourWheelerSpot implements ParkingSpotFactory{

    @Override
    public ParkingSpot createParkingSpot(int spotId, Map<Integer, Integer> distancesFromEntrances) {
        return new ParkingSpot(spotId, VehicleType.TWO_WHEELER, distancesFromEntrances);
    }


}