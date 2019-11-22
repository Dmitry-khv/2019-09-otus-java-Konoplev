package ru.otus.atm;

import java.util.*;

public class StorageImpl {
    private static Set<Banknotes> storage = new TreeSet<>();

    public void putToStorage(Banknotes banknotes) {
        int amount = banknotes.getAmount();
        Iterator<Banknotes> banknotesIterator = storage.iterator();
        while (banknotesIterator.hasNext()) {
            Banknotes iterator = banknotesIterator.next();
            if (iterator.equals(banknotes)) {
                amount += iterator.getAmount();
                banknotesIterator.remove();
                break;
            }
        }
        storage.add(new BanknotesImpl(banknotes.getValue(), amount));
    }


    public Set<Banknotes> getFromStorage(int amount) {
        if (amount > getCurrentSum())
            throw new IllegalArgumentException("Сумма превышает допустимую");
        Set<Banknotes> delivery = new TreeSet<>();
        Iterator<Banknotes> banknotesIterator = storage.iterator();
        Banknotes banknote;
        int currentValue;
        int currentAmount;
        Value banknoteValue;
        int temp = 0;

        while (banknotesIterator.hasNext()) {
            banknote = banknotesIterator.next();
            banknoteValue = banknote.getValue();
            currentValue = banknote.getValue().getValue();
            currentAmount = banknote.getAmount();
            if (amount >= currentValue) {
                temp = amount / currentValue;
                if (temp <= currentAmount)
                    delivery.add(new BanknotesImpl(banknoteValue, temp));
                else {
                    temp = currentAmount;
                    delivery.add(new BanknotesImpl(banknoteValue, temp));
                }
            }
            amount -= temp*currentValue;
        }
        if (amount != 0)
            throw new IllegalArgumentException("Введите другую сумму");
        else {
            updateStorage(delivery, storage);
            return delivery;
        }
    }

    private int getCurrentSum() {
        int currentSum = 0;

        for (Banknotes banknotes : storage)
            currentSum += banknotes.getValue().getValue() * banknotes.getAmount();
        return currentSum;
    }

    public void updateStorage(Set<Banknotes> delivery, Set<Banknotes> storage) {
        Set<Banknotes>newStorage = new TreeSet<>();
        delivery.toArray();
        storage.toArray();

        for(Banknotes source : storage) {
            for (Banknotes removed : delivery) {
                if (source.equals(removed)) {
                    int amount = source.getAmount() - removed.getAmount();
                    newStorage.add(new BanknotesImpl(source.getValue(), amount));
                }
            }
        }
        StorageImpl.storage = newStorage;
        System.out.println("Остаток: " + getCurrentSum());
    }
}
