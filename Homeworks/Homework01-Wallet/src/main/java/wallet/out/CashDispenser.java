package wallet.out;

public class CashDispenser {
    private static final int INITIAL_COUNT = 500;
    private int count;

    public CashDispenser(){
        count = INITIAL_COUNT;
    }

    public void dispenseCash(double amount){
        double billsRequiered = amount / 20;
        count -= billsRequiered;
    }

    public boolean isSufficientCashAvailable(double amount){
        double billsRequiered = amount / 20;

        if (count >= billsRequiered)
            return true;
        else
            return false;

    }
}
