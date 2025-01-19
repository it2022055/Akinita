package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Properties.Land;
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
        System.out.println("Method execution start");
        System.out.println("estatename: " + estateΝame);
        System.out.println("location: " + location);
        System.out.println("price: " + price);
        System.out.println("description: " + description);
        System.out.println("propertyType: " + propertyType);
        System.out.println("SqMeters: " + sqMeters);
        String email = principal.getName();
        Integer ownerId = ownerService.findOwnerIdByEmail(email);
        Owner owner=ownerService.findById(ownerId);
        model.addAttribute("estateΝame", estateΝame);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("location", location);
        model.addAttribute("SqMeters", sqMeters);
        if(!propertyType.equals("Land")) {
            redirectAttributes.addFlashAttribute("estateΝame", estateΝame);
            redirectAttributes.addFlashAttribute("description", description);
            redirectAttributes.addFlashAttribute("price", price);
            redirectAttributes.addFlashAttribute("location", location);
            redirectAttributes.addFlashAttribute("SqMeters", sqMeters);
            redirectAttributes.addFlashAttribute("owner", owner);
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
        System.out.println("estateName: " + model.getAttribute("estateΝame"));
        System.out.println("location: " + model.getAttribute("location"));
        System.out.println("price: " + model.getAttribute("price"));
        System.out.println("description: " + model.getAttribute("description"));
        System.out.println("SqMeters: " + model.getAttribute("SqMeters"));
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
