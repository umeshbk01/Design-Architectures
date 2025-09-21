package ElevatorStrategyLLD.models;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import ElevatorStrategyLLD.constants.Direction;
import ElevatorStrategyLLD.constants.ElevatorState;
import ElevatorStrategyLLD.controllers.ElevatorRequest;
import ElevatorStrategyLLD.controllers.ExternalRequestController;
import ElevatorStrategyLLD.controllers.InternalRequestController;

public class Elevator implements Runnable{

    public int id;
    public final int maxFloors;
    public volatile int currentFloor=0;

    public volatile ElevatorState state = ElevatorState.IDLE;
    public volatile Direction direction=null;

    public final PriorityBlockingQueue<ElevatorRequest> requests = new PriorityBlockingQueue<>(10, (r1, r2) -> {
        int floor1 = 0;
        int floor2 = 0;     
        if (r1 instanceof ExternalRequestController) {
            floor1 = ((ExternalRequestController) r1).floorNumber;
        } else if (r1 instanceof InternalRequestController) {
            floor1 = ((InternalRequestController) r1).floorNumber;
        }
        if (r2 instanceof ExternalRequestController) {
            floor2 = ((ExternalRequestController) r2).floorNumber;
        } else if (r2 instanceof InternalRequestController) {
            floor2 = ((InternalRequestController) r2).floorNumber;
        }
        return Integer.compare(floor1, floor2);
    });

    public volatile boolean running = true;

    public Elevator(int id, int maxFloors) {
        this.id = id;
        this.maxFloors = maxFloors;
    }
    public int getId() {
        return id;
    }
    public int getCurrentFloor(){
        return currentFloor;
    }
    public void addRequest(ElevatorRequest req){
        requests.offer(req);
    }

    public void shutdown(){
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                ElevatorRequest req = requests.poll(1, TimeUnit.SECONDS);
                if(req != null){
                    serveRequest(req);
                }else{
                    state = ElevatorState.IDLE;
                    direction = null;
                }
            } catch (InterruptedException e) {
                // TODO: handle exception
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Elevator " + id + " shutting down.");
    }
        public void serveRequest(ElevatorRequest req) {
            int targetFloor;
            if(req instanceof ExternalRequestController){
                ExternalRequestController extReq = (ExternalRequestController) req;
                targetFloor = extReq.floorNumber;
                System.out.println("Elevator " + id + " received external request to floor " + targetFloor);
            }else{
                InternalRequestController intReq = (InternalRequestController) req;
                targetFloor = intReq.floorNumber;
                System.out.println("Elevator " + id + " received internal request to floor " + targetFloor);
            }
            if(targetFloor > currentFloor){
                direction = Direction.UP;
                state = ElevatorState.MOVING;
                while(currentFloor < targetFloor){
                    try {
                        Thread.sleep(1000); // Simulate time taken to move one floor
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    currentFloor++;
                    System.out.println("Elevator " + id + " at floor " + currentFloor);
                }
        }
        if(targetFloor > currentFloor){
            direction = Direction.UP;
        }else if(targetFloor < currentFloor){
            direction = Direction.DOWN;
    
        }
        state = ElevatorState.MOVING;
        while (currentFloor != targetFloor) {
            if(direction == Direction.UP){
                currentFloor++;
            }else{
                currentFloor--;
            }
            System.out.println("Elevator " + id + " at floor " + currentFloor);
            try {
                Thread.sleep(1000); // Simulate time taken to move one floor
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        state = ElevatorState.IDLE;
        direction = null;
        System.out.println("Elevator " + id + " reached target floor " + targetFloor);
        if(req instanceof ExternalRequestController){
            int dest = new Random().nextInt(maxFloors);
            if(dest != currentFloor){
                addRequest(new InternalRequestController(dest));
                System.out.println("Elevator " + id + " added internal request to floor " + dest);
            }
        }
    }
}
