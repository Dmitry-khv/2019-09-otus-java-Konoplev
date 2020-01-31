package ru.otus.serverHW.database.api.dao;

public class UserDaoException extends RuntimeException {
    public UserDaoException(Exception e) {
        super(e);
    }
}
