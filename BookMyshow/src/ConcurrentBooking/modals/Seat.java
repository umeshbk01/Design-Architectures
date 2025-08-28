package ConcurrentBooking.modals;

import ConcurrentBooking.constants.SeatAvailability;

public class Seat {
    public final String seatId;
    public SeatAvailability state;
    public Seat(String seatId){
        this.seatId = seatId;
        this.state = SeatAvailability.AVAILABLE;
    }
    
    @Override
    public String toString() { 
        return seatId + ":" + state; 
    }

}
