package ru.otus.serverHw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.service.DBServiceUser;
import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class AdminController {
    private final DBServiceUser serviceUser;

    public AdminController(DBServiceUser serviceUser) {
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

    @GetMapping("/admin/users")
    public String usersView(Model model) {
        List<User> list = serviceUser.getUserList();
        model.addAttribute("users", list);
        return "admin";
    }

    @PatchMapping("/admin/save")
    public RedirectView saveUser(@ModelAttribute User user) {
        serviceUser.saveUser(user);
        return new RedirectView("/admin", true);
    }


}
