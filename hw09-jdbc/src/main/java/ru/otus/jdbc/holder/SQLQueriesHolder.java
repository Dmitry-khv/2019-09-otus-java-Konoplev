package ru.otus.jdbc.holder;


public interface SQLQueriesHolder {
    String getSaveQuery(Class<?> clazz);
    String getUpdateQuery(Class<?> clazz);
    String getLoadQuery(Class<?> clazz);
}
