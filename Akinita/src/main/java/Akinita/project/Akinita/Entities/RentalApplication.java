package Akinita.project.Akinita.Entities;

import Akinita.project.Akinita.Entities.Actors.User;
import Akinita.project.Akinita.Entities.Properties.Property;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Path;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Entity
public class RentalApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id") // Ξένο κλειδί προς τον πίνακα Owner
    private User owner; // Αναφορά στον ιδιοκτήτη

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id") // Ξένο κλειδί προς τον πίνακα Property
    private Property property; // Αναφορά στο ακίνητο

    @ManyToOne
    @JoinColumn(name = "renter_id", referencedColumnName = "id") // Ξένο κλειδί προς τον πίνακα Renter
    private User renter; // Αναφορά στον ενοικιαστή

    @Column
    private String renterJob;

    @Column
    private Boolean renterPets;

    @Column
    private Boolean Status;

    @Temporal(TemporalType.DATE)                                       // Date is saved without 'hours'            // Auto to kommati prepei na ginei anathesh otan ton dektei o owner
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "rental_date")
    private Date rentalDate;

    @Column
    private int rentalDuration;

    @Column
    private String description;

    // Constructors
    public RentalApplication() {}

    public RentalApplication(User owner, Property property, User renter) {
        this.owner = owner;
        this.property = property;
        this.renter = renter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters και Setters
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    public void setOwnerId(Integer ownerId) {
        this.owner.setId(ownerId);
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public String getRenterJob() {
        return renterJob;
    }

    public void setRenterJob(String renterJob) {
        this.renterJob = renterJob;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public Boolean getRenterPets() {
        return renterPets;
    }

    public void setRenterPets(Boolean renterPets) {
        this.renterPets = renterPets;
    }

    public int getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}


