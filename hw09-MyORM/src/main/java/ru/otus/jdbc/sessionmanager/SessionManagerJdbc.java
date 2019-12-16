package ru.otus.jdbc.sessionmanager;

import ru.otus.api.sessionmanager.DatabaseSession;
import ru.otus.api.sessionmanager.SessionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionManagerJdbc implements SessionManager {

    private static final int TIMEOUT_IN_SECONDS = 5;
    private final DataSource dataSource;
    private Connection connection;
    private DatabaseSessionJdbc databaseSession;

    public SessionManagerJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void beginSession() {
        try {
            connection = dataSource.getConnection();
            databaseSession = new DatabaseSessionJdbc(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commitSession() {
        checkConnection();
        try {
            connection.commit();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollbackSession() {
        checkConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        checkConnection();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DatabaseSessionJdbc getCurrentSession() {
        checkConnection();
        return databaseSession;
    }

    private void checkConnection() {
        try {
            if (connection == null || !connection.isValid(TIMEOUT_IN_SECONDS))
                throw new SQLException("connection is invalid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
