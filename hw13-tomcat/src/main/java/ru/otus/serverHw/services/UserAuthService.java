package ru.otus.serverHw.services;

import ru.otus.cache.api.model.Login;

public interface UserAuthService {
    boolean authenticate(Login login);
}
