package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Services.PropertyService;
import Akinita.project.Akinita.entities.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("Service")
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/searchProperties")
    public String searchProperties(@RequestParam(name = "area", required = false) String area,
                                   @RequestParam(name = "propertyType", required = false) String propertyType,
                                   Model model) {
        System.out.println("Area: " + area);
        System.out.println("Property Type: " + propertyType);
        model.addAttribute("Properties", propertyService.findAllProperties(area, propertyType));
        return "redirect:/properties/searchProperties";
    }

}
