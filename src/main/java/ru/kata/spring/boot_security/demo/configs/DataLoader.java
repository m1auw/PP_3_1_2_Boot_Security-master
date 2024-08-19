package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRep;
import ru.kata.spring.boot_security.demo.repository.UserRep;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRep roleRepository;
    private final UserRep userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(RoleRep roleRepository, UserRep userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        loadRoles();
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            Role userRole = new Role();
            adminRole.setName("ADMIN");
            userRole.setName("USER");
            roleRepository.saveAll(List.of(adminRole, userRole));

            List<Role> roles = List.of(adminRole, userRole);

            User admin = new User();
            admin.setName("admin");
            admin.setPassword(encodePassword("admin"));
            admin.setEmail("admin@admin.com");
            admin.setRoles(roles);
            userRepository.save(admin);
            System.out.println("Added admin");
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}