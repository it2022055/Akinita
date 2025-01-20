package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.Actors.User;
import Akinita.project.Akinita.Repositories.User.RoleRepository;
import Akinita.project.Akinita.Entities.Role;
import Akinita.project.Akinita.Repositories.User.UserRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthController(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void setup() {
        Role role_user = new Role("ROLE_USER");
        Role role_owner = new Role("ROLE_OWNER");
        Role role_renter = new Role("ROLE_RENTER");
        Role role_admin = new Role("ROLE_ADMIN");

        roleRepository.updateOrInsert(role_user);
        roleRepository.updateOrInsert(role_owner);
        roleRepository.updateOrInsert(role_renter);
        roleRepository.updateOrInsert(role_admin);
        //User user = new User();
        //user.setUsername("admin");
        //String encodedPassword = passwordEncoder.encode("admin");
        //user.setPassword(encodedPassword);
        //user.setEmail("admin@example.com");
        //Set<Role> roles = new HashSet<>();
        //roles.add(role_admin);
        //user.setRoles(roles);
        //userRepository.save(user);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
