package ElevatorLLD.Modal;

import ElevatorLLD.Constants.Direction;

public class ElevatorDisplay {
    int floor;
    Direction direction;
    ElevatorDisplay(){

    }
    ElevatorDisplay(int floor, Direction direction){
        this.floor=floor;
        this.direction=direction;
    }
    public void setDisplay(int floor, Direction direction){
        this.floor=floor;
        this.direction=direction;
    }
    public void showDisplay(){
        System.out.println("Floor: "+floor);
        System.out.println("Direction: " +direction);
    }
}
