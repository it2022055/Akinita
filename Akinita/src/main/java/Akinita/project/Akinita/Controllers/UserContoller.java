package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Repositories.User.OwnerRepository;
import Akinita.project.Akinita.Repositories.User.RenterRepository;
import Akinita.project.Akinita.Repositories.User.RoleRepository;
import Akinita.project.Akinita.Services.UserService;
import Akinita.project.Akinita.entities.Owner;
import Akinita.project.Akinita.entities.Renter;
import Akinita.project.Akinita.entities.Role;
import Akinita.project.Akinita.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserContoller {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RenterRepository renterRepository;


    public UserContoller(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public String register(@RequestParam("role") String role, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("role", role); // Προώθηση του ρόλου στη φόρμα
        System.out.println(role);
        return "auth/register";
    }

    @GetMapping("/roleselection")
    public String roleSelection() {
        return "auth/roleselection";
    }


    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, @RequestParam("role") String role,  @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,Model model) {
        System.out.println(firstname);
        System.out.println(lastname);
        Integer id = userService.saveUser(user);
        if (role.equals("ROLE_OWNER")){
            Owner newOwner=new Owner();
            newOwner.setFirstName(firstname);
            newOwner.setLastName(lastname);
            newOwner.setEmail(user.getEmail());
            newOwner.setTelephoneNumber(user.getTelephone());
            ownerRepository.save(newOwner);
        }else{
            Renter newRenter=new Renter();
            renterRepository.save(newRenter);
        }
        String message = "User '" + id + "' saved successfully with role: " + role;
        model.addAttribute("msg", message);

        return "redirect:/login";
    }


    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Long user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "auth/user";
    }

    @PostMapping("/user/{user_id}")
    public String saveStudent(@PathVariable Long user_id, @ModelAttribute("user") User user, Model model) {
        User the_user = (User) userService.getUser(user_id);
        the_user.setEmail(user.getEmail());
        the_user.setUsername(user.getUsername());
        userService.updateUser(the_user);
        model.addAttribute("users", userService.getUsers());
        return "auth/users";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().remove(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().add(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";

    }

    @PostMapping("/user/{user_id}/roleselection")
    public String showRoleSelection(Model model){
        return "auth/roleselection";
    }
}
