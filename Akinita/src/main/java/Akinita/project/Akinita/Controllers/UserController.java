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
        model.addAttribute("isError",false);
        System.out.println(role);
        return "auth/register";
    }

    @GetMapping("/roleselection") //Επιστροφή φόρμας επιλογής ρόλου
    public String roleSelection() {
        return "auth/roleselection";
    }

    @PostMapping("/saveUser") //Αποθήκευση καινούργιου χρήστη
    public String registerUser(@ModelAttribute User user, @RequestParam("role") String role, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("telephone") String telephone, Model model) {

        model.addAttribute("isError",false);

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        if (userService.existsUser(user.getUsername())){ //Έλεγχος αν το username υπάρχει ήδη
            model.addAttribute("isError",true);
            model.addAttribute("error", "Username already exists!");
            return "auth/register";
        }

        if(!user.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
            model.addAttribute("isError",true);
            model.addAttribute("error", "Email must be of the format name@service.domain");
            return "auth/register";
        }

        if(userService.existsEmail(user.getEmail())){
            model.addAttribute("isError",true);
            model.addAttribute("error", "Email already exists!");
            return "auth/register";
        }

        if(telephone.length() != 10){
            model.addAttribute("isError",true);
            model.addAttribute("error", "Telephone must be 10 digits");
            return "auth/register";
        }

        if (role.equals("ROLE_OWNER")){ //Αν ο ρόλος που έχει επιλεχθεί είναι Owner

            if(ownerService.existsTelephone(telephone)){
                model.addAttribute("isError",true);
                model.addAttribute("error", "Telephone already exists!");
                return "auth/register";
            }

            User saveduser = userService.saveUser(user, role); //Αποθήκευση user στη Βάση Δεδομένων
            Owner newOwner=new Owner(saveduser,firstname,lastname,telephone); //Δημιουργία αντικειμένου Owner
            try{
                ownerService.save(newOwner); //Αποθήκευση owner στη βάση δεδομένων
            }catch (Exception e){
                throw new RuntimeException("Saving owner failed");
            }

            return "redirect:/login";
        }else{ //Αν ο ρόλος που έχει επιλεχθεί είναι Renter

            if(renterService.existsTelephone(telephone)){
                model.addAttribute("isError",true);
                model.addAttribute("error", "Telephone already exists!");
                return "auth/register";
            }

            User saveduser = userService.saveUser(user, role); //Αποθήκευση user στη Βάση Δεδομένων
            Renter newRenter=new Renter(saveduser,firstname,lastname,telephone); //Δημιουργία αντικειμένου Renter
            try{
                renterService.save(newRenter); //Αποθήκευση renter στη βάση δεδομένων
            }catch (Exception e){
                throw new RuntimeException("Saving renter failed");
            }
            return "redirect:/Renter/registrationSubmitted";
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
    public String editUser(@PathVariable int user_id, @ModelAttribute("user") User user, Model model) {
        User the_user = userService.getUser(user_id); //Ανάκτηση αντικειμένου user από τη Βάση Δεδομένων
        if (!the_user.getUsername().equals(user.getUsername())) {
            if (userService.existsUser(user.getUsername())) {
                model.addAttribute("isError", true);
                model.addAttribute("error", "Username already exists!");
                return "auth/user";
            } else {
                the_user.setUsername(user.getUsername()); // Change username
            }
        }

        if (!the_user.getEmail().equals(user.getEmail())) {
            if (userService.existsEmail(user.getEmail())) {
                model.addAttribute("isError", true);
                model.addAttribute("error", "Email already exists!");
                return "auth/user";
            } else {
                the_user.setEmail(user.getEmail()); // Change email
            }
        }

        try {
            userService.updateUser(the_user); // Save changes to the database
        } catch (Exception e) {
            throw new RuntimeException("Updating user failed");
        }
        model.addAttribute("users", userService.getUsers()); //Προσθήκη των users στο model
        return "auth/users";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable int user_id, @PathVariable Integer role_id, Model model){ //Μέθοδος διαγραφής ρόλου
        User user = userService.getUser(user_id); //Ανάκτηση user
        Role role = (Role) userService.getRole(role_id); //Ανάκτηση ρόλου
        try{
            if (user.getRoles().contains(role)) {      // Αφαίρεση user από το αντίστοιχο table του role
                if (role.getName().equals("ROLE_OWNER")) {

                    ownerService.deleteOwner(user_id);

                } else if (role.getName().equals("ROLE_RENTER")) {

                    renterService.deleteRenter(user_id);

                }

                user.getRoles().remove(role); //Αφαίρεση ρόλου
                userService.updateUser(user); //Αποθήκευση αλλαγής στη Βάση Δεδομένων
            }

        }catch (Exception e){
            throw new RuntimeException("Updating user failed");
        }
        model.addAttribute("users", userService.getUsers()); //Προσθήκη users στο model
        model.addAttribute("roles", userService.getRoles()); //Προσθήκη ρόλων στο model
        return "auth/users";

    }

    @GetMapping("/user/delete/{user_id}")
    public String deleteUser(@PathVariable int user_id){ //Μέθοδος διαγραφής ρόλου
        User user = userService.getUser(user_id); //Ανάκτηση user
        try{
            userService.deleteUser(user,user.getId());
        }catch (Exception e){
            throw new RuntimeException("Updating user failed");
        }
        return "auth/users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable int user_id, @PathVariable Integer role_id, Model model){ //Μέθοδος προσθήκης ρόλου
        User user = userService.getUser(user_id); //Ανάκτηση user
        Role role = (Role) userService.getRole(role_id); //Ανάκτηση ρόλου
        try{
            if (!user.getRoles().contains(role)) {      // Προσθήκη user στο αντίστοιχο table του role
                if(role.getName().equals("ROLE_OWNER")){

                    Renter renter = renterService.getRenterById(user_id);
                    Owner owner_renter = new Owner(user, renter.getFirstName(), renter.getLastName(), user.getEmail());
                    ownerService.save(owner_renter);

                } else if(role.getName().equals("ROLE_RENTER")){

                    Owner owner = ownerService.getOwnerById(user_id);
                    Renter renter_owner = new Renter(user, owner.getFirstName(), owner.getLastName(), user.getEmail());
                    renter_owner.setAcceptance("Accepted");
                    renterService.save(renter_owner);

                }

                user.getRoles().add(role); //Προσθήκη ρόλου
                userService.updateUser(user); //Αποθήκευση αλλαγής στη Βάση Δεδομένων

            }
        }catch (Exception e){
            throw new RuntimeException("Updating user failed");
        }
        model.addAttribute("users", userService.getUsers()); //Προσθήκη users στο model
        model.addAttribute("roles", userService.getRoles()); //Προσθήκη ρόλων στο model
        return "auth/users";

    }

    @PostMapping("/user/{user_id}/roleselection")
    public String showRoleSelection(){ //Φόρμα role selection
        return "auth/roleselection";
    }
}
