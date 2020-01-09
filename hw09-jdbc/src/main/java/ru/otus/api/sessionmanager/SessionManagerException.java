package ru.otus.api.sessionmanager;

public class SessionManagerException extends RuntimeException {
    public SessionManagerException(Exception e) {
        super(e);
    }
}
