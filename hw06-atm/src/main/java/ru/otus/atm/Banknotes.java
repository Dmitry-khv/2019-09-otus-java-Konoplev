package ru.otus.atm;


public interface Banknotes extends Comparable<Banknotes>{
    void putToStorage();
    Value getValue();
    int getAmount();
}
