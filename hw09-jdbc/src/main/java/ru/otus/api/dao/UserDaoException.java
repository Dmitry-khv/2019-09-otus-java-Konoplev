package ru.otus.api.dao;

public class UserDaoException extends RuntimeException {
    public UserDaoException(Exception e) {
        super(e);
    }
}
