package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.*;
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
    public String SearchProperties(Model model, Principal principal) {
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

//    @PostMapping("/saveUser")
//    public String saveUser(@ModelAttribute User user, @RequestParam("role") String role,  @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,@RequestParam("telephone") String telephone, Model model) {
//        User saveduser = userService.saveUser(user, role);
//        if (role.equals("ROLE_OWNER")){
//            Owner newOwner=new Owner(saveduser,firstname,lastname,telephone);
//            ownerService.save(newOwner);
//        }else{
//            Renter newRenter=new Renter();
//            renterRepository.save(newRenter);
//        }
//        String message = "User '" + saveduser.getId() + "' saved successfully with role: " + role;
//        model.addAttribute("msg", message);
//
//        return "redirect:/login";
//    }

//    @PostMapping("/submitProperty")
//    public String submitProperty(@RequestParam String propertyType, @ModelAttribute RealEstate realEstate, Model model) {
//        RealEstate newProperty;
//
//        switch(propertyType) {
//            case "House":
//                newProperty = new House();
//                break;
//            case "Land":
//                newProperty = new Land();
//                break;
//            case "Parking":
//                newProperty = new Parking();
//                break;
//            case "CommercialProperty":
//                newProperty = new CommercialProperty();
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid property type");
//        }
//        Integer id = propertyService.SaveProperty(newProperty);
//        String message = "Property '"+id+"' saved successfully !";
//        model.addAttribute("msg", message);
//        return "redirect:/index";
//    }
//
//    @GetMapping("/manageApplications/{propertyId}")
//    public String manageApplications(@PathVariable String propertyId, Model model, Principal principal) {
//        String email = principal.getName();
//        Integer ownerId = ownerService.findOwnerIdByEmail(email);
//        model.addAttribute("Applications", ownerService.getOwnerRentalApplications(ownerId));
//        return "properties/manageApplications";
//    }

}
