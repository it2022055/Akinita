package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Repositories.User.RoleRepository;
import Akinita.project.Akinita.entities.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    RoleRepository roleRepository;

    public AuthController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void setup() {
        Role role_user = new Role("ROLE_USER");
        Role role_admin = new Role("ROLE_ADMIN");

        roleRepository.updateOrInsert(role_user);
        roleRepository.updateOrInsert(role_admin);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
