package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Enums.EnergyClass;
import Akinita.project.Akinita.Entities.Enums.Facilities;
import Akinita.project.Akinita.Entities.Properties.*;
import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Services.ApplicationService;
import Akinita.project.Akinita.Services.OwnerService;
import Akinita.project.Akinita.Services.PropertyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"DuplicatedCode", "SpringJavaAutowiredFieldsWarningInspection"})
@Controller
@SessionAttributes({"estateName", "description", "price", "location", "sqMeters", "propertyType", "owner"}) //Μεταβλητές γενικές για τη χρήση σε παραπάνω από μια μεθόδους
@RequestMapping("Owner")
public class OwnerController {
    @Autowired
    private PropertyService propertyService; //Δήλωση του property Service
    @Autowired
    private OwnerService ownerService; //Δήλωση του owner Service
    @Autowired
    private ApplicationService applicationService; //Δήλωση του application Service

    @GetMapping("/submitProperty") //Μέθοδος επιστροφής φόρμας για εισαγωγή γενικών πληροφοριών ιδιοκτησίας
    public String submitProperty() {
        return "properties/submitProperty";
    }

    @PostMapping("/submitNewProperty") //Μέθοδος για προσθήκη ιδιοκτησίας
    public String submitNewProperty( Principal principal,@RequestParam("SqMeters") int sqMeters,
                                     @RequestParam("estateName") String estateName,
                                     @RequestParam("description") String description,
                                     @RequestParam("price") int price, @RequestParam("location") String location,
                                     @RequestParam("propertyType") String propertyType,
                                     @ModelAttribute RealEstate realEstate,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {
        if (principal == null) { //Στην περίπτωση που δεν είναι logged in ο Owner τον επιστέφει στη σελίδα του log in
            return "redirect:/login";
        }
        String email = principal.getName(); //Εξαγωγή email
        Integer ownerId = ownerService.findOwnerIdByEmail(email); //Επιστροφή Id του owner μέσω της findOwnerByEmail
        Owner owner=ownerService.findById(ownerId); //Δημιουργία αντικειμένου Owner μέσω της findById(Επιστροφή του
        model.addAttribute("estateName", estateName); //Προσθήκη ονόματος ιδιοκτησίας στο model
        model.addAttribute("description", description); //Προσθήκη περιγραφής στο model
        model.addAttribute("price", price); //Προσθήκη τιμής στο model
        model.addAttribute("location", location); //Προσθήκη περιοχής στο model
        model.addAttribute("sqMeters", sqMeters); //Προσθήκη τετραγωνικών μέτρων στο model
        model.addAttribute("owner", owner); //Προσθήκη ιδιοκτήτη στο model
        model.addAttribute("propertyType", propertyType); //Προσθήκη τύπου ιδιοκτησίας στο model
        if(!propertyType.equals("Land")) { //Στην περίπτωση που ο τύπος ιδιοκτησίας ΔΕΝ είναι land
            redirectAttributes.addFlashAttribute("estateName", estateName); //Προσθήκη ονόματος ιδιοκτησίας στο redAttributes για redirection
            redirectAttributes.addFlashAttribute("description", description); //Προσθήκη περιγραφής στο redAttributes για redirection
            redirectAttributes.addFlashAttribute("price", price); //Προσθήκη τιμής στο redAttributes για redirection
            redirectAttributes.addFlashAttribute("location", location); //Προσθήκη περιοχής στο redAttributes για redirection
            redirectAttributes.addFlashAttribute("SqMeters", sqMeters); //Προσθήκη τετραγωνικών μέτρων στο redAttributes για redirection
            redirectAttributes.addFlashAttribute("owner", owner); //Προσθήκη ιδιοκτήτη στο redAttributes για redirection
            redirectAttributes.addFlashAttribute("propertyType", propertyType); //Προσθήκη τύπου ιδιοκτησίας στο redAttributes για redirection
            return "redirect:/Owner/propertyFacilities"; //Redirection στη φόρμα για την προσθήκη παροχών
        }else{ //Στην περίπτωση που είναι land
            Land land = new Land(); //Δημιουργίας land αντικειμένου
            land.setLocation(location); //Προσθήκη attributes
            land.setEstateName(estateName); //Προσθήκη attributes
            land.setDescription(description); //Προσθήκη attributes
            land.setPrice(price); //Προσθήκη attributes
            land.setVisibility("Invisible"); //Προσθήκη attributes
            land.setAvailability(true); //Προσθήκη attributes
            land.setSquareMeter(sqMeters); //Προσθήκη attributes
            land.setRenter(null); //Προσθήκη attributes
            land.setOwner(owner); //Προσθήκη attributes
            try{
                propertyService.SaveLandProperty(land); //Αποθήκευση του καινούριου property στη Βάση Δεδομένων
            }catch(Exception e){
                throw new RuntimeException("Error saving land");
            }
            return "redirect:/Owner/propertySubmitted";
        }
    }
    @GetMapping("/propertyFacilities") //Φόρμα επιστροφής παροχών ιδιοκτησίας
    public String propertyFacilities() {
        return "properties/propertyFacilities";
    }

    @PostMapping("/propertyFacilities") //Φόρμα προσθήκης παροχών ιδιοκτησίας
    public String propertyFacilities(Model model,
                                     @RequestParam("constructionDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date constructiondate,
                                     @RequestParam(value = "AC", required = false) Boolean ac,
                                     @RequestParam(value = "ELEVATOR", required = false) Boolean elevator,
                                     @RequestParam(value = "PARKING", required = false) Boolean parkingparam,
                                     @RequestParam(value = "GARDEN", required = false) Boolean garden,
                                     @RequestParam(value = "FIREPLACE", required = false) Boolean fireplace,
                                     @RequestParam(value = "POOL", required = false) Boolean pool,
                                     @RequestParam(value = "STORAGE", required = false) Boolean storage,
                                     @RequestParam(value = "ALARM", required = false) Boolean alarm,
                                     @RequestParam(value = "SHAREDEXPENSES", required = false) Boolean sharedExpenses,
                                     @RequestParam(value = "ENERGYCLASS") String energyclass) {
        //Λήψη δεδομένων από το μοντέλο
        String estateName = (String) model.getAttribute("estateName");
        String description = (String) model.getAttribute("description");
        Object priceObj = model.getAttribute("price");
        int price = (priceObj != null) ? (int) priceObj : 0;
        String location = (String) model.getAttribute("location");
        Object sqMetersObj = model.getAttribute("sqMeters");
        int sqMeters = (sqMetersObj != null) ? (int) sqMetersObj : 0;
        String propertyType = (String) model.getAttribute("propertyType");
        Owner owner = (Owner) model.getAttribute("owner");

        EnergyClass energyClassEnum = EnergyClass.valueOf(energyclass); //Δήλωση enum EnergyClass με βάση το input από τη φόρμα

        //Προσθήκη παροχών στη selectedFacilities
        Set<Facilities> selectedFacilities = new HashSet<>();
        if (ac != null && ac) selectedFacilities.add(Facilities.AC);
        if (elevator != null && elevator) selectedFacilities.add(Facilities.ELEVATOR);
        if (parkingparam != null && parkingparam) selectedFacilities.add(Facilities.PARKING);
        if (garden != null && garden) selectedFacilities.add(Facilities.GARDEN);
        if (fireplace != null && fireplace) selectedFacilities.add(Facilities.FIREPLACE);
        if (pool != null && pool) selectedFacilities.add(Facilities.POOL);
        if (storage != null && storage) selectedFacilities.add(Facilities.STORAGE);
        if (alarm != null && alarm) selectedFacilities.add(Facilities.ALARM);

        if (propertyType.equals("House")) { //Αν ο τύπος ιδιοκτησίας είναι Σπίτι
            House house = new House(); //Δημιουργία αντικειμένου House
            house.setLocation(location); //Προσθήκη attributes
            house.setEstateName(estateName); //Προσθήκη attributes
            house.setDescription(description); //Προσθήκη attributes
            house.setPrice(price); //Προσθήκη attributes
            house.setVisibility("Invisible"); //Προσθήκη attributes
            house.setAvailability(true); //Προσθήκη attributes
            house.setSquareMeter(sqMeters); //Προσθήκη attributes
            house.setOwner(owner); //Προσθήκη attributes
            house.setConstructionDate(constructiondate); //Προσθήκη attributes
            house.setBuildingFees(sharedExpenses != null && sharedExpenses); //Προσθήκη attributes
            house.setEnergyClass(energyClassEnum); //Προσθήκη attributes
            house.setFacilities(selectedFacilities); //Προσθήκη attributes

            try {
                propertyService.SaveHouseProperty(house); //Αποθήκευση του καινούριου property στη Βάση Δεδομένων
            } catch (Exception e) {
                throw new RuntimeException("Error saving house");
            }
        } else if (propertyType.equals("Parking")) {
            Parking parking = new Parking(); //Δημιουργία αντικειμένου Parking
            parking.setLocation(location); //Προσθήκη attributes
            parking.setEstateName(estateName); //Προσθήκη attributes
            parking.setDescription(description); //Προσθήκη attributes
            parking.setPrice(price); //Προσθήκη attributes
            parking.setVisibility("Invisible"); //Προσθήκη attributes
            parking.setAvailability(true); //Προσθήκη attributes
            parking.setSquareMeter(sqMeters); //Προσθήκη attributes
            parking.setOwner(owner); //Προσθήκη attributes
            parking.setConstructionDate(constructiondate); //Προσθήκη attributes
            parking.setBuildingFees(sharedExpenses != null && sharedExpenses); //Προσθήκη attributes
            parking.setEnergyClass(energyClassEnum); //Προσθήκη attributes
            parking.setFacilities(selectedFacilities); //Προσθήκη attributes

            try {
                propertyService.SaveParkingProperty(parking); //Αποθήκευση του καινούριου property στη Βάση Δεδομένων
            } catch (Exception e) {
                throw new RuntimeException("Error saving parking");
            }
        } else {
            CommercialProperty commercialProperty = new CommercialProperty();  //Δημιουργία αντικειμένου CommercialProperty
            commercialProperty.setLocation(location); //Προσθήκη attributes
            commercialProperty.setEstateName(estateName); //Προσθήκη attributes
            commercialProperty.setDescription(description); //Προσθήκη attributes
            commercialProperty.setPrice(price); //Προσθήκη attributes
            commercialProperty.setVisibility("Invisible"); //Προσθήκη attributes
            commercialProperty.setAvailability(true); //Προσθήκη attributes
            commercialProperty.setSquareMeter(sqMeters); //Προσθήκη attributes
            commercialProperty.setOwner(owner); //Προσθήκη attributes
            commercialProperty.setConstructionDate(constructiondate); //Προσθήκη attributes
            commercialProperty.setBuildingFees(sharedExpenses != null && sharedExpenses); //Προσθήκη attributes
            commercialProperty.setEnergyClass(energyClassEnum); //Προσθήκη attributes
            commercialProperty.setFacilities(selectedFacilities); //Προσθήκη attributes

            try {
                propertyService.SaveCommercialProperty(commercialProperty); //Αποθήκευση του καινούριου property στη Βάση Δεδομένων
            } catch (Exception e) {
                throw new RuntimeException("Error saving commercial property");
            }
        }

        return "redirect:/Owner/propertySubmitted";
    }




    @GetMapping("/propertySubmitted") //Επιστροφή φόρμας επιτυχίας προσθήκης ιδιοκτησίας στη βάση δεδομένων
    public String propertySubmitted() {
        return "properties/propertySubmitted";
    }


    @GetMapping("/Listings") //Μέθοδος επιστροφής των ιδιοκτησιών του Owner
    public String ownerListings(Model model, Principal principal) {
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        model.addAttribute("Listings", ownerService.getOwnerProperties(ownerId)); //Προσθήκη στο model
        return "properties/ownerListings";
    }

    @Transactional
    @GetMapping("/Listings/ChangeAvailability/{property_id}") //Μέθοδος αλλαγής διαθεσιμότητας ιδιοκτησίας
    public String ChangeAvailability(@PathVariable("property_id") Integer property_id){
       Property the_property=  propertyService.getPropertyById(property_id);
        the_property.setAvailability(!the_property.isAvailableForSale()); //Αλλαγή διαθεσιμότητας
        try{
            propertyService.updateProperty(the_property); //Αποθήκευση αλλαγής στη βάση δεδομένων
        }catch (Exception e){
            throw new RuntimeException("Error updating property");
        }
       return "redirect:/Owner/Listings";
    }

    @Transactional
    @GetMapping("/Listings/Delete/{property_id}")  //Μέθοδος διαγραφής ιδιοκτησίας
    public String DeleteProperty(@PathVariable("property_id") Integer property_id){
        Property the_property=  propertyService.getPropertyById(property_id);
        try{
            propertyService.DeleteProperty(the_property); //Διαγραφή από βάση δεδομένων
        }catch (Exception e){
            throw new RuntimeException("Error deleting property");
        }

        return "redirect:/Owner/Listings";
    }

    @GetMapping("/manageApplications") //Μέθοδος διαχείρισης φορμών ενοικίασης
    public String manageApplications(Model model, Principal principal) {
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        model.addAttribute("Applications", ownerService.getOwnerRentalApplications(ownerId)); //Επιστροφή των applications
        return "properties/manageApplications";
    }

    @Transactional
    @GetMapping("/manageRentalApplications/setstatus/{rentalApplication_id}") //Μέθοδος αποδοχής/απόρριψης application
    public String SetStatus(@PathVariable("rentalApplication_id") Integer rentalApplication_id,
                            @RequestParam("status") String status) {
        RentalApplication the_rentalApplication = applicationService.getApplication(rentalApplication_id);
        try {
            if ("accept".equalsIgnoreCase(status)) {
                applicationService.SetRentalApplicationStatus(true); //Αποδοχή

            } else if ("decline".equalsIgnoreCase(status)) {
                applicationService.SetRentalApplicationStatus(false); //Απόρριψη
            } else {
                throw new IllegalArgumentException("Invalid status value: " + status);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
        try{
            applicationService.save(the_rentalApplication); //Αποθήκευση αλλαγής στη Βάση Δεδομένων
        }catch (Exception e){
            throw new RuntimeException("Error updating rental application");
        }
        return "redirect:/Owner/manageApplications";
    }


}
