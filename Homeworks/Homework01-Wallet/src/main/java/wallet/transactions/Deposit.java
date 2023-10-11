package wallet.transactions;

import wallet.in.DepositSlot;
import wallet.in.Keypad;
import wallet.out.Screen;
import wallet.repositories.BankDatabase;

public class Deposit extends Transaction{
private double amount;
private Keypad keypad;
private DepositSlot depositSlot;
private final  static  int CANCELED = 0;

    public Deposit(int accountNumber, Screen screen,
                   BankDatabase bankDatabase,Keypad keypad,DepositSlot depositSlot) {
        super(accountNumber, screen, bankDatabase);

        this.keypad = keypad;
        this.depositSlot = depositSlot;

    }

    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        amount = promptForDepositAmount();

        if (amount != CANCELED){
            screen.displayMessage("Deposit envelope containing ");
            screen.displayDollarAmount(amount);

            boolean envelopeReceived = depositSlot.isEnvelopeReceived();

            if (envelopeReceived){
                screen.displayMessageLine("\nYour envelope hase been received ");

                bankDatabase.credit(getAccountNumber(),amount);
            }else {
                screen.displayMessageLine("You did not insert an envelope,so your transaction" +
                        "has been canceled: ");
            }

        }else {
            screen.displayMessageLine("\nCanceling transaction...");
        }

    }

    private double promptForDepositAmount(){
        Screen screen = getScreen();

        screen.displayMessage("\nPlease enter your deposit amount in cents " +
                "or 0 to cancel: ");
        int input = keypad.getInput();

        if (input == CANCELED)
            return CANCELED;
        else {
            return (double) input / 100;
        }
    }
}
