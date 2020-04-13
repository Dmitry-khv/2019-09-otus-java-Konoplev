package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.service.DBServiceUser;
import java.util.List;

@Controller
public class AdminController {
    private final DBServiceUser serviceUser;

    public AdminController(DBServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }


    @GetMapping("/admin")
    public String adminView() {
        return "admin";
    }

    @GetMapping("/admin/save")
    public String saveUserView(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @GetMapping("/admin/users")
    public String adminUsersView(Model model) {
        List<User> list = serviceUser.getUserList();
        model.addAttribute("users", list);
        return "admin";
    }


    @PostMapping("/admin/save")
    public RedirectView saveUser(@ModelAttribute("user") User user) {
        serviceUser.saveUser(user);
        return new RedirectView("/admin", true);
    }


}
