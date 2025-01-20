package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.*;
import Akinita.project.Akinita.Entities.Actors.User;
import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Services.FileStorageService;
import Akinita.project.Akinita.Services.RenterService;
import Akinita.project.Akinita.Services.PropertyService;
import Akinita.project.Akinita.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("Renter")
public class RenterController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private RenterService renterService;
    @Autowired
    private UserService userService;;
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/rental_application")
    public String RenterRentalApplications(Model model, @RequestParam("property_id") Property property) {
        model.addAttribute("property_id", property.getId());
        return "/renter/rental_application";
    }


    @PostMapping("/rental_application")
    public String submitRentalApp(@RequestParam("property_id") Integer property_id,
                                  @RequestParam("jobSituation") String jobSituation,
                                  @RequestParam("taxStatement") MultipartFile taxStatement,
                                  @RequestParam("identityDocument") MultipartFile identityDocument,
                                  @RequestParam("termsAcceptance") MultipartFile termsAcceptance,
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
        System.out.println("TaxStatement: " + taxStatement);
        System.out.println("IdentityDocument: " + identityDocument);
        System.out.println("TermsAcceptance: " + termsAcceptance);
        System.out.println("RentalDuration: " + rentalDuration);
        System.out.println("Description: " + description);
        System.out.println("Pets: " + pets);


        // Δημιουργία του αντικειμένου αίτησης ενοικίασης
        RentalApplication application = new RentalApplication();
        application.setRenter(renter);
        application.setProperty(property);
        application.setOwner(owner);

        application.setRenterJob(jobSituation);
        application.setRentalDuration(rentalDuration);
        application.setDescription(description);
        application.setRenterPets(pets);


        renterService.saveApplication(application);            // part 1

        // Αποθήκευση των αρχείων
        try {
            if (taxStatement != null && !taxStatement.isEmpty()) {
                fileStorageService.saveFile(taxStatement, application);
            }
            if (identityDocument != null && !identityDocument.isEmpty()) {
                fileStorageService.saveFile(identityDocument, application);
            }
            if (termsAcceptance != null && !termsAcceptance.isEmpty()) {
                fileStorageService.saveFile(termsAcceptance, application);
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Παρουσιάστηκε σφάλμα κατά την αποθήκευση των αρχείων.");
            return "redirect:/Renter/RentalApplications";  // Ανακατεύθυνση αν υπάρχει σφάλμα
        }

        // Αποθήκευση της αίτησης (μπορείς να την αποθηκεύσεις στην βάση μέσω του Service)
        renterService.saveApplication(application);

        // Προαιρετικά, προσθέτουμε ένα μήνυμα επιτυχίας για την αναγνώριση της επιτυχίας της αποθήκευσης
        model.addAttribute("message", "Η αίτησή σας καταχωρήθηκε επιτυχώς.");

        // Ανακατεύθυνση ή επιστροφή στη σελίδα
        return "redirect:/Renter/applicationSubmitted";  // Ανακατεύθυνση στη σελίδα με τις αιτήσεις

    }

    @GetMapping("/applicationSubmitted")
    public String RentalApplicationsSub(Model model, Principal principal) {
        return "/renter/applicationSubmitted";
    }
}
