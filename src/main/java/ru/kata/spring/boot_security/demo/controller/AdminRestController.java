package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.security.auth.login.AccountNotFoundException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public User getAdmin(Principal principal) {
        return userService.getUserByName(principal.getName());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws AccountNotFoundException {
        User userEdit = userService.getUserById(id);
        userEdit.setName(user.getName());
        userEdit.setEmail(user.getEmail());
        if (user.getPassword() != null) {
            userEdit.setPassword(user.getPassword());
        }
        userEdit.setRoles(user.getRoles());
        userService.update(userEdit);
        return ResponseEntity.ok(userEdit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws AccountNotFoundException {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
