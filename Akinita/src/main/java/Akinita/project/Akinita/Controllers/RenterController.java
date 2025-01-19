package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.*;
import Akinita.project.Akinita.Entities.Actors.User;
import Akinita.project.Akinita.Services.RenterService;
import Akinita.project.Akinita.Services.PropertyService;
import Akinita.project.Akinita.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/RentalApplications")
    public String RenterRentalApplications(Model model, Principal principal) {
        String email = principal.getName();
        Integer renterId = renterService.findRenterIdByEmail(email);
        model.addAttribute("Applications", renterService.getRenterRentalApplications(renterId));
        return "properties/manageApplications";
    }

    @GetMapping("/Search")
    public String SearchProperties(Model model) {
            model.addAttribute("Properties", propertyService.findAllProperties());
            return "properties/searchProperties";
    }

    @GetMapping("/submitRentalApplication")
    public String submitRentalApp(@RequestParam(name = "renter_id", required = true) int renter_id,
                                  @RequestParam(name = "owner_id", required = true) int owner_id,
                                  @RequestParam(name = "property_id", required = true) int property_id,
                                  Model model) {

        RentalApplication rentApp = new RentalApplication((User)userService.getUser(owner_id),
                                                          propertyService.getPropertyById(property_id),
                                                          (User)userService.getUser(renter_id));

        model.addAttribute("Applications", renterService.saveApplication(rentApp));

        return "properties/submitRentalApplication";
    }


}
