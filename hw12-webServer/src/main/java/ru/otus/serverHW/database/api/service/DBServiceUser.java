package ru.otus.serverHW.database.api.service;

import org.h2.engine.User;


public interface DBServiceUser {
    long saveUser(User user);

    void updateUser(User user);

    User getUser(long id);
}
