package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRep;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRep userRep;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImp(UserRep userRep, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.userRep = userRep;
    }

    @Override
    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRep.save(user);
    }

    @Override
    public void update(User user) throws AccountNotFoundException {
        if (userRep.findById(user.getId()).isPresent()) {
            user.setPassword(encoder.encode(user.getPassword()));
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
