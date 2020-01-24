package ru.otus.cachehw.hibernate.api.dao;

public class UserDaoException extends RuntimeException {
    public UserDaoException(Exception e) {
        super(e);
    }
}
