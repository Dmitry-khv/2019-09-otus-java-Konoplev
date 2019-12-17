package ru.otus.api.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DBServiceImpl<T> implements DBService {
    private static Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

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
