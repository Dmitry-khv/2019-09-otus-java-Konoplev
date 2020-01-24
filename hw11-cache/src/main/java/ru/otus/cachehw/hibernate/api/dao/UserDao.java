package ru.otus.cachehw.hibernate.api.dao;

import ru.otus.cachehw.hibernate.api.model.User;
import ru.otus.cachehw.hibernate.api.sessionmanager.SessionManager;

public interface UserDao {

    long save(User user);

    User findById(long id);

    void update(User user);

    SessionManager getSessionManager();
}
