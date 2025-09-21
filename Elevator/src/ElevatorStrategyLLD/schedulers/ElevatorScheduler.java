package ElevatorStrategyLLD.schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ElevatorStrategyLLD.constants.ElevatorState;
import ElevatorStrategyLLD.controllers.ExternalRequestController;
import ElevatorStrategyLLD.models.Elevator;

public class ElevatorScheduler {
    public final List<Elevator> elevators;
    public final ExecutorService pool;
    public ElevatorScheduler(int numElevators, int maxFloors){
        elevators = new ArrayList<>();
        pool = Executors.newFixedThreadPool(numElevators);
        for(int i=0; i<numElevators; i++){
            Elevator elevator = new Elevator(i, maxFloors);
            elevators.add(elevator);
            pool.submit(elevator);
        }
    }
    public void submitExternalRequest(ExternalRequestController req){
        Elevator e = null;
        int bestDist = Integer.MAX_VALUE;
        for(Elevator elevator: elevators){
            if(elevator.state == ElevatorState.MAINTENANCE){
                continue;
            }else{
                int dist = Math.abs(elevator.getCurrentFloor() - req.floorNumber);
                if(dist < bestDist){
                    bestDist = dist;
                    e = elevator;
                }
            }
        }
        if(e != null){
            e.addRequest(req);
            System.out.println("Assigned request at floor " + req.floorNumber + " to elevator " + e.id);
    }
}
public void shutdown(){
    for(Elevator e: elevators){
        e.shutdown();
    }
    pool.shutdown();
}
    
}
