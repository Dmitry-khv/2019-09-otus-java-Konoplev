package ru.otus.cache.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.api.repository.UserRepository;
import ru.otus.cache.api.repository.UserRepositoryException;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.sessionmanager.SessionManager;

import java.util.List;


public class DBServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DBServiceUserImpl.class);

    private final UserRepository userRepository;

    public DBServiceUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public long saveUser(User user) {
        try(SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userRepository.save(user);
                sessionManager.commitSession();
                logger.info("created user {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public List<User> getUserList() {
        List<User> listUsers;
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                listUsers = userRepository.getListUsers();
                sessionManager.commitSession();
                logger.info("Users {} were got", listUsers);
                return listUsers;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new UserRepositoryException(e);
            }
        }
    }

    @Override
    public void updateUser(User user) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userRepository.update(user);
                sessionManager.commitSession();
                logger.info("User {} has updated", user.getName());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
    }

    @Override
    public User getUser(long id) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                User user = userRepository.findById(id);
                sessionManager.commitSession();
                logger.info("User {} has updated", id);
                return user;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new UserRepositoryException(e);
            }
        }
    }
}
