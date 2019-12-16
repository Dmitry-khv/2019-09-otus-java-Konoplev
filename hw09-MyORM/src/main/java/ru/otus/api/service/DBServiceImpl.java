package ru.otus.api.service;


import java.util.Optional;

public class DBServiceImpl<T> implements DBService {
    private final T obj;

    public DBServiceImpl(T obj) {
        this.obj = obj;
    }

    @Override
    public Optional<T> getObject(long id) {
        return Optional.empty();
    }

    @Override
    public long saveObject(Object obj) {
        return 0;
    }
}
