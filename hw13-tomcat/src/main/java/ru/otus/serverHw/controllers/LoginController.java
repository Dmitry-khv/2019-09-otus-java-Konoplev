package ru.otus.serverHw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.cache.api.model.Login;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.service.DBServiceUser;
import ru.otus.serverHw.services.UserAuthService;


@Controller
public class LoginController {

    private final UserAuthService userAuthService;

    public LoginController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @RequestMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/admin")
    public String authorityPost(@ModelAttribute Login login) {

        if (userAuthService.authenticate(login))
            return "admin";
        else {
            return "login";
        }
    }


}
