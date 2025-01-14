package ElevatorLLD.Modal;

import ElevatorLLD.Constants.Direction;
import ElevatorLLD.Constants.ElevatorState;

public class ElevatorCar {
    public int id;
    public ElevatorDisplay display;
    InternalButtons internalButtons;
    public ElevatorState elevatorState;
    int currentFloor;
    Direction direction;
    ElevatorDoor elevatorDoor;

    public ElevatorCar(){
        display = new ElevatorDisplay();
        internalButtons = new InternalButtons();
        elevatorState = ElevatorState.IDLE;
        currentFloor = 0;
        direction = Direction.UP;
        elevatorDoor = new ElevatorDoor();

    }
    public void showDisplay() {
         display.showDisplay();
    }

    public void pressButton(int destination) {
        internalButtons.pressButton(destination, this);
    }

    public void setDisplay() {
        this.display.setDisplay(currentFloor, direction);
    }

    boolean moveElevator(Direction dir, int destinationFloor){
        int startFloor = currentFloor;
        if(dir == Direction.UP) {
            for(int i = startFloor; i<=destinationFloor; i++) {

                this.currentFloor = startFloor;
                setDisplay();
                showDisplay();
                if(i == destinationFloor) {
                    return true;
                }
            }
        }

        if(dir == Direction.DOWN) {
            for(int i = startFloor; i>=destinationFloor; i--) {

                this.currentFloor = startFloor;
                setDisplay();
                showDisplay();
                if(i == destinationFloor) {
                    return true;
                }
            }
        }
        return false;
    }


}
