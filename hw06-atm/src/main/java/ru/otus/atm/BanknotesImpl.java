package ru.otus.atm;

import java.util.*;

public class BanknotesImpl implements Banknotes {
    private final Value value;
    private int amount;
    static StorageImpl storage = new StorageImpl();

    BanknotesImpl(Value value, int amount) {
        this.value = value;
        this.amount = amount;
    }

    @Override
    public void putToStorage() {
        storage.putToStorage(this);
    }

    public static Set<Banknotes> getFromStorage(int amount) {
        return storage.getFromStorage(amount);
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public int compareTo(Banknotes o) {
        return o.getValue().getValue() - value.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BanknotesImpl banknotes = (BanknotesImpl) o;
        return value == banknotes.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
