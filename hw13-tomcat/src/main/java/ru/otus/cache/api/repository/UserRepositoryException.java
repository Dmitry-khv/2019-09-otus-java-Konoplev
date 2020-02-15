package ru.otus.cache.api.repository;

public class UserRepositoryException extends RuntimeException {
    public UserRepositoryException(Exception e) {
        super(e);
    }
}
