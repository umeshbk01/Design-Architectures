package ZoomCarLLD.modals;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ZoomCarLLD.constants.VehicleStatus;

abstract public class Vehicle {
    public final String id;
    final String brand;
    final String model;
    final String regNo;
    public VehicleStatus status;

    // Pricing & Specs
    final double dailyRentalCost;   // price per day
    final double hourlyRentalCost;  // price per hour
    final int noOfSeats;
    public int kmsDriven;

    protected Vehicle(String id, String brand, String model, String regNo,
                      double dailyRentalCost, double hourlyRentalCost,
                      int noOfSeats, int kmsDriven) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.regNo = regNo;
        this.status = VehicleStatus.AVAILABLE;
        this.dailyRentalCost = dailyRentalCost;
        this.hourlyRentalCost = hourlyRentalCost;
        this.noOfSeats = noOfSeats;
        this.kmsDriven = kmsDriven;
    }

    public double calculateCost(LocalDateTime start, LocalDateTime end){
        long mins = Duration.between(start, end).toMinutes();
        if (mins <= 0) return 0.0;
        long minutesPerDay = 24 * 60;
        long days = mins/minutesPerDay;
        long remainingMins = mins % minutesPerDay;
        long hours = remainingMins / 60;
        long leftoverMinutes = remainingMins % 60;
        double cost = days * dailyRentalCost + hours * hourlyRentalCost;
        if (leftoverMinutes > 0) {
            cost += hourlyRentalCost; // charge an extra hour for leftover minutes
        }
        return cost;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s) seats=%d kms=%d price/day=%.2f price/hr=%.2f",
                brand, model, regNo, noOfSeats, kmsDriven, dailyRentalCost, hourlyRentalCost);
    }
}
