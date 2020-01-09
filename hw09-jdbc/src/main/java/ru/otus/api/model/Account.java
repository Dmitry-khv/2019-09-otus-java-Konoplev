package ru.otus.api.model;

import ru.otus.api.annotation.Id;

import java.util.Objects;

public class Account {

    @Id
    private final long no;
    private String type;
    private double rest;


    public Account(long no, String type, double rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRest() {
        return rest;
    }

    public void setRest(double rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return no == account.no &&
                Double.compare(account.rest, rest) == 0 &&
                Objects.equals(type, account.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no, type, rest);
    }
}
