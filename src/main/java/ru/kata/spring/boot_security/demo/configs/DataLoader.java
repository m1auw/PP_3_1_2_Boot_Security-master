package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRep;
import ru.kata.spring.boot_security.demo.repository.UserRep;

import java.util.HashSet;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRep roleRepository;
    private final UserRep userRepository;

    @Autowired
    public DataLoader(RoleRep roleRepository, UserRep userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadRoles();
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            Role userRole = new Role();
            adminRole.setRole("ADMIN");
            userRole.setRole("USER");

            List<Role> roles = roleRepository.saveAll(List.of(adminRole, userRole));

            // Ничего не работает опять

//            User admin = new User("admin","admin@admin","admin", new HashSet<>(roles));
//            userRepository.save(admin);
//            System.out.println("Added admin");

            // Я хотела еще в контроллере сделать чтобы админ мог давать роли создаваемым пользователям,
            // но оно не хочет работать
            // Или как оно должно понимать кто будет админом, а кто юзером?
            // Я опять что-то делаю не так
        }
    }
}