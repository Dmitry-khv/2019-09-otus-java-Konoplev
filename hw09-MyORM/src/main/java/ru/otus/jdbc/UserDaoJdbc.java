package ru.otus.jdbc;

import ru.otus.api.dao.UserDao;
import ru.otus.api.model.User;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    @Override
    public Optional<User> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void saveUser(User user) {

    }
}
