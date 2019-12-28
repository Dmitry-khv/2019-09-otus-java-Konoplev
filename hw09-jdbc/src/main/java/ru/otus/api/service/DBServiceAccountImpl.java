package ru.otus.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.AccountDao;
import ru.otus.api.dao.AccountDaoException;
import ru.otus.api.model.Account;
import ru.otus.api.sessionmanager.SessionManager;

import java.util.Optional;

public class DBServiceAccountImpl implements DBServiceAccount {
    private static Logger logger = LoggerFactory.getLogger(DBServiceAccountImpl.class);
    private final AccountDao accountDao;

    public DBServiceAccountImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void saveAccount(Account account) {
        try(SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long id = accountDao.save(account);
                sessionManager.commitSession();
                logger.info("account {} added", id);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
    }

    @Override
    public void updateAccount(Account account) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                accountDao.update(account);
                sessionManager.commitSession();
                logger.info("account {} updated", account.getNo());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }

    }

    @Override
    public Account getAccount(long no) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Account account = accountDao.findByNo(no);
                sessionManager.commitSession();
                logger.info("get Account {}", no);
                return account;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new AccountDaoException(e);
            }
        }
    }
}
