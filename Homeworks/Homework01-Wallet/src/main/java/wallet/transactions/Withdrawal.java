package wallet.transactions;

import wallet.in.Keypad;
import wallet.out.CashDispenser;
import wallet.out.Screen;
import wallet.repositories.BankDatabase;

public class Withdrawal extends Transaction{
    private int amount;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private final static int CANCELED = 6;
    public Withdrawal(int accountNumber, Screen screen, BankDatabase bankDatabase,Keypad keypad,CashDispenser cashDispenser) {
        super(accountNumber, screen, bankDatabase);
        this.keypad = keypad;
        this.cashDispenser = cashDispenser;
    }

    @Override
    public void execute() {
        boolean cashDispensed = false;
        double availableBalance;

        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        do {
            amount = displayMenuOfAmounts();
            if (amount != CANCELED){
              availableBalance = bankDatabase.getAvailableBalance(getAccountNumber()) ;
              if (amount <= availableBalance){
                  if (cashDispenser.isSufficientCashAvailable(amount)){
                    bankDatabase.debit(getAccountNumber(),amount);
                    cashDispenser.dispenseCash(amount);
                    cashDispensed = true;
                    screen.displayMessageLine("\nYour cash has been dispensed," +
                            "please take your cash $" + amount);
                  }else{
                      screen.displayMessageLine("\nInsafficient cash available in  your Wallet" +
                              "\nPlease choose a smaller amount");
                  }

              }else {
                  screen.displayMessageLine("\nInsufficient funds in your account." +
                          "\nPlease choose a smaller amount");
              }
            } else {
                screen.displayMessageLine("\nCanceling transaction...");
                return;
            }

        }while (!cashDispensed);


    }
    private int displayMenuOfAmounts(){
        int userChoice = 0;
        Screen screen = getScreen();

        int[] amounts = {0,20,40,60,100,200};

        while (userChoice == 0){
            screen.displayMessageLine("\nWithdrawal Menu:");
            screen.displayMessageLine("1 - $20");
            screen.displayMessageLine("2 - $40");
            screen.displayMessageLine("3 - $60");
            screen.displayMessageLine("4 - $80");
            screen.displayMessageLine("5 - $100");
            screen.displayMessageLine("6 - Cancel transaction");
            screen.displayMessage("\nChoose a withdrawal amount: ");

            int input = keypad.getInput();

            switch (input){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    userChoice = amounts[input];
                    break;
                case CANCELED:
                    userChoice = CANCELED;
                    break;
                default:
                    screen.displayMessage("\nInvalid selection try again");
            }


        }
        return userChoice;
    }
}
