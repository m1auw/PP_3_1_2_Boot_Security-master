package ru.kata.spring.boot_security.demo.detail;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;


public class RoleGrantedAuthorityImp implements GrantedAuthority {

    private Role role;

    public RoleGrantedAuthorityImp(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName().toUpperCase();
    }
}
