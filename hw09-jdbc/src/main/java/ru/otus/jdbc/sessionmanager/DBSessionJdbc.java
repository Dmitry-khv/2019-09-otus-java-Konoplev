package ru.otus.jdbc.sessionmanager;

import ru.otus.api.sessionmanager.DatabaseSession;

import java.sql.Connection;

public class DBSessionJdbc implements DatabaseSession {
    private final Connection connection;

    public DBSessionJdbc(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
