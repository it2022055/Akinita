package Akinita.project.Akinita.entities;

import Akinita.project.Akinita.Interfaces.RealEstate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class Land implements RealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Estate name is required")
    @Size(max = 50)
    @Column(name = "estate_name")
    private String estateName;

    @NotBlank(message = "Location is required")
    @Size(max = 50)
    @Column
    private String location;

    @NotBlank(message = "Price is required")
    @Size(max = 10)
    @Column
    private int price;

    @NotBlank(message = "Description is required")
    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @NotBlank(message = "Availability for Sale is required")
    @Column(name = "availability")
    private boolean availability;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getEstateName() {
        return estateName;
    }

    @Override
    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isAvailableForSale() {
        return availability;
    }

    @Override
    public void setAvailableForSale(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public Owner whoIsOwner() {
        return owner;
    }

    @Override
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}
