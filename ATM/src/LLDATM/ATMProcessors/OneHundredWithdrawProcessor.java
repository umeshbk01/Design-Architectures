package LLDATM.ATMProcessors;

import LLDATM.Models.ATM;

public class OneHundredWithdrawProcessor extends CashWithdrawProcess{
    public OneHundredWithdrawProcessor(CashWithdrawProcess nextCashWithdrawProcessor){
        super(nextCashWithdrawProcessor);
    }

    public void withdraw(ATM atm, int remainingAmount){
        int required = remainingAmount/100;
        int balance = remainingAmount%100;

        if(required <= atm.getNoOfOneHundredNotes()){
            atm.deductOneHundredNotes(required);
        }else if(required > atm.getNoOfOneHundredNotes()){
            atm.deductOneHundredNotes(balance);
            balance += (required - atm.getNoOfOneHundredNotes());
        }

        if(balance != 0){
            System.out.println("Something went wrong");
        }
    }
}
