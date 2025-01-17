package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Services.OwnerService;
import Akinita.project.Akinita.Services.RenterService;
import Akinita.project.Akinita.Services.UserService;
import Akinita.project.Akinita.Entities.Owner;
import Akinita.project.Akinita.Entities.Renter;
import Akinita.project.Akinita.Entities.Role;
import Akinita.project.Akinita.Entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    private final OwnerService ownerService;
    private final RenterService renterService;


    // Direct access to Repositories is bad practice amd should be avoided in Controllers, only by Services


    public UserController(UserService userService, OwnerService ownerService, RenterService renterService) {
        this.userService = userService;
        this.ownerService = ownerService;
        this.renterService = renterService;
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
    public String saveUser(@ModelAttribute User user, @RequestParam("role") String role,  @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,@RequestParam("telephone") String telephone, Model model) {
        System.out.println(firstname);
        System.out.println(lastname);
        System.out.println(telephone);
        Integer id = userService.saveUser(user);
        if (role.equals("ROLE_OWNER")){

            Owner newOwner = new Owner();
            newOwner.setFirstName(firstname);
            newOwner.setLastName(lastname);
            newOwner.setTelephoneNumber(telephone);
            ownerService.saveOwner(id,newOwner);

        }else{

            Renter newRenter = new Renter();
            newRenter.setFirstName(firstname);
            newRenter.setLastName(lastname);
            newRenter.setTelephoneNumber(telephone);
            renterService.saveRenter(id, newRenter);

        }

        String message = "User '" + id + "' saved successfully with role: " + role;
        model.addAttribute("msg", message);

        return "redirect:/login";
    }


    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", userService.getRoles());
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
        Role role = (Role) userService.getRole(role_id);
        user.getRoles().remove(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", userService.getRoles());
        return "auth/users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Role role = (Role) userService.getRole(role_id);
        user.getRoles().add(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", userService.getRoles());
        return "auth/users";

    }

    @PostMapping("/user/{user_id}/roleselection")
    public String showRoleSelection(Model model){
        return "auth/roleselection";
    }
}
