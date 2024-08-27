package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRep;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRep roleRep;

    @Autowired
    public RoleServiceImp(RoleRep roleRep) {
        this.roleRep = roleRep;
    }

    @Override
    public Role findByName(String name) {
        return roleRep.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRep.findAll();
    }
}
