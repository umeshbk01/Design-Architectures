package RentalCarLLD.Models;

import java.util.Date;

import RentalCarLLD.Constants.PaymentMode;

public class PaymentDetails {

    int paymentId;
    int amountPaid;
    Date dateOfPayment;
    boolean isRefundable;
    PaymentMode paymentMode;

}

