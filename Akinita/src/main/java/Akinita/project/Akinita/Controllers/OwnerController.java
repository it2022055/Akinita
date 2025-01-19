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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
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
    public String submitNewProperty( Principal principal,@RequestParam("SqMeters") int sqMeters, @RequestParam("estateΝame") String estateΝame, @RequestParam("description") String description, @RequestParam("price") int price, @RequestParam("location") String location, @RequestParam("propertyType") String propertyType, @ModelAttribute RealEstate realEstate, Model model, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        Owner owner=ownerService.findById(ownerId);
        model.addAttribute("estateΝame", estateΝame);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("location", location);
        model.addAttribute("SqMeters", sqMeters);
        model.addAttribute("owner", owner);
        model.addAttribute("propertyType", propertyType);
        if(!propertyType.equals("Land")) {
            redirectAttributes.addFlashAttribute("estateΝame", estateΝame);
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
            land.setEstateName(estateΝame);
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
        System.out.println("Method PROPERTY FACILITISE start");
        // Ανάκτηση δεδομένων από το μοντέλο
        String estateName = (String) model.getAttribute("estateΝame");
        String location = (String) model.getAttribute("location");
        Integer price = (Integer) model.getAttribute("price");
        String description = (String) model.getAttribute("description");
        Integer sqMeters = (Integer) model.getAttribute("SqMeters");
        String propertyType = (String) model.getAttribute("propertyType");
        Owner owner = (Owner) model.getAttribute("owner");
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
            house.setRenter(null);
            try{
                propertyService.SaveHouseProperty(house);
            }catch(Exception e){
                e.printStackTrace();
                return "redirect:/Owner/propertySubmitted";
            }
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
            parking.setRenter(null);
            try{
                propertyService.SaveParkingProperty(parking);
            }catch(Exception e){
                e.printStackTrace();
                return "redirect:/Owner/propertySubmitted";
            }
        } else if (propertyType.equals("CommercialProperty")) {
            CommercialProperty commercialProperty = new CommercialProperty();
            commercialProperty.setLocation(location);
            commercialProperty.setEstateName(estateName);
            commercialProperty.setDescription(description);
            commercialProperty.setPrice(price);
            commercialProperty.setVisibility("Invisible");
            commercialProperty.setAvailability(true);
            commercialProperty.setSquareMeter(sqMeters);
            commercialProperty.setOwner(owner);
            commercialProperty.setRenter(null);
            try{
                propertyService.SaveCommercialProperty(commercialProperty);
            }catch(Exception e){
                e.printStackTrace();
                return "redirect:/Owner/propertySubmitted";
            }
        }
        return "properties/propertyFacilities";
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


    @GetMapping("/manageApplications")
    public String manageApplications(Model model, Principal principal) {
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        model.addAttribute("Applications", ownerService.getOwnerRentalApplications(ownerId));
        return "properties/manageApplications";
    }

}
