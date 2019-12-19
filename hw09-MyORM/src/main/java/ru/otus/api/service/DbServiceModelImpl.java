package ru.otus.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.ModelDao;
import ru.otus.api.model.Model;
import ru.otus.api.sessionmanager.SessionManager;

public class DbServiceModelImpl<T> implements DBServiceModel {
  private static Logger logger = LoggerFactory.getLogger(DbServiceModelImpl.class);

  private final ModelDao modelDao;

  public DbServiceModelImpl(ModelDao modelDao) {
    this.modelDao = modelDao;
  }

  @Override
  public void create(Object objectData) {
    try (SessionManager sessionManager = modelDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        modelDao.create(objectData);
        sessionManager.commitSession();
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public void update(Object objectData) {

  }

  @Override
  public void createOrUpdate(Object objectData) {

  }

  @Override
  public Object load(long id, Class clazz) {
    return null;
  }

//  @Override
//  public long saveUser(User user) {
//    try (SessionManager sessionManager = modelDao.getSessionManager()) {
//      sessionManager.beginSession();
//      try {
//        long userId = modelDao.saveUser(user);
//        sessionManager.commitSession();
//
//        logger.info("created user: {}", userId);
//        return userId;
//      } catch (Exception e) {
//        logger.error(e.getMessage(), e);
//        sessionManager.rollbackSession();
//        throw new DbServiceException(e);
//      }
//    }
//  }
//
//
//  @Override
//  public Optional<User> getUser(long id) {
//    try (SessionManager sessionManager = userDao.getSessionManager()) {
//      sessionManager.beginSession();
//      try {
//        Optional<User> userOptional = userDao.findById(id);
//
//        logger.info("user: {}", userOptional.orElse(null));
//        return userOptional;
//      } catch (Exception e) {
//        logger.error(e.getMessage(), e);
//        sessionManager.rollbackSession();
//      }
//      return Optional.empty();
//    }
//  }


}
