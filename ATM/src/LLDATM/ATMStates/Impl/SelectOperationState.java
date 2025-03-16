package LLDATM.ATMStates.Impl;

import LLDATM.ATMStates.State;
import LLDATM.Constants.TransactionType;
import LLDATM.Models.ATM;
import LLDATM.Models.Card;

public class SelectOperationState extends State{
    public SelectOperationState(){
        showOperations();
    }

    @Override
    public void selectOperation(ATM atm, Card card, TransactionType type){
        switch (type) {
            case CASH_WITHDRAWAL:
                atm.setCurrentState(null);
                break;
            case BALANCE_CHECK:
                atm.setCurrentState(null);
                break;
            default:
                System.out.println("Invalid Option");
                exit(atm);

        }
    }

    @Override
    public void exit(ATM atmObject){
        returnCard();
        atmObject.setCurrentState(new IdleState());
        System.out.println("Exit happens");
    }

    @Override
    public void returnCard(){
        System.out.println("Please collect your card");
    }


    private void showOperations(){
        System.out.println("Please select the Operation");
        TransactionType.showAllTransactionTypes();
    }

}
