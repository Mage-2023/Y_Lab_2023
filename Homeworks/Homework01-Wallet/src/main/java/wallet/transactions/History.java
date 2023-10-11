package wallet.transactions;

import wallet.models.Account;
import wallet.out.Screen;
import wallet.repositories.BankDatabase;

public class History extends Transaction{


    public History(int accountNumber, Screen screen, BankDatabase bankDatabase) {
        super(accountNumber, screen, bankDatabase);
    }

    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        Account account = bankDatabase.getAccount(getAccountNumber());

        screen.displayMessageLine("\nTransaction history");
        bankDatabase.printTransactionHistory(account);



    }
}
