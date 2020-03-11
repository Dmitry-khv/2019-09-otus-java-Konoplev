package ru.otus.cache.api.repository;

public class UserDaoException extends RuntimeException {
    public UserDaoException(Exception e) {
        super(e);
    }
}
