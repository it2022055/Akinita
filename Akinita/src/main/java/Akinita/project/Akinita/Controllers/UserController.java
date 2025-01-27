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

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
public class UserController {
    @Autowired
    private UserService userService; //Δήλωση του User Service

    @Autowired
    private OwnerService ownerService; //Δήλωση του Owner Service

    @Autowired
    private RenterService renterService; //Δήλωση του Renter Service

    @PostMapping("/register") //Μέθοδος για registration
    public String register(@RequestParam("role") String role, Model model) {
        User user = new User();
        model.addAttribute("user", user); //Προώθηση του user στη φόρμα
        model.addAttribute("role", role); //Προώθηση του ρόλου στη φόρμα
        System.out.println(role);
        return "auth/register";
    }

    @GetMapping("/roleselection") //Επιστροφή φόρμας επιλογής ρόλου
    public String roleSelection() {
        return "auth/roleselection";
    }


    @PostMapping("/saveUser") //Αποθήκευση καινούργιου χρήστη
    public String saveUser(@ModelAttribute User user, @RequestParam("role") String role,  @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,@RequestParam("telephone") String telephone, Model model) {

        if (userService.existsUser(user.getUsername())){ //Έλεγχος αν το username υπάρχει ήδη
            System.out.println("Username is taken");
            return "redirect:/";
        }
        User saveduser = userService.saveUser(user, role); //Αποθήκευση user στη Βάση Δεδομένων
        if (role.equals("ROLE_OWNER")){ //Αν ο ρόλος που έχει επιλεχθεί είναι Owner
            Owner newOwner=new Owner(saveduser,firstname,lastname,telephone); //Δημιουργία αντικειμένου Owner
            try{
                ownerService.save(newOwner); //Αποθήκευση owner στη βάση δεδομένων
            }catch (Exception e){
                throw new RuntimeException("Saving owner failed");
            }
            return "redirect:/login";
        }else{ //Αν ο ρόλος που έχει επιλεχθεί είναι Renter
            Renter newRenter=new Renter(saveduser,firstname,lastname,telephone); //Δημιουργία αντικειμένου Renter
            try{
                renterService.save(newRenter); //Αποθήκευση renter στη βάση δεδομένων
            }catch (Exception e){
                throw new RuntimeException("Saving renter failed");
            }
            return "redirect:/Renter/applicationSubmitted";
        }
    }

    @GetMapping("/users")
    public String showUsers(Model model){ //Μέθοδος προβολής όλων των users
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", userService.getRoles());
        return "auth/users";
    }

    @GetMapping("/user/{user_id}") //Μέθοδος προβολής user
    public String showUser(@PathVariable int user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "auth/user";
    }

    @PostMapping("/user/{user_id}")
    public String saveStudent(@PathVariable int user_id, @ModelAttribute("user") User user, Model model) {
        User the_user = (User) userService.getUser(user_id); //Ανάκτηση αντικειμένου user από τη Βάση Δεδομένων
        the_user.setEmail(user.getEmail()); //Αλλαγή email
        the_user.setUsername(user.getUsername()); //Αλλαγή username
        try{
            userService.updateUser(the_user); //Αποθήκευση αλλαγής στη Βάση Δεδομένων
        }catch (Exception e){
            throw new RuntimeException("Updating user failed");
        }
        model.addAttribute("users", userService.getUsers()); //Προσθήκη των users στο model
        return "auth/users";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable int user_id, @PathVariable Integer role_id, Model model){ //Μέθοδος διαγραφής ρόλου
        User user = (User) userService.getUser(user_id); //Ανάκτηση user
        Role role = (Role) userService.getRole(role_id); //Ανάκτηση ρόλου
        user.getRoles().remove(role); //Αφαίρεση ρόλου
        try{
            userService.updateUser(user); //Αποθήκευση αλλαγής στη Βάση Δεδομένων
        }catch (Exception e){
            throw new RuntimeException("Updating user failed");
        }
        model.addAttribute("users", userService.getUsers()); //Προσθήκη users στο model
        model.addAttribute("roles", userService.getRoles()); //Προσθήκη ρόλων στο model
        return "auth/users";

    }

    @GetMapping("/user/delete/{user_id}")
    public String deleteUser(@PathVariable int user_id, Model model){ //Μέθοδος διαγραφής ρόλου
        User user = (User) userService.getUser(user_id); //Ανάκτηση user
        System.out.println("User id" + user_id);
        try{
            userService.deleteUser(user,user.getId());
        }catch (Exception e){
            throw new RuntimeException("Updating user failed");
        }
        return "auth/users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable int user_id, @PathVariable Integer role_id, Model model){ //Μέθοδος προσθήκης ρόλου
        User user = (User) userService.getUser(user_id); //Ανάκτηση user
        Role role = (Role) userService.getRole(role_id); //Ανάκτηση ρόλου
        user.getRoles().add(role); //Προσθήκη ρόλου
        try{
            userService.updateUser(user); //Αποθήκευση αλλαγής στη Βάση Δεδομένων
        }catch (Exception e){
            throw new RuntimeException("Updating user failed");
        }
        model.addAttribute("users", userService.getUsers()); //Προσθήκη users στο model
        model.addAttribute("roles", userService.getRoles()); //Προσθήκη ρόλων στο model
        return "auth/users";

    }

    @PostMapping("/user/{user_id}/roleselection")
    public String showRoleSelection(Model model){ //Φόρμα role selection
        return "auth/roleselection";
    }
}
