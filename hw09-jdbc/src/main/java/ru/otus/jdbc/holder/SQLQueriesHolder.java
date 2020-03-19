package ru.otus.jdbc.holder;


public interface SQLQueriesHolder {
    String saveSQL(Class<?> clazz);
    String updateSQL(Class<?> clazz);
    String loadSQL(Class<?> clazz);
}
