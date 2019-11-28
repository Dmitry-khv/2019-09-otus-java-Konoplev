package atm;

import java.util.Set;

public interface ATM {
    void putToCassette(Banknotes banknotes);
    Set<Banknotes> takeMoney(int amount);
    int getCurrentBalance();
    ATM clone();
}