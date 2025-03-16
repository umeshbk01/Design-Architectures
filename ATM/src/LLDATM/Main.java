package LLDATM;

import LLDATM.Models.User;
import LLDATM.Models.UserBankAccount;
import LLDATM.Constants.TransactionType;
import LLDATM.Models.ATM;
import LLDATM.Models.Card;
public class Main {
    ATM atm;
    User user;
    public static void main(String[] args) {
        System.out.println("ATM Low level design");
        Main main = new Main();
        main.initialize();

        main.atm.printCurrentATMStatus();
        main.atm.getCurrentState().insertCard(main.atm, main.user.card);
        main.atm.getCurrentState().authenticatePin(main.atm, main.user.card, 112211);
        main.atm.getCurrentState().selectOperation(main.atm, main.user.card, TransactionType.CASH_WITHDRAWAL);
        main.atm.getCurrentState().cashWithdrawal(main.atm, main.user.card, 2700);
        main.atm.printCurrentATMStatus();

    }

    private void initialize() {

        //create ATM
        atm = ATM.getATMObject();
        atm.setAtmBalance(3500, 1,2,5);

        //create User
        this.user = createUser();
    }

    private User createUser(){

        User user = new User();
        user.setCard(createCard());
        return user;
    }

    private Card createCard(){

        Card card = new Card();
        card.setBankAccount(createBankAccount());
        return card;
    }

    private UserBankAccount createBankAccount() {

        UserBankAccount bankAccount = new UserBankAccount();
        bankAccount.balance = 3000;

        return bankAccount;

    }

}
