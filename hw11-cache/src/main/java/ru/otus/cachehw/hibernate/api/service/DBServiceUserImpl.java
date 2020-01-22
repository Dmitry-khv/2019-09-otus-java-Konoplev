package ru.otus.cachehw.hibernate.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.cache.BigObject;
import ru.otus.cachehw.cache.HwListener;
import ru.otus.cachehw.cache.MyCache;
import ru.otus.cachehw.hibernate.api.dao.UserDao;
import ru.otus.cachehw.hibernate.api.dao.UserDaoException;
import ru.otus.cachehw.hibernate.api.model.User;
import ru.otus.cachehw.hibernate.api.sessionmanager.SessionManager;

public class DBServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DBServiceUserImpl.class);
    private final MyCache<String, User> cache;
    private final UserDao userDao;



    public DBServiceUserImpl(UserDao userDao, MyCache<String, User> cache) {
        this.userDao = userDao;
        this.cache = cache;
    }


    @Override
    public long saveUser(User user) {
        try(SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.save(user);
                sessionManager.commitSession();
                logger.info("created user {}", userId);
                cache.put(String.valueOf(userId), user);
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
                cache.put(String.valueOf(user.getId()), user);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
    }

    @Override
    public User getUser(long id) {
        if(cache.get(String.valueOf(id)) != null)
            return cache.get(String.valueOf(id));
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                User user = userDao.findById(id);
                sessionManager.commitSession();
                logger.info("Got user {} ", id);
                cache.put(String.valueOf(user.getId()), user);
                return user;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new UserDaoException(e);
            }
        }
    }

    public void addCacheListener() {
        HwListener<String, User> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);
        cache.addListener(listener);
    }
}
