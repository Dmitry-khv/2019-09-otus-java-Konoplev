package ru.otus.cache.api.dao;

import ru.otus.cache.api.model.User;
import ru.otus.cache.api.sessionmanager.SessionManager;

import java.util.List;

public interface UserDao {

    long save(User user);

    User findById(long id);

    void update(User user);

    SessionManager getSessionManager();

    List<User> getListUsers();
}
