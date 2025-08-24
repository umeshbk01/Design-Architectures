package ZoomCarLLD.modals;

import java.util.UUID;

import ZoomCarLLD.constants.PaymentMode;
import ZoomCarLLD.constants.PaymentStatus;

public class Payment {
    public static class PaymentResult{
        public final String transactionId;
        public final PaymentStatus status;
        PaymentResult(String transactionId, PaymentStatus status) {
            this.transactionId = transactionId;
            this.status = status;
        }
    }

    public PaymentResult process(double amount, PaymentMode paymentMode){
        System.out.printf("[Payment] Processing ₹%.2f via %s...%n", amount, paymentMode);
        String txn = UUID.randomUUID().toString();
        System.out.printf("[Payment] Success. TxnId=%s%n", txn);
        return new PaymentResult(txn, PaymentStatus.SUCCESS);
    }
}
