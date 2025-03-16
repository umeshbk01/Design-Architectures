package LLDATM.ATMStates.Impl;

import LLDATM.ATMStates.State;
import LLDATM.Models.ATM;
import LLDATM.Models.Card;

public class IdleState extends State{

    @Override
    public void insertCard(ATM atm, Card card){
        System.out.println("Card is inserted");
        atm.setCurrentState(new HasCardState());
    }
}
