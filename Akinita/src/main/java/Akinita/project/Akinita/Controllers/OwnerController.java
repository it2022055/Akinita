package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Properties.CommercialProperty;
import Akinita.project.Akinita.Entities.Properties.House;
import Akinita.project.Akinita.Entities.Properties.Land;
import Akinita.project.Akinita.Entities.Properties.Parking;
import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Services.OwnerService;
import Akinita.project.Akinita.Services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;

@Controller
@SessionAttributes({"estateName", "description", "price", "location", "sqMeters", "propertyType", "owner"})
@RequestMapping("Owner")
public class OwnerController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/submitProperty")
    public String submitProperty() {
        return "properties/submitProperty";
    }

    @PostMapping("/submitNewProperty")
    public String submitNewProperty( Principal principal,@RequestParam("SqMeters") int sqMeters,
                                     @RequestParam("estateName") String estateName,
                                     @RequestParam("description") String description,
                                     @RequestParam("price") int price, @RequestParam("location") String location,
                                     @RequestParam("propertyType") String propertyType,
                                     @ModelAttribute RealEstate realEstate,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        Owner owner=ownerService.findById(ownerId);
        model.addAttribute("estateName", estateName);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("location", location);
        model.addAttribute("sqMeters", sqMeters);
        model.addAttribute("owner", owner);
        model.addAttribute("propertyType", propertyType);
        if(!propertyType.equals("Land")) {
            redirectAttributes.addFlashAttribute("estateName", estateName);
            redirectAttributes.addFlashAttribute("description", description);
            redirectAttributes.addFlashAttribute("price", price);
            redirectAttributes.addFlashAttribute("location", location);
            redirectAttributes.addFlashAttribute("SqMeters", sqMeters);
            redirectAttributes.addFlashAttribute("owner", owner);
            redirectAttributes.addFlashAttribute("propertyType", propertyType);
            return "redirect:/Owner/propertyFacilities";
        }else{
            Land land = new Land();
            land.setLocation(location);
            land.setEstateName(estateName);
            land.setDescription(description);
            land.setPrice(price);
            land.setVisibility("Invisible");
            land.setAvailability(true);
            land.setSquareMeter(sqMeters);
            land.setRenter(null);
            land.setOwner(owner);
            try{
                propertyService.SaveLandProperty(land);
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("Redirecting to /properties/propertySubmitted");
            return "redirect:/Owner/propertySubmitted";
        }
    }
    @GetMapping("/propertyFacilities")
    public String propertyFacilities(Model model) {
        return "properties/propertyFacilities";
    }

    @PostMapping("/propertyFacilities")
    public String propertyFacilities(Model model, @RequestParam("constructionDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date constructiondate,
                                     @RequestParam(value = "AC", required = false) Boolean ac,
                                     @RequestParam(value = "ELEVATOR", required = false) Boolean elevator,
                                     @RequestParam(value = "PARKING", required = false) Boolean parkingparam,
                                     @RequestParam(value = "GARDEN", required = false) Boolean garden,
                                     @RequestParam(value = "FIREPLACE", required = false) Boolean fireplace,
                                     @RequestParam(value = "POOL", required = false) Boolean pool,
                                     @RequestParam(value = "STORAGE", required = false) Boolean storage,
                                     @RequestParam(value = "ALARM", required = false) Boolean alarm,
                                     @RequestParam(value = "SHAREDEXPENSES", required = false) Boolean sharedExpenses) {
        String estateName = (String) model.getAttribute("estateName");
        String description = (String) model.getAttribute("description");
        Integer price = (Integer) model.getAttribute("price");
        String location = (String) model.getAttribute("location");
        Integer sqMeters = (Integer) model.getAttribute("sqMeters");
        String propertyType = (String) model.getAttribute("propertyType");
        Owner owner = (Owner) model.getAttribute("owner");
        ac = (ac != null) ? ac : false;
        elevator = (elevator != null) ? elevator : false;
        parkingparam = (parkingparam != null) ? parkingparam : false;
        garden = (garden != null) ? garden : false;
        fireplace = (fireplace != null) ? fireplace : false;
        pool = (pool != null) ? pool : false;
        storage = (storage != null) ? storage : false;
        alarm = (alarm != null) ? alarm : false;
        sharedExpenses = (sharedExpenses != null) ? sharedExpenses : false;
        if(propertyType.equals("House")){
            House house = new House();
            house.setLocation(location);
            house.setEstateName(estateName);
            house.setDescription(description);
            house.setPrice(price);
            house.setVisibility("Invisible");
            house.setAvailability(true);
            house.setSquareMeter(sqMeters);
            house.setOwner(owner);
            house.setConstructionDate(constructiondate);
            house.setRenter(null);
            house.setBuildingFees(sharedExpenses);
            try{
                propertyService.SaveHouseProperty(house);
            }catch(Exception e){
                e.printStackTrace();}
        } else if (propertyType.equals("Parking")) {
            Parking parking = new Parking();
            parking.setLocation(location);
            parking.setEstateName(estateName);
            parking.setDescription(description);
            parking.setPrice(price);
            parking.setVisibility("Invisible");
            parking.setAvailability(true);
            parking.setSquareMeter(sqMeters);
            parking.setOwner(owner);
            parking.setConstructionDate(constructiondate);
            parking.setRenter(null);
            parking.setBuildingFees(sharedExpenses);
            try{
                propertyService.SaveParkingProperty(parking);
            }catch(Exception e){
                e.printStackTrace();
            }
        } else{
            CommercialProperty commercialProperty = new CommercialProperty();
            commercialProperty.setLocation(location);
            commercialProperty.setEstateName(estateName);
            commercialProperty.setDescription(description);
            commercialProperty.setPrice(price);
            commercialProperty.setVisibility("Invisible");
            commercialProperty.setAvailability(true);
            commercialProperty.setSquareMeter(sqMeters);
            commercialProperty.setOwner(owner);
            commercialProperty.setConstructionDate(constructiondate);
            commercialProperty.setRenter(null);
            commercialProperty.setBuildingFees(sharedExpenses);
            try{
                propertyService.SaveCommercialProperty(commercialProperty);
            }catch(Exception e){
                e.printStackTrace();

            }
        }
        return "redirect:/Owner/propertySubmitted";
    }

    @GetMapping("/propertySubmitted")
    public String propertySubmitted() {
        return "properties/propertySubmitted";
    }


    @GetMapping("/Listings")
    public String ownerListings(Model model, Principal principal) {
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        model.addAttribute("Listings", ownerService.getOwnerProperties(ownerId));
        return "properties/ownerListings";
    }

    @PostMapping("/Listings/action")
    public String handlePropertyAction(
            @RequestParam("propertyId") Long propertyId,
            @RequestParam("action") String action,
            Principal principal) {
        System.out.println("Selected action:"+action);
        System.out.println("Selected property:"+propertyId);

        if ("changeAvailability".equals(action)) {
            //propertyService.changeAvailability(propertyId);
        } else if ("deleteProperty".equals(action)) {
            //propertyService.deleteById(propertyId);
        }
        // Επιστροφή στα Listings μετά την ενέργεια
        return "redirect:/Listings";
    }


    @GetMapping("/manageApplications")
    public String manageApplications(Model model, Principal principal) {
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        model.addAttribute("Applications", ownerService.getOwnerRentalApplications(ownerId));
        return "properties/manageApplications";
    }

}
