package ru.otus.cache.api.service;


import ru.otus.cache.api.model.User;

import java.util.List;

public interface DBServiceUser {
    long saveUser(User user);

    List<User> getUserList();

    void updateUser(User user);

    User getUser(long id);
}
