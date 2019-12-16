package ru.otus.jdbc;

import ru.otus.api.model.Model;

public interface JdbcTemplate <T> {
    void create(T objectData);
    void update(T objectData);
    T load(long id, Class<T> clazz);
}
