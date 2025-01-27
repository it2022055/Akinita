package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.FileEntity;
import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Repositories.User.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service

public class FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    public ByteArrayResource getFile(int fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        // Δημιουργία Resource από τα byte[] δεδομένα του αρχείου
        return new ByteArrayResource(fileEntity.getData());
    }

    public Optional<FileEntity> getFileById(int fileId) {
        return fileRepository.findById(fileId);
    }

    @Transactional
    public List<Integer> findId (int appId) {
        return fileRepository.findAllTheId(appId);
    }

    public FileEntity getFileByIdAndAppId(Integer fileId, Integer appId) {
        return fileRepository.findByIdAndAppId(fileId, appId);
    }
    public void saveFile(MultipartFile file, RentalApplication application) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setRentalApplication(application); // Σύνδεση με την αίτηση

        fileRepository.save(fileEntity);
    }

    public List<FileEntity> findAllById(Integer appId) {
        return fileRepository.findAllById(appId);
    }
}