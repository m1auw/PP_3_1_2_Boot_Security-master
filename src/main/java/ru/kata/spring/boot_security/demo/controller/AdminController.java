package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.security.auth.login.AccountNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String adminPage(Model model, Principal principal) {
        User admin = userService.getUserByName(principal.getName());
        model.addAttribute("admin", admin);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/add")
    public String showAddFrom(Model model) {
        model.addAttribute("userAdd", new User());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String name, @RequestParam String email, @RequestParam String password,
                          @RequestParam(required = false) boolean isAdmin) {

        User user = new User(name, email, passwordEncoder.encode(password));

        adminRole(isAdmin, user);
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String showEditFrom(Model model, @RequestParam Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("userEdit", user);
        return "admin";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute Long id, @ModelAttribute String name, @ModelAttribute String email,
                           @ModelAttribute String password, @RequestParam(required = false) boolean isAdmin)
            throws AccountNotFoundException {

        User user = userService.getUserById(id);
        user.setName(name);
        user.setEmail(email);

        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        adminRole(isAdmin, user);

        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) throws AccountNotFoundException {
        userService.delete(id);
        return "redirect:/admin";
    }

    private void adminRole(@RequestParam(required = false) boolean isAdmin, User user) {
        List<Role> roles = new ArrayList<>();
        Role userRole = roleService.findByName("ROLE_USER");
        roles.add(userRole);

        if (isAdmin) {
            Role adminRole = roleService.findByName("ROLE_ADMIN");
            roles.add(adminRole);
        }

        user.setRoles(roles);
    }
}
