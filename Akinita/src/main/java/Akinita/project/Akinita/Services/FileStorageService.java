package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.FileEntity;
import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Repositories.User.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ApplicationService applicationService;

    public FileEntity saveFile(MultipartFile file, int rpId) throws IOException {
        RentalApplication rentalApplication = applicationService.getApplication(rpId);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setRentalApplication(rentalApplication);  // Σύνδεση του αρχείου με τον χρήστη

        return fileRepository.save(fileEntity);
    }
}
