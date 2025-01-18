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

@Controller
@RequestMapping("Owner")
public class OwnerController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/submitProperty")
    public String submitProperty() {
        return "properties/propertyFacilities";
    }

    @PostMapping("/submitNewProperty")
    public String submitProperty(@RequestParam("estatename") String estatename, @RequestParam("description") String description, @RequestParam("price") int price, @RequestParam("location") String location, @RequestParam("propertyType") String propertyType, @ModelAttribute RealEstate realEstate, Model model) {
        Property property=new Property();
        model.addAttribute("property", property);
        model.addAttribute("estatename", estatename);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("location", location);
        if(!property.equals("Land")) {
            return "properties/propertyFacilities";
        }else{
            return "redirect:/index";
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


    @GetMapping("/Listings")
    public String ownerListings(Model model, Principal principal) {
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        model.addAttribute("Listings", ownerService.getOwnerProperties(ownerId));
        return "properties/ownerListings";
    }


    @GetMapping("/manageApplications/{propertyId}")
    public String manageApplications(@PathVariable String propertyId, Model model, Principal principal) {
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        model.addAttribute("Applications", ownerService.getOwnerRentalApplications(ownerId));
        return "properties/manageApplications";
    }

}
