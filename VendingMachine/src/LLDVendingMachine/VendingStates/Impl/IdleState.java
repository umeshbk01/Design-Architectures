package LLDVendingMachine.VendingStates.Impl;

import LLDVendingMachine.Models.VendingMachine;
import LLDVendingMachine.VendingStates.State;

public class IdleState implements State{

    public IdleState(){
        System.out.println("Currently Vending machine is in IdleState");
    }

    public IdleState(VendingMachine machine){
        System.out.println("Currently Vending machine is in IdleState");
        machine.setCoinList(new ArrayList<>());
    }

    @Override
    public void clickOnInsertCoin(VendingMachine machine) throws Exception{
        machine.setVendingMachineState(new HasMoneyState());
    }

    @Override
    public void updateInventory(VendingMachine machine, Item item, int codeNumber) throws Exception{
        machine.getInventory().addItem(item, codeNumber);
    }
    
}