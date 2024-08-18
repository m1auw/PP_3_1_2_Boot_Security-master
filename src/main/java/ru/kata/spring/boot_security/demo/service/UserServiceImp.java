package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRep;

import java.util.List;

@Service
@Transactional
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

    @Override
    public void delete(Long id) {
        userRep.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRep.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRep.findAll();
    }
}
