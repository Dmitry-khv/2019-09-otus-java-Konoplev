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
    }

    @GetMapping("/")
    public String startPage() {
        return "index";
    }


    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
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
