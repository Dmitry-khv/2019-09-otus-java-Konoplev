package ru.otus.atm;

import java.util.Set;

public class ATM {

    public void putMoney(Value value, int amount) {
        new BanknotesImpl(value, amount).putToStorage();
    }

    public Set<Banknotes> getMoney(int amount) {
        return BanknotesImpl.getFromStorage(amount);
    }
}

