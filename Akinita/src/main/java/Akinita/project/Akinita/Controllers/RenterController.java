package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.*;
import Akinita.project.Akinita.Entities.Actors.Renter;
import Akinita.project.Akinita.Entities.Actors.User;
import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping("Renter")
public class RenterController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private RenterService renterService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/rental_application")
    public String RenterRentalApplications(Model model, @RequestParam("property_id") Property property) {
        model.addAttribute("property_id", property.getId());
        return "/renter/rental_application";
    }

    @PostMapping("/rental_application")
    public String submitRentalApp(@RequestParam("property_id") Integer property_id,
                                  @RequestParam("jobSituation") String jobSituation,
                                  @RequestParam("rentalDuration") int rentalDuration,
                                  @RequestParam("description") String description,
                                  @RequestParam(value = "pets", defaultValue = "false") boolean pets,
                                  Model model, Principal principal) {

        String email = principal.getName();
        Integer renterId = renterService.findRenterIdByEmail(email);
        User renter = userService.getUser(renterId);


        Property property = propertyService.getPropertyById(property_id);
        User owner = userService.getUser(property.getOwnerId());

        System.out.println("RenterId: " + renterId);
        System.out.println("PropertyId: " + property_id);
        System.out.println("JobSituation: " + jobSituation);
        System.out.println("RentalDuration: " + rentalDuration);
        System.out.println("Description: " + description);
        System.out.println("Pets: " + pets);


        // Δημιουργία του αντικειμένου αίτησης ενοικίασης
        RentalApplication application = new RentalApplication();


        if(!applicationService.findByRenter(renter.getId()).contains(applicationService.findByProperty(property_id))  ) {
            model.addAttribute("message", "You have already applied for for rent to this property!");
            return "redirect:/";
        }


        application.setRenter(renter);
        application.setProperty(property);
        application.setOwner(owner);

        application.setRenterJob(jobSituation);
        application.setRentalDuration(rentalDuration);
        application.setDescription(description);
        application.setRenterPets(pets);

        // Αποθήκευση της αίτησης (μπορείς να την αποθηκεύσεις στη βάση μέσω του Service)
        renterService.saveApplication(application);

        // Προαιρετικά, προσθέτουμε ένα μήνυμα επιτυχίας για την αναγνώριση της επιτυχίας της αποθήκευσης
        model.addAttribute("message", "Η αίτηση σας καταχωρήθηκε επιτυχώς.");

        // Ανακατεύθυνση ή επιστροφή στη σελίδα
        return "redirect:/Renter/applicationSubmitted";  // Ανακατεύθυνση στη σελίδα με τις αιτήσεις
    }

    @GetMapping("/registrationSubmitted")
    public String RegistrationApplicationsSub() {
        return "/renter/registrationsubmitted";
    }

    @GetMapping("/applicationSubmitted")
    public String RentalApplicationsSub() {
        return "/renter/applicationSubmitted";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/AcceptRenters") //Μέθοδος προβολής Renters προς αποδοχή από τον admin
    public String AcceptRenters(Model model) {
        model.addAttribute("Renters",renterService.findAllUnacceptedRenters()); //Προσθήκη Renters στο model
        return "renter/acceptRenters";
    }

    @Secured("ROLE_ADMIN")
    @Transactional
    @GetMapping("/AcceptRenters/{renter_id}") //Μέθοδος αποδοχής Renter
    public String acceptRenters(Model model, @PathVariable Integer renter_id) {
        Renter the_renter = renterService.getRenterById(renter_id);
        the_renter.setAcceptance("Accepted"); //Αλλαγή απο "Unaccepted" σε "Accepted"
        try{
            renterService.UpdateRenter(the_renter); //Αποθήκευση αλλαγής στη Βάση Δεδομένων
        }catch (Exception e){
            throw new RuntimeException("Could not update renter");
        }
        return "redirect:/Renter/AcceptRenters";
    }
}
