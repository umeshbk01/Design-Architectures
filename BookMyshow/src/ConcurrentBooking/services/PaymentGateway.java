package ConcurrentBooking.services;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentGateway {
    private final Random rnd = new Random();
    private final Map<String, String> completed = new ConcurrentHashMap<>(); // paymentRequestId -> txnId

    public synchronized Optional<String> processPayment(String paymentRequestId, double amount) {
        if (completed.containsKey(paymentRequestId)) {
            return Optional.of(completed.get(paymentRequestId));
        }
        // simulate 80% success rate
        boolean success = rnd.nextInt(100) < 80;
        if (!success) {
            return Optional.empty();
        }
        String txnId = "txn-" + UUID.randomUUID();
        completed.put(paymentRequestId, txnId);
        return Optional.of(txnId);
    }    

}
