package ru.otus.api.service;

import ru.otus.api.model.User;

import java.util.Optional;

public interface DBServiceModel<T> {

  void create(T objectData);

  void update(T objectData);

  void createOrUpdate(T objectData); // опционально.

  <T> T load(long id, Class<T> clazz);

}
