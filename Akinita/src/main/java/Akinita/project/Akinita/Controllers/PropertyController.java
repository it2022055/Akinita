package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Services.PropertyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping("Service")
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/searchProperties")
    public String searchProperties(@RequestParam(name = "location", required = false) String location,
                                   @RequestParam(name = "propertyType", required = false) String propertyType,
                                   @RequestParam(name = "minPrice", required = false) Double minPrice,
                                   @RequestParam(name = "maxPrice", required = false) Double maxPrice,
                                   @RequestParam(name = "priceSlider", required = false) Double priceSlider,
                                   @RequestParam(name = "minSize", required = false) Integer minSize,
                                   @RequestParam(name = "maxSize", required = false) Integer maxSize,
                                   @RequestParam(name = "sizeSlider", required = false) Integer sizeSlider,
                                   @RequestParam(name = "buildingFees", required = false) String buildingFees,
                                   @RequestParam(name = "constructionDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date constructionDate,
                                   RedirectAttributes redirectAttributes) {

        boolean bf ;
        if (buildingFees == null ){
            bf = false;
        }else{
            bf = buildingFees.equals("Yes");
        }
        String locationT = (location.isEmpty())? "All" : location;

        System.out.println("Location: " + locationT);
        System.out.println("Property Type: " + propertyType);
        System.out.println("Min Price: " + minPrice);
        System.out.println("Max Price: " + maxPrice);
        System.out.println("Min Size: " + minSize);
        System.out.println("Max Size: " + maxSize);
        System.out.println("Building Fees: " + bf);
        System.out.println("Construction Date: " + constructionDate);
        System.out.println("Size slider: " + sizeSlider);
        System.out.println("Price slider: " + priceSlider);


        List<Property> properties = propertyService.findProperties(locationT, propertyType, minPrice, maxPrice, minSize, maxSize, bf, constructionDate,priceSlider, sizeSlider);
        redirectAttributes.addFlashAttribute("properties", properties);
        return "redirect:/Service/search_results";  // Επιστρέφει την αντίστοιχη σελίδα αποτελεσμάτων
    }

    @GetMapping("/search_results")
    public String RenterSearchResults(Model model) {
        // Λήψη των αποτελεσμάτων από τα FlashAttributes
        List<Property> properties = (List<Property>) model.asMap().get("properties");

        // Προσθήκη των αποτελεσμάτων στο μοντέλο για την προβολή στη σελίδα
        model.addAttribute("properties", properties);
        return "search_results";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/AcceptListings") //Μέθοδος προβολής ιδιοκτησιών προς αποδοχή από τον admin
    public String updateProperties(Model model){
        model.addAttribute("properties", propertyService.findAllInvisibleProperties()); //Προσθήκη ιδιοκτησιών στο model
        return "/properties/updateProperties";
    }

    @Secured("ROLE_ADMIN")
    @Transactional
    @GetMapping("/AcceptListings/{property_id}") //Μέθοδος αποδοχής ιδιοκτησίας από τον admin
    public String updateProperties(@ModelAttribute("properties") Property property,@PathVariable int property_id){
        Property the_property= propertyService.getPropertyById(property_id);
        the_property.setVisibility("Visible"); //Αλλαγή visibility
        try {
            propertyService.updateProperty(the_property); //Αποθήκευση αλλαγών στη Βάση Δεδομένων
        }catch (Exception e){
            throw new RuntimeException("Could not update property");
        }

        return "redirect:/Service/AcceptListings";

    }

}