package Akinita.project.Akinita.Controllers;

import Akinita.project.Akinita.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload/{userId}")
    public String uploadFile(@RequestParam("file") MultipartFile file, @PathVariable int userId, Model model) {      // gia save arxeiwn
        try {
            fileStorageService.saveFile(file, userId);
            model.addAttribute("message", "File uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }
        return "uploadStatus";
    }
}