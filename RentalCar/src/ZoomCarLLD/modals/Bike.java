package ZoomCarLLD.modals;

public class Bike extends Vehicle {

    public Bike(String id, String brand, String model, String regNo,
                double dailyRentalCost, double hourlyRentalCost,
                int noOfSeats, int kmsDriven) {
        super(id, brand, model, regNo, dailyRentalCost, hourlyRentalCost, noOfSeats, kmsDriven);
    }
}
