package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.service.DBServiceUser;

import javax.annotation.PostConstruct;

@Service
public class InitialDBService {

    private final DBServiceUser serviceUser;

    public InitialDBService(DBServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @PostConstruct
    public void createAdmin() {
        User admin = new User();
        admin.setRole(User.ROLE.ADMIN);
        admin.setLogin("admin");
        admin.setPassword("11111");
        serviceUser.saveUser(admin);
    }
}
