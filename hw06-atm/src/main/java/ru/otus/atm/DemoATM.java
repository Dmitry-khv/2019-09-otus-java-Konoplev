package ru.otus.atm;


import java.util.Set;

public class DemoATM {
    public static void main(String[] args) {
        ATM atm = new ATMImpl();
        atm.putToCassette(new Rub(Value.ONE_HUNDRED, 10));
        atm.putToCassette(new Rub(Value.ONE_HUNDRED, 10));
        atm.putToCassette(new Rub(Value.FIVE_HUNDREDS, 10));
        atm.putToCassette(new Rub(Value.ONE_THOUSAND, 10));
        atm.putToCassette(new Rub(Value.FIVE_THOUSANDS, 10));

        Set<Banknotes> banknotes = atm.takeMoney(66800);
        for (Banknotes banknote : banknotes)
            System.out.println(banknote.getValue() + ": " + banknote.getAmount() + "шт.");
        System.out.println(atm.getBalance());
    }
}
