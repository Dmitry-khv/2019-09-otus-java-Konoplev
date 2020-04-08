package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.service.DBServiceUser;


@Controller
public class LoginController {
    private final DBServiceUser serviceUser;

    public LoginController(DBServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        createAdmin();
    }

    public void createAdmin() {
        User admin = new User();
        admin.setRole(User.ROLE.ADMIN);
        admin.setLogin("admin");
        admin.setPassword("11111");
        serviceUser.saveUser(admin);
    }

    @GetMapping("/")
    public String adminPage() {
        return "index.html";
    }


    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login.html";
    }

    @PostMapping("/login")
    public RedirectView submitLogin(@ModelAttribute("user") User user) {

        boolean result = serviceUser.getUserList().stream()
                .anyMatch(usr -> usr.getRole().equals(User.ROLE.ADMIN)
                        && usr.getLogin().equals(user.getLogin())
                        && usr.getPassword().equals(user.getPassword()));

        if (result)
            return new RedirectView("/admin", true);
        else
            return new RedirectView("/login", true);
    }
}
