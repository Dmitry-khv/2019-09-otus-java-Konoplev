package ru.otus.jdbc.sessionmanager;

import ru.otus.api.sessionmanager.SessionManager;
import ru.otus.api.sessionmanager.SessionManagerException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionManagerJdbc implements SessionManager {
    private static final int TIMEOUT_IN_SECONDS = 5;
    private final DataSource dataSource;
    private Connection connection;
    private DBSessionJdbc dbSessionJdbc;

    public SessionManagerJdbc(DataSource dataSource) {
        if (dataSource == null)
            new Exception("Datasource is null").printStackTrace();
        this.dataSource = dataSource;
    }

    @Override
    public void beginSession() {
        try {
            connection = dataSource.getConnection();
            dbSessionJdbc = new DBSessionJdbc(connection);
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void commitSession() {
        checkConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void rollbackSession() {
        checkConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void close() {
        checkConnection();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public DBSessionJdbc getCurrentSession() {
        checkConnection();
        return dbSessionJdbc;
    }

    private void checkConnection() {
        try {
            if (connection == null || !connection.isValid(TIMEOUT_IN_SECONDS))
                System.out.println("Connection is invalid");
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }
}
