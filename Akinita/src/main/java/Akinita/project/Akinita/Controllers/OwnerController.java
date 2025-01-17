package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.*;
import Akinita.project.Akinita.Services.OwnerService;
import Akinita.project.Akinita.Services.PropertyService;
import Akinita.project.Akinita.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("Owner")
public class OwnerController {

    private final PropertyService propertyService;

    private final OwnerService ownerService;

    private final UserService userService;


    @Autowired
    public OwnerController(PropertyService propertyService, OwnerService ownerService, UserService userService) {
        this.propertyService = propertyService;
        this.ownerService = ownerService;
        this.userService = userService;
    }

    @GetMapping("/submitProperty")
    public String submitProperty(Model model) {
        model.addAttribute("propertyTypes", new String[]{"House", "Land", "Parking", "CommercialProperty"}); // Προσθήκη των τύπων ακινήτων στο μοντέλο
        return "properties/submitProperty";
    }

    @PostMapping("/submitProperty")
    public String submitProperty(@RequestParam String propertyType, @ModelAttribute Property property, Model model) {      // RealEstate is interface

        int id = propertyService.SaveProperty(propertyType, property);              // epeidh pername ena property object auto shmainei oti oi upoklaseis pou exoun parapanw dedomena tha ta xasoun , To Be fixed
        String message = "Property '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "redirect:/index";
    }


    @GetMapping("/Listings")
    public String ownerListings(Model model, Principal principal) {
        String username = principal.getName();
        Integer ownerId = userService.findByUsername(username);                // check for null
        model.addAttribute("Listings", ownerService.getOwnerProperties(ownerId));
        return "properties/ownerListings";
    }


    @GetMapping("/manageApplications/{propertyId}")
    public String manageApplications(@PathVariable String propertyId, Model model, Principal principal) {
        String username = principal.getName();
        Integer ownerId = userService.findByUsername(username);                  // check for null
        model.addAttribute("Applications", ownerService.getOwnerRentalApplications(ownerId));
        return "properties/manageApplications";
    }

}
