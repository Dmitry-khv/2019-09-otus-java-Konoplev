package ru.otus.api.service;

import ru.otus.api.model.User;


public interface DBServiceUser {
    long saveUser(User user);

    void updateUser(User user);

    User getUser(long id);
}
