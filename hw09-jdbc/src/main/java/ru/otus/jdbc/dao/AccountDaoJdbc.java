package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.AccountDao;
import ru.otus.api.dao.UserDaoException;
import ru.otus.api.model.Account;
import ru.otus.api.sessionmanager.SessionManager;
import ru.otus.jdbc.DBExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;

public class AccountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

    private final SessionManagerJdbc sessionManagerJdbc;


    public AccountDaoJdbc(SessionManagerJdbc sessionManagerJdbc) {
        this.sessionManagerJdbc = sessionManagerJdbc;
    }

    @Override
    public long save(Account account) {
        try {
            DBExecutor<Account> dbExecutor = new DBExecutor<>(getConnection());
            return dbExecutor.save(account);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public Account findByNo(long id) {
        try {
            DBExecutor<Account> dbExecutor = new DBExecutor<>(getConnection());
            return dbExecutor.load(Account.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(Account account) {
        try {
            DBExecutor<Account> dbExecutor = new DBExecutor<>(getConnection());
            dbExecutor.update(account);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManagerJdbc;
    }

    private Connection getConnection() {
        return sessionManagerJdbc.getCurrentSession().getConnection();
    }
}
