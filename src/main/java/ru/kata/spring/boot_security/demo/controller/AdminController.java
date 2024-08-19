package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public String getAdmin(Model model) {
//        model.addAttribute("admin", new User());
//        return "admin";
//    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/add")
    public String showAddFrom(Model model) {
        model.addAttribute("user", new User());
        return "users/add";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        User user = new User(name, email, password);
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String showEditFrom(Model model, @RequestParam Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/add";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam Long id, @RequestParam String name, @RequestParam String email,
                           @RequestParam String password) throws AccountNotFoundException {
        User user = userService.getUserById(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userService.update(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) throws AccountNotFoundException {
        userService.delete(id);
        return "redirect:/users";
    }
}
