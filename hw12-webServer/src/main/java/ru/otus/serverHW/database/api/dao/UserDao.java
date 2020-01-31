package ru.otus.serverHW.database.api.dao;

import org.h2.engine.User;
import ru.otus.serverHW.database.api.sessionmanager.SessionManager;

public interface UserDao {

    long save(User user);

    User findById(long id);

    void update(User user);

    SessionManager getSessionManager();
}
