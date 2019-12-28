package ru.otus.api.dao;

import ru.otus.api.model.Account;
import ru.otus.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    long save (Account account);

    Account findByNo (long id);

    void update(Account account);

    SessionManager getSessionManager();
}
