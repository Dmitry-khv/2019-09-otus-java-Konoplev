package ru.otus.api.service;

import java.util.Optional;

public interface DBService {
    <T> Optional<T> getObject(long id);

    long saveObject(Object obj);
}
