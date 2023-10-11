package wallet.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wallet.models.Account;

import java.util.ArrayList;
import java.util.List;

public class BankDatabase {


  private static final Logger LOGGER = LoggerFactory.getLogger(BankDatabase.class);
    private List<Account> accounts;

    public BankDatabase() {
        accounts = new ArrayList<>();

    }
    public void createUser(int accountNumber,int userPin){
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setPin(userPin);
        accounts.add(account);
      //  getAccount(accountNumber).getTransactionHistory().add("Registered new account: " + accountNumber);
        LOGGER.info("Registered new account with account number - {} and pin - {}",accountNumber,userPin); //loggered into the log.txt
    }

    public Account getAccount(int accountNumber){
        for (Account account : accounts){
            if (account.getAccountNumber() == accountNumber){
                return account;
            }
        }
        return null;
    }

    public boolean authenticateUser(int accountNumber,int userPin){
        Account account = getAccount(accountNumber);

        if (account != null){
            return account.validatePin(userPin);
        }else
            return false;
    }
    public double getAvailableBalance(int accountNumber){
        return getAccount(accountNumber).getAvailableBalance();
    }

    public double getTotalBalance(int accountNumber){
        return getAccount(accountNumber).getTotalBalance();
    }

    public void debit(int accountNumber,double amount){
        getAccount(accountNumber).debit(amount);
        getAccount(accountNumber).getTransactionHistory().add("Withdrew $" + amount + ". The balance: $" + getTotalBalance(accountNumber));
        LOGGER.debug("User with account number - {} withdrew amount - {}",accountNumber,amount);

    }


    public void credit(int accountNumber,double amount){
        getAccount(accountNumber).credit(amount);
        getAccount(accountNumber).getTransactionHistory().add("Deposited $" + amount + ". The balance: $" + getTotalBalance(accountNumber));
        LOGGER.debug("User with account number - {} deposited amount - {}",accountNumber,amount);
    }


    public void printTransactionHistory(Account account){
        System.out.println("Transaction history for Account: " + account.getAccountNumber() + ":");
        for (String history : account.getTransactionHistory()){
            System.out.println(history);

        }
        LOGGER.info("User {} viewed transaction history",account.getAccountNumber());
    }


}
