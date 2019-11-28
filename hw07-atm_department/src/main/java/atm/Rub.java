package atm;

import java.util.Objects;

public class Rub implements Banknotes {
    private final Value value;
    private int amount;

    public Rub(Value value, int amount) {
        this.value = value;
        this.amount = amount;
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
        Rub banknotes = (Rub) o;
        return value == banknotes.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}