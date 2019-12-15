package ru.otus.api.dao;

import ru.otus.api.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    void saveUser(User user);

}
