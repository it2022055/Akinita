package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.*;
import Akinita.project.Akinita.Entities.Actors.User;
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
    public String RenterRentalApplications(Model model, Principal principal) {
        String email = principal.getName();
        Integer renterId = renterService.findRenterIdByEmail(email);
        model.addAttribute("Applications", renterService.getRenterRentalApplications(renterId));
        return "rental_application";
    }


    @PostMapping("/rental_application")
    public String submitRentalApp(@RequestParam("property_id") int propertyId,
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

        System.out.println(renterId);
        System.out.println(propertyId);
        System.out.println(jobSituation);
        System.out.println(taxStatement);
        System.out.println(identityDocument);
        System.out.println(termsAcceptance);
        System.out.println(rentalDuration);
        System.out.println(description);
        System.out.println(pets);

        // Δημιουργία του αντικειμένου αίτησης ενοικίασης
        RentalApplication application = new RentalApplication();
        application.setRenterId(renterId);
        application.setPropertyId(propertyId);
        application.setOwnerId(propertyService.findOwnerIdByPropertyId(propertyId));
        application.setRenterJob(jobSituation);
        application.setRentalDuration(rentalDuration);
        application.setDescription(description);
        application.setRenterPets(pets);

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
        return "redirect:/Renter/RentalApplications";  // Ανακατεύθυνση στη σελίδα με τις αιτήσεις

    }

    @GetMapping("/rental_application_submitted")
    public String RentalApplicationsSub(Model model, Principal principal) {

        return "rental_application";
    }
}
