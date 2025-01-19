package RentalCarLLD.Models;

public class Location {
    String address;
    int pincode;
    String city;
    String state;
    String country;

    public Location(int pincode, String city, String state, String country) {
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.country = country;

    }
}
