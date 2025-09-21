package ElevatorStrategyLLD;

import ElevatorStrategyLLD.constants.Direction;
import ElevatorStrategyLLD.controllers.ExternalRequestController;
import ElevatorStrategyLLD.schedulers.ElevatorScheduler;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ElevatorScheduler scheduler = new ElevatorScheduler(3, 10);

        // Simulate external hall calls
        scheduler.submitExternalRequest(new ExternalRequestController(2, Direction.UP));
        Thread.sleep(1000);
        scheduler.submitExternalRequest(new ExternalRequestController(5, Direction.DOWN));
        Thread.sleep(1000);
        scheduler.submitExternalRequest(new ExternalRequestController(7, Direction.DOWN));
        scheduler.submitExternalRequest(new ExternalRequestController(1, Direction.UP));

        Thread.sleep(12000);
        scheduler.shutdown();
    }
}
