package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.AccountDao;
import ru.otus.api.model.Account;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<Account> dbExecutor;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }


    @Override
    public Optional<Account> findByOn(long on) {
        return Optional.empty();
    }

    @Override
    public void saveAccount(Account account) {

    }
}
