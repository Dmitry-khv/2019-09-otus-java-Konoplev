package ru.otus.cache.api.repository;

import ru.otus.cache.api.model.User;
import ru.otus.cache.api.sessionmanager.SessionManager;

import java.util.List;

public interface UserRepository {

    long save(User user);

    User findById(long id);

    void update(User user);

    SessionManager getSessionManager();

    List<User> getListUsers();
}
