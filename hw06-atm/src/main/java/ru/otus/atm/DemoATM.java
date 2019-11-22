package ru.otus.atm;

import java.util.Set;

public class DemoATM {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.putMoney(Value.ONE_HUNDRED, 10);
        atm.putMoney(Value.ONE_HUNDRED, 10);
        atm.putMoney(Value.FIVE_HUNDREDS, 10);
        atm.putMoney(Value.ONE_THOUSAND, 10);
        atm.putMoney(Value.FIVE_THOUSANDS, 10);

        Set<Banknotes> banknotes = atm.getMoney(67000);
        for (Banknotes banknote : banknotes)
            System.out.println(banknote.getValue() + ": " + banknote.getAmount() + "шт.");
    }
}
