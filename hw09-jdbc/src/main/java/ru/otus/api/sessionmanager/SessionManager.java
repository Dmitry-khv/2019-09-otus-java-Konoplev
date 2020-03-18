package ru.otus.api.sessionmanager;

import ru.otus.jdbc.sessionmanager.DBSessionJdbc;

public interface SessionManager extends AutoCloseable {
    void beginSession();

    void commitSession();

    void rollbackSession();

    void close();

    DatabaseSession getCurrentSession();
}
