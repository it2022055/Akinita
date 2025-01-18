package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.*;
import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Services.OwnerService;
import Akinita.project.Akinita.Services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

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
    public String submitNewProperty( Principal principal, @RequestParam("estateΝame") String estateΝame, @RequestParam("description") String description, @RequestParam("price") int price, @RequestParam("location") String location, @RequestParam("propertyType") String propertyType, @ModelAttribute RealEstate realEstate, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        System.out.println("Method execution start");
        System.out.println("estatename: " + estateΝame);
        System.out.println("location: " + location);
        System.out.println("price: " + price);
        System.out.println("description: " + description);
        System.out.println("propertyType: " + propertyType);
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        Owner owner=ownerService.findById(ownerId);
        model.addAttribute("estateΝame", estateΝame);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("location", location);
        if(!propertyType.equals("Land")) {
            return "redirect:/properties/propertyFacilities";
        }else{
            Land land = new Land();
            land.setLocation(location);
            land.setEstateName(estateΝame);
            land.setDescription(description);
            land.setPrice(price);
            land.setVisibility("Invisible");
            land.setAvailability(true);
            land.setOwner(owner);
            propertyService.SaveLandProperty(land);
            return "redirect:/properties/propertySubmitted";
        }

        //RealEstate newProperty = switch (propertyType) {
            //case "House" -> new House();
            //case "Land" -> new Land();
            //case "Parking" -> new Parking();
            //case "CommercialProperty" -> new CommercialProperty();
            //default -> throw new IllegalArgumentException("Invalid property type");
        //};

        //int id = propertyService.SaveProperty(newProperty);

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
