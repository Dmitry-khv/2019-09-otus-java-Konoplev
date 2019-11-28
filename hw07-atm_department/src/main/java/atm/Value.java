package atm;

public enum Value {
    ONE_HUNDRED(100),
    FIVE_HUNDREDS(500),
    ONE_THOUSAND(1000),
    FIVE_THOUSANDS(5000);

    private int numValue;

    Value(int numValue) {
        this.numValue = numValue;
    }

    public int getValue() {
        return numValue;
    }
}