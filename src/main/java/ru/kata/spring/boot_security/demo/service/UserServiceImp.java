package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRep;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRep userRep;

    @Autowired
    public UserServiceImp(UserRep userRep) {
        this.userRep = userRep;
    }

    @Override
    public void save(User user) {
        userRep.save(user);
    }

    // Так? Не так?
    @Override
    public void update(User user) throws AccountNotFoundException {
        if (userRep.findById(user.getId()).isPresent()) {
            userRep.save(user);
        } else {
            throw new AccountNotFoundException();
        }
    }

    @Override
    public void delete(Long id) throws AccountNotFoundException {
        User user = userRep.findById(id).orElse(null);
        if (user != null) {
            userRep.delete(user);
        } else {
            throw new AccountNotFoundException();
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRep.findById(id).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRep.findAll();
    }

    @Override
    public User getUserByName(String name) {
        return userRep.findByName(name);
    }
}
