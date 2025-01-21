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

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
public class AuthController {
    @Autowired
    UserRepository userRepository; //Δήλωση του user repository
    @Autowired
    RoleRepository roleRepository; //Δήλωση του roleRepository
    @Autowired
    private BCryptPasswordEncoder passwordEncoder; //Δήλωση του passwordEncoder

    public AuthController(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void setup() {
        Role role_user = new Role("ROLE_USER"); //Δήλωση του ρόλου User
        Role role_owner = new Role("ROLE_OWNER"); //Δήλωση του ρόλου Owner
        Role role_renter = new Role("ROLE_RENTER"); //Δήλωση του ρόλου Renter
        Role role_admin = new Role("ROLE_ADMIN"); // Δήλωση του ρόλου Admin

        roleRepository.updateOrInsert(role_user); //Προσθήκη ρόλου user στον πίνακα με τους ρόλους
        roleRepository.updateOrInsert(role_owner); //Προσθήκη ρόλου user στον πίνακα με τους ρόλους
        roleRepository.updateOrInsert(role_renter); //Προσθήκη ρόλου user στον πίνακα με τους ρόλους
        roleRepository.updateOrInsert(role_admin); //Προσθήκη ρόλου user στον πίνακα με τους ρόλους
        //User user = new User(); //Δημιουργία admin
        //user.setUsername("admin"); //Username admin
        //String encodedPassword = passwordEncoder.encode("admin"); //Password admin
        //user.setPassword(encodedPassword);
        //user.setEmail("admin@example.com"); //Email admin
        //Set<Role> roles = new HashSet<>();
        //roles.add(role_admin); //Εισαγωγή του ρόλου admin
        //user.setRoles(roles);
        //userRepository.save(user); //Αποθήκευση του admin
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    } //Μέθοδος επιστροφής φόρμας login
}
