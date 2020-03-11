package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.cache.api.model.User;
import ru.otus.cache.api.repository.UserDao;
import ru.otus.services.UserAuthService;

import java.net.http.HttpClient;


@Controller
public class LoginController {
    private final UserAuthService userAuthService;
    private final UserDao userDao;

    public LoginController(UserAuthService userAuthService, UserDao userDao) {
        this.userAuthService = userAuthService;
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String adminPage() {
        return "index";
    }

//    @PostMapping("/login")
//    public String loginView(@ModelAttribute User user) {
//        return "login";
//    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public RedirectView submitLogin(@ModelAttribute("user") User user, RedirectAttributes attributes) {

        boolean result = userDao.getListUsers().stream()
                .anyMatch(usr -> usr.getRole().equals(User.ROLE.ADMIN)
                        && usr.getLogin().equals(user.getLogin())
                        && usr.getPassword().equals(user.getPassword()));

        if (result)
            return new RedirectView("/admin", true);
        else
            return new RedirectView("/login", true);
    }

//    @PostMapping("/login")
//    public String authorityPost(@ModelAttribute User user){
//        String login = user.getLogin();
//        String password = user.getPassword();
//
//        return "admin";
//    }



//
//    private final UserAuthS userAuthService;
//    private final UserDao userDao;
//
//    public LoginController(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    public LoginController(UserAuthService userAuthService) {
//        this.userAuthService = userAuthService;
//    }
//
//    @GetMapping("/login")
//    public String loginView() {
//        return "login";
//    }
//
//    @PostMapping("/admin")
//    public String authorityPost(@ModelAttribute Login login) {
//
//        boolean result = userDao.getListUsers().stream()
//                .anyMatch(user -> user.getRole().equals(User.ROLE.ADMIN)
//                        && user.getLogin().equals(login.getUsername())
//                        && user.getPassword().equals(login.getPassword()));
//
//        if(result)
//            return "admin";
//        else
//            return "login";
//    }


}
