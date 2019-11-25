package ru.otus.atm;

import java.util.Set;

public interface ATM {
    void putMoney(Banknotes banknotes);
    Set<Banknotes> getMoney(int amount);
    String getBalance();
}
