package MallParking.Ticket;

import java.util.Date;

import MallParking.Vehicles.Vehicle;

public class Ticket {
    private static int ticketCounter = 1;
    private final int ticketId;
    private final Vehicle vehicle;
    private final Date arrivalTime;
    private Date exitTime;
    private double cost;

    public Ticket(Vehicle vehicle) {
        this.ticketId = ticketCounter++;
        this.vehicle = vehicle;
        this.arrivalTime = new Date();
    }

    public int getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}