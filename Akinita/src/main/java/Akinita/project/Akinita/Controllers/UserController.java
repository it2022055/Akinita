package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Services.OwnerService;
import Akinita.project.Akinita.Services.RenterService;
import Akinita.project.Akinita.Services.UserService;
import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Actors.Renter;
import Akinita.project.Akinita.Entities.Role;
import Akinita.project.Akinita.Entities.Actors.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private RenterService renterService;


    public UserController(UserService userService,OwnerService ownerService,RenterService renterService) {
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

        if (userService.existsUser(user.getUsername())){
            System.out.println("Username is taken");
            return "redirect:/";
        }

        User saveduser = userService.saveUser(user, role);
        if (role.equals("ROLE_OWNER")){
            Owner newOwner=new Owner(saveduser,firstname,lastname,telephone);
            ownerService.save(newOwner);
        }else{
            Renter newRenter=new Renter(saveduser,firstname,lastname,telephone);
            renterService.save(newRenter);
        }
        String message = "User '" + saveduser.getId() + "' saved successfully with role: " + role;
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
    public String showUser(@PathVariable int user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "auth/user";
    }

    @PostMapping("/user/{user_id}")
    public String saveStudent(@PathVariable int user_id, @ModelAttribute("user") User user, Model model) {
        User the_user = (User) userService.getUser(user_id);
        the_user.setEmail(user.getEmail());
        the_user.setUsername(user.getUsername());
        userService.updateUser(the_user);
        model.addAttribute("users", userService.getUsers());
        return "auth/users";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable int user_id, @PathVariable Integer role_id, Model model){
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
    public String addRoletoUser(@PathVariable int user_id, @PathVariable Integer role_id, Model model){
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
