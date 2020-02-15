package ru.otus.serverHw.services;

import ru.otus.cache.api.model.User;
import ru.otus.cache.api.repository.UserRepository;

public class UserAuthServiceImpl implements UserAuthService {
    private final UserRepository userRepository;

    public UserAuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return userRepository.getListUsers().stream()
                .anyMatch(user -> user.getRole().equals(User.ROLE.ADMIN)
                        && user.getLogin().equals(login)
                        && user.getPassword().equals(password));
    }
}
