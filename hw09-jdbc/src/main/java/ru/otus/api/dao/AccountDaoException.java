package ru.otus.api.dao;

public class AccountDaoException extends RuntimeException {
    public AccountDaoException(Exception e) {
        super(e);
    }
}
