package ru.otus.jdbc;

public interface JdbcTemplate {
    <T> void create(T objectData);
    <T> void update(T objectData);
    <T> T load(long id, Class<T> clazz);
}
