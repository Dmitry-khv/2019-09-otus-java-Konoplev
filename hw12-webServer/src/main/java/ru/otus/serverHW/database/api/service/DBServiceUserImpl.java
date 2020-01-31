package ru.otus.serverHW.database.api.service;

import org.h2.engine.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.serverHW.database.api.dao.UserDao;
import ru.otus.serverHW.database.api.dao.UserDaoException;
import ru.otus.serverHW.database.api.sessionmanager.SessionManager;

public class DBServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DBServiceUserImpl.class);

    private final UserDao userDao;

    public DBServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public long saveUser(User user) {
        try(SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.save(user);
                sessionManager.commitSession();
                logger.info("created user {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void updateUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.update(user);
                sessionManager.commitSession();
                logger.info("User {} has updated", user.getName());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
    }

    @Override
    public User getUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                User user = userDao.findById(id);
                sessionManager.commitSession();
                logger.info("User {} has updated", id);
                return user;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new UserDaoException(e);
            }
        }
    }
}
