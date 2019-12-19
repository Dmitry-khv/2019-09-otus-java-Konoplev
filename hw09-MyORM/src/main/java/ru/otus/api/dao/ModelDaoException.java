package ru.otus.api.dao;

public class ModelDaoException extends RuntimeException {
  public ModelDaoException(Exception ex) {
    super(ex);
  }
}
