package Akinita.project.Akinita.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(Model model) { //Μέθοδος επιστροφής κεντρικής σελίδας(αναζήτηση ιδιοκτησίας)

        model.addAttribute("title", "Home");
        return "index";
    }
}

