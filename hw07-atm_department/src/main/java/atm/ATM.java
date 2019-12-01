package atm;

import atm_departmen.Memento;

import java.util.Set;

public interface ATM extends Listener{
    void putToCassette(Banknotes...banknotes);
    Set<Banknotes> takeMoney(int amount);
    Integer getCurrentBalance();
    ATM clone();
   Memento saveState();
}