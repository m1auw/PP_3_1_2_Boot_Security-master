package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface UserService {
    void save(User user);

    void update(User user) throws AccountNotFoundException;

    void delete(Long id) throws AccountNotFoundException;

    User getUserById(Long id);

    List<User> getAllUsers();

    User getUserByName(String name);
}
