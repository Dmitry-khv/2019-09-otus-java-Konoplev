package ru.otus.api.dao;

import ru.otus.api.model.User;
import ru.otus.api.sessionmanager.SessionManager;

import java.sql.SQLException;
import java.util.Optional;

public interface ModelDao<T> {
  void create(T objectData) throws SQLException, IllegalAccessException;

  void update(T objectData);

  void createOrUpdate(T objectData); // опционально.

  <T> T load(long id, Class<T> clazz);

  SessionManager getSessionManager();
}
