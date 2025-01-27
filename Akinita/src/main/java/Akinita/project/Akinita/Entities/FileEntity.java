package Akinita.project.Akinita.Entities;

import groovy.transform.builder.Builder;
import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;

    private String fileType;

    @Lob  // Για αποθήκευση μεγάλων αρχείων
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_application_id")
    private RentalApplication rentalApplication;

    // Constructors, Getters & Setters
    public FileEntity(RentalApplication rentalApplication, String originalFilename, String filePath) {
        this.rentalApplication = rentalApplication;
        this.fileName = originalFilename;
        this.fileType = filePath;
    }

    public FileEntity() { }

    public int getId() { return id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    public RentalApplication getRentalApplication() { return rentalApplication; }
    public void setRentalApplication(RentalApplication rentalApplication) { this.rentalApplication = rentalApplication; }
}