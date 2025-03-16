package LLDATM.ATMStates.Impl;

import LLDATM.ATMProcessors.FiveHundredWithdrawProcessor;
import LLDATM.ATMProcessors.OneHundredWithdrawProcessor;
import LLDATM.ATMProcessors.TwoThousandWithdrawProcessor;
import LLDATM.ATMProcessors.CashWithdrawProcess;
import LLDATM.ATMStates.State;
import LLDATM.Models.ATM;
import LLDATM.Models.Card;

public class CashWithdrawalState extends State {
    public CashWithdrawalState() {
        System.out.println("Please enter the Withdrawal Amount");
    }
    public void cashWithdrawal(ATM atm, Card card, int withdrawalAmount){
        if(atm.getAtmBalance() < withdrawalAmount){
            System.out.println("Insufficient fund in the ATM Machine");
            exit(atm);
        }else if(card.getBankBalance() < withdrawalAmount){
            System.out.println("Insufficient fund in the your Bank Account");
            exit(atm);
        }else{
            card.deductBankBalance(withdrawalAmount);
            atm.deductATMBalance(withdrawalAmount);
            
            CashWithdrawProcess withdrawProcessor =
                    new TwoThousandWithdrawProcessor(new FiveHundredWithdrawProcessor(new OneHundredWithdrawProcessor(null)));

            withdrawProcessor.withdraw(atm, withdrawalAmount);
            exit(atm);
        }
    }

    @Override
    public void exit(ATM atmObject) {
        returnCard();
        atmObject.setCurrentState(new IdleState());
        System.out.println("Exit happens");
    }

    @Override
    public void returnCard() {
        System.out.println("Please collect your card");
    }

}
