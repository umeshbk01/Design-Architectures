package LLDATM.ATMProcessors;

import LLDATM.Models.ATM;

public abstract class CashWithdrawProcess {
    CashWithdrawProcess nexCashWithdrawProcess;

    CashWithdrawProcess(CashWithdrawProcess cashWithdrawProcess){
        this.nexCashWithdrawProcess = cashWithdrawProcess;
    }

    public void withdraw(ATM atm, int remainingAmount){
        if(nexCashWithdrawProcess != null){
            nexCashWithdrawProcess.withdraw(atm, remainingAmount);
        }
    }
}
