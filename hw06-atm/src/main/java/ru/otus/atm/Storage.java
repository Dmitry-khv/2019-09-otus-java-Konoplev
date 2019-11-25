package ru.otus.atm;

import java.util.Set;

public interface Storage {
    void putToCassette(Banknotes banknotes);
    Set<Banknotes> takeMoney(int amount);
    int getCurrentSum();
}
