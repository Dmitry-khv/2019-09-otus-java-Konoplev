package ru.otus.cache.api.dao;

public class UserDaoException extends RuntimeException {
    public UserDaoException(Exception e) {
        super(e);
    }
}
