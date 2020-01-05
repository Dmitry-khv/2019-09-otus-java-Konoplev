package ru.otus.api.dao;

import ru.otus.api.model.User;
import ru.otus.api.sessionmanager.SessionManager;

public interface UserDao {

    long save(User user);

    User findById(long id);

    void update(User user);

    SessionManager getSessionManager();
}
