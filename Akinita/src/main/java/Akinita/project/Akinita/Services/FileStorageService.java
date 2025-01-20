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

    public FileEntity saveFile(MultipartFile file, RentalApplication application) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setRentalApplication(application); // Σύνδεση με την αίτηση

        return fileRepository.save(fileEntity);
    }
}