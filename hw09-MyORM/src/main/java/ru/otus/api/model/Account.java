package ru.otus.api.model;

public class Account {
    private final long no;
    private final String type;
    private final double number;

    public Account(long no, String type, double number) {
        this.no = no;
        this.type = type;
        this.number = number;
    }

    public long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public double getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", number=" + number +
                '}';
    }
}
