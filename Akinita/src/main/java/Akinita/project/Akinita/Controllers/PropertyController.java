package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("Service")
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/searchProperties")
    public String searchProperties(@RequestParam(name = "area", required = false) String area,
                                   @RequestParam(name = "propertyType", required = false) String propertyType,
                                   @RequestParam(name = "minPrice", required = false) Double minPrice,
                                   @RequestParam(name = "maxPrice", required = false) Double maxPrice,
                                   @RequestParam(name = "priceSlider", required = false) Double priceSlider,
                                   @RequestParam(name = "minSize", required = false) Double minSize,
                                   @RequestParam(name = "maxSize", required = false) Double maxSize,
                                   @RequestParam(name = "sizeSlider", required = false) Double sizeSlider,
                                   @RequestParam(name = "buildingFees", required = false) String buildingFees,
                                   @RequestParam(name = "constructionDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date constructionDate,
                                   Model model) {

        boolean bf = buildingFees.equals("yes");

        System.out.println("Area: " + area);
        System.out.println("Property Type: " + propertyType);
        System.out.println("Min Price: " + minPrice);
        System.out.println("Max Price: " + maxPrice);
        System.out.println("Min Size: " + minSize);
        System.out.println("Max Size: " + maxSize);
        System.out.println("Building Fees: " + bf);
        System.out.println("Construction Date: " + constructionDate);
        System.out.println("Size slider: " + sizeSlider);
        System.out.println("Price slider: " + priceSlider);


        List properties = propertyService.findProperties(area, propertyType, minPrice, maxPrice, minSize, maxSize, bf, constructionDate,priceSlider, sizeSlider);
        model.addAttribute("properties", properties);
        return "search_results";  // Επιστρέφει την αντίστοιχη σελίδα αποτελεσμάτων
    }


}
