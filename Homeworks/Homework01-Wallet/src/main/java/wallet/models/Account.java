package wallet.models;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int accountNumber;//account number
    private int pin; // pin for authentication
    private double availableBalance; // funds available for withdrawal;
    private double totalBalance;//funds available  + pending deposits

    private List<String> transactionHistory = new ArrayList<>();

    public Account() {
    }

    public Account(int accountNumber, int pin, double availableBalance, double totalBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.availableBalance = availableBalance;
        this.totalBalance = totalBalance;
    }

    public boolean validatePin(int userPIN){

        if (userPIN == pin){
            return  true;
        } else
            return false;

    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void credit(double amount){
        totalBalance += amount;
        availableBalance = totalBalance;
    }

    public void debit(double amount){
        availableBalance -= amount;
        totalBalance -= amount;

    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }


    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}
