package ru.otus.api.model;

public class Account implements Model{
    private long no;
    private final String type;
    private double number;

    public Account(String type, double number) {
        this.type = type;
        this.number = number;
    }

    public void setNo(long no) {
        this.no = no;
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

    public void setNumber(double number) {
        this.number = number;
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
