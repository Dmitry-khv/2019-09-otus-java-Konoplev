package ru.otus.serverHw.services;

import org.springframework.stereotype.Service;
import ru.otus.cache.api.model.Login;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.repository.UserRepository;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    private final UserRepository userRepository;

    public UserAuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(Login login) {
        String userName = login.getUsername();
        String password = login.getPassword();

        return userRepository.getListUsers().stream()
                .anyMatch(user -> user.getRole().equals(User.ROLE.ADMIN)
                        && user.getLogin().equals(userName)
                        && user.getPassword().equals(password));
    }
}
