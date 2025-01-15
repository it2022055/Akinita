package Akinita.project.Akinita.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.*;

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

    // Constructors
    public RentalApplication() {}


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
}


