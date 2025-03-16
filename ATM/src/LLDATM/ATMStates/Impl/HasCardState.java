package LLDATM.ATMStates.Impl;

import LLDATM.ATMStates.State;
import LLDATM.Models.ATM;
import LLDATM.Models.Card;

public class HasCardState extends State{
    public HasCardState(){
        System.out.println("enter your card pin number");
    }

    @Override
    public void authenticatePin(ATM atm, Card card, int pin){
        boolean authPin = card.isCorrectPINEntered(pin);
        if(authPin){
            atm.setCurrentState(null);
        }else{
            System.out.println("Invalid PIN Number");
            exit(atm);
        }
    }

    @Override
    public void exit(ATM atm){
        returnCard();
        atm.setCurrentState(new IdleState());
        System.out.println("Exit happens");
    }

    @Override
    public void returnCard(){
        System.out.println("Please collect your card");
    }

}
