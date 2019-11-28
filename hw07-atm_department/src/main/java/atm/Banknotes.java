package atm;

public interface Banknotes extends Comparable<Banknotes> {
    Value getValue();
    int getAmount();
}