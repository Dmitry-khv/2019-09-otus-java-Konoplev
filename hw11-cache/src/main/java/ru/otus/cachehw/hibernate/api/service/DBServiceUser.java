package ru.otus.cachehw.hibernate.api.service;

import ru.otus.cachehw.hibernate.api.model.User;


public interface DBServiceUser {
    long saveUser(User user);

    void updateUser(User user);

    User getUser(long id);
}
