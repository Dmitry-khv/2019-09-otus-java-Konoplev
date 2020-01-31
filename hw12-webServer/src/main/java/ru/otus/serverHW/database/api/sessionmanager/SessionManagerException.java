package ru.otus.serverHW.database.api.sessionmanager;

public class SessionManagerException extends RuntimeException {
    public SessionManagerException(String msg) {
        super(msg);
    }
    public SessionManagerException(Exception e) {
        super(e);
    }
}
