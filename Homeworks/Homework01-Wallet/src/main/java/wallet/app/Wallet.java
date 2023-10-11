package wallet.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wallet.in.DepositSlot;
import wallet.in.Keypad;
import wallet.out.CashDispenser;
import wallet.out.Screen;
import wallet.repositories.BankDatabase;
import wallet.transactions.*;

public class Wallet {
    private static final Logger LOGGER = LoggerFactory.getLogger(Wallet.class);
    private boolean userAuthenticated;//wether user is authenticated
    private int currentAccountNumber;
    private Screen screen;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private DepositSlot depositSlot;
    private BankDatabase bankDatabase;//account information database


    private static final int BALANCE_INQUIRY=1;
    private static final int WITHDRAWAL = 2;
    public static final int DEPOSIT = 3;
    private static final int EXIT = 4;
    private static final int HISTORY = 5;

    public Wallet(){
        userAuthenticated = false;//user is not authenticated
        currentAccountNumber =0;//no current account number to start
        screen = new Screen();//initialise screen to display results
        keypad = new Keypad();//keypad to take inputs
        cashDispenser = new CashDispenser();//to despense cash
        depositSlot = new DepositSlot(); // tp deposit cash amounts
        bankDatabase = new BankDatabase();// account information database

    }

    public void run(){
        //createe new account
        createUser();
        //user authentication
        while (true){
            while (!userAuthenticated){
                screen.displayMessage("\nWelcome!");
                authenticateUser();
            }

            performTransaction();
            userAuthenticated = false;
            currentAccountNumber = 0;
            screen.displayMessageLine("\nThank you! Good buy!");


        }
    }

    private void createUser(){
        screen.displayMessageLine("Please enter your new account number:");
        currentAccountNumber= keypad.getInput();
        screen.displayMessage("\nEnter your pin:");
        int pin = keypad.getInput();
        bankDatabase.createUser(currentAccountNumber,pin);


    }

    private void authenticateUser(){
        screen.displayMessageLine("Please enter your account number:");
        int accountNumber = keypad.getInput();
        screen.displayMessage("\nEnter your pin:");
        int pin = keypad.getInput();

        userAuthenticated = bankDatabase.authenticateUser(accountNumber,pin);
        if (userAuthenticated){
            currentAccountNumber = accountNumber;

        }else {
            screen.displayMessageLine("Invalid account number or pin" +
                    " please try again.");
        }

    }
    private void performTransaction(){
        Transaction currentTransaction = null;
        boolean userExited = false;

        while (!userExited){
            int mainMenuSelection = displayMainMenu();
            switch (mainMenuSelection){

                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case DEPOSIT:
                case HISTORY:
                    currentTransaction = createTransaction(mainMenuSelection);
                    currentTransaction.execute();
                    break;
                case EXIT:
                    screen.displayMessageLine("\nExiting the system");
                    userExited = true;
                    LOGGER.info("User - {} exited",currentAccountNumber);

                    break;
                default:
                    screen.displayMessageLine("\nYou didn't enter a valid selection " +
                            "please try again");
                    break;
            }
        }
    }
    private int displayMainMenu(){
        screen.displayMessageLine("\nMain menu: ");
        screen.displayMessageLine("1 - View my balance");
        screen.displayMessageLine("2 - Withdraw cash");
        screen.displayMessageLine("3 - dposit funds");
        screen.displayMessageLine("4 - Exit");
        screen.displayMessageLine("5 - History\n");
        screen.displayMessage("Enter a choice: ");
        return keypad.getInput();
    }
    private Transaction createTransaction(int type){
        Transaction temp = null;
        switch (type){
            case BALANCE_INQUIRY:
                temp = new BalanceInquiry(currentAccountNumber,screen,bankDatabase);
                break;
            case WITHDRAWAL:
                temp = new Withdrawal(currentAccountNumber,screen,bankDatabase,keypad,cashDispenser);
                break;
            case DEPOSIT:
                temp = new Deposit(currentAccountNumber,screen,bankDatabase,keypad,depositSlot);
                break;
            case HISTORY:
                temp = new History(currentAccountNumber,screen,bankDatabase);


        }
        return temp;
    }



}
