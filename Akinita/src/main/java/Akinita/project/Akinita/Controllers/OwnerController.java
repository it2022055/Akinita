package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Services.OwnerService;
import Akinita.project.Akinita.entities.CommercialProperty;
import Akinita.project.Akinita.entities.House;
import Akinita.project.Akinita.entities.Land;
import Akinita.project.Akinita.entities.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("Owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/submitProperty")
    public String submitProperty(Model model) {
        model.addAttribute("propertyTypes", new String[]{"House", "Land", "Parking", "CommercialProperty"}); // Προσθήκη των τύπων ακινήτων στο μοντέλο
        return "properties/submitProperty";
    }

    @PostMapping("/submitProperty")
    public String submitProperty(@RequestParam String propertyType, @ModelAttribute RealEstate realEstate, Model model) {
        RealEstate newProperty;

        switch(propertyType) {
            case "House":
                newProperty = new House();
                break;
            case "Land":
                newProperty = new Land();
                break;
            case "Parking":
                newProperty = new Parking();
                break;
            case "CommercialProperty":
                newProperty = new CommercialProperty();
                break;
            default:
                throw new IllegalArgumentException("Invalid property type");
        }
        //Integer id = RealEstateService.saveProperty(newProperty);
        //String message = "Property '"+id+"' saved successfully !";
        //model.addAttribute("msg", message);
        return "redirect:/index";
    }


    @GetMapping("/Listings")
    public String ownerListings(Model model, Principal principal) {
        String username = principal.getName();
        //Integer ownerId = ownerService.findOwnerIdByUsername(username);
        //model.addAttribute("Listings", ownerService.getOwnerProperties(ownerId));
        return "properties/ownerListings";
    }


    @GetMapping("/manageApplications/{propertyId}")
    public String manageApplications(@PathVariable String propertyId, Model model, Principal principal) {
        String username = principal.getName();
        //Integer ownerId = ownerService.findOwnerIdByUsername(username);
        //model.addAttribute("Applications", ownerService.getOwnerApplications(ownerId));
        return "properties/manageApplications";
    }

    @GetMapping("/searchProperties")
    public String searchProperties(Model model) {
        //model.addAttribute("teachers", RealEstateService.getProperties());
        return "properties/searchProperties";
    }

}
