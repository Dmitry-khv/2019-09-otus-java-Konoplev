package ru.otus.serverHw.services;

import ru.otus.cache.api.dao.UserDao;
import ru.otus.cache.api.model.User;

public class UserAuthServiceImpl implements UserAuthService {
    private final UserDao userDao;

    public UserAuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return userDao.getListUsers().stream()
                .anyMatch(user -> user.getRole().equals(User.ROLE.ADMIN)
                        && user.getLogin().equals(login)
                        && user.getPassword().equals(password));
    }
}
