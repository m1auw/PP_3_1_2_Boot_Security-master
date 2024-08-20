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
            adminRole.setName("ROLE_ADMIN");
            userRole.setName("ROLE_USER");
            roleRepository.saveAll(List.of(adminRole, userRole));

            List<Role> aroles = List.of(adminRole, userRole);
            List<Role> uroles = List.of(userRole);

            User admin = new User();
            admin.setName("admin");
            admin.setPassword(encodePassword("admin"));
            admin.setEmail("admin@admin.com");
            admin.setRoles(aroles);
            userRepository.save(admin);
            System.out.println("Added admin");

            User user = new User();
            user.setName("user");
            user.setPassword(encodePassword("user"));
            user.setEmail("user@user.com");
            user.setRoles(uroles);
            userRepository.save(user);
            System.out.println("Added user");
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}