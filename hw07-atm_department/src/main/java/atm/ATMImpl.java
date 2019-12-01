package atm;

import atm_departmen.Memento;

import java.util.*;

public class ATMImpl implements ATM, Cloneable {
    private Set<Banknotes> cassettes;

    public ATMImpl(Set<Banknotes> givenCassettes) {
        cassettes = givenCassettes;
    }

    @Override
    public void putToCassette(Banknotes...newBanknotes) {
        for (Banknotes banknote : newBanknotes) {
            int amountOfNewBanknote = banknote.getAmount();
            Iterator<Banknotes> oldBanknotes = cassettes.iterator();
            while (oldBanknotes.hasNext()) {
                Banknotes oldBanknote = oldBanknotes.next();
                if (oldBanknote.equals(banknote)) {
                    amountOfNewBanknote += oldBanknote.getAmount();
                    oldBanknotes.remove();
                    break;
                }
            }
            cassettes.add(new Rub(banknote.getValue(), amountOfNewBanknote));
        }
    }

    private Set<Banknotes> createBundleForDeliver(int amount) {
        Set<Banknotes> delivery = new TreeSet<>();
        Iterator<Banknotes> banknotesIterator = cassettes.iterator();
        Banknotes banknote;
        Value banknoteValue;
        int currentValue;
        int currentAmount;
        int temp = 0;

        while (banknotesIterator.hasNext()) {
            banknote = banknotesIterator.next();
            banknoteValue = banknote.getValue();
            currentValue = banknoteValue.getValue();
            currentAmount = banknote.getAmount();

            if (amount >= currentValue) {
                temp = amount / currentValue;
                if (temp <= currentAmount)
                    delivery.add(new Rub(banknoteValue, temp));
                else {
                    temp = currentAmount;
                    delivery.add(new Rub(banknoteValue, temp));
                }
            }
            amount -= temp*currentValue;
        }

        if (amount == 0) {
            updateStorage(delivery, cassettes);
            return delivery;
        }
        else
            throw new IllegalArgumentException("Введите другую сумму");
    }

    @Override
    public Set<Banknotes> takeMoney(int amount) {
        if (isMoneyEnough(amount)) {
            return createBundleForDeliver(amount);
        }
        throw new IllegalArgumentException("Сумма превышает допустимую");
    }

    private void updateStorage(Set<Banknotes> delivery, Set<Banknotes> cassettes) {
        Set<Banknotes>tempStorage = new TreeSet<>();
        delivery.toArray();
        cassettes.toArray();

        for(Banknotes beforeDelivery : cassettes) {
            for (Banknotes removed : delivery) {
                if (beforeDelivery.equals(removed)) {
                    int amount = beforeDelivery.getAmount() - removed.getAmount();
                    tempStorage.add(new Rub(beforeDelivery.getValue(), amount));
                }
            }
        }
        this.cassettes = tempStorage;
    }

    @Override
    public Integer getCurrentBalance() {
        int currentSum = 0;

        for (Banknotes banknotes : cassettes)
            currentSum += banknotes.getValue().getValue() * banknotes.getAmount();
        return currentSum;
    }

    @Override
    public ATM clone() {
        Set<Banknotes> cassettesCopy = new TreeSet<>(cassettes);
        return new ATMImpl(cassettesCopy);
    }

    @Override
    public Memento saveState() {
        return new Memento(clone());
    }

    private boolean isMoneyEnough(int amount) {
        return amount <= getCurrentBalance();
    }

    public void restore(Set<Banknotes>old) {
        cassettes = old;
    }
}