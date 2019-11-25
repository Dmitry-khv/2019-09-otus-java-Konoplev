package ru.otus.atm;


import java.util.Set;

public class ATMImpl implements ATM {
    private Storage storage = new StorageImpl();


    @Override
    public void putMoney(Banknotes banknotes) {
        storage.putToCassette(banknotes);
    }

    @Override
    public Set<Banknotes> getMoney(int amount) {
        return storage.takeMoney(amount);
    }

    @Override
    public String getBalance() {
        return String.format("Остаток в банкомате = %d руб.", storage.getCurrentSum());
    }
}

