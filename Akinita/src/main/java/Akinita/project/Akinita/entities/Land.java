package Akinita.project.Akinita.entities;

import Akinita.project.Akinita.Interfaces.RealEstate;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Land implements RealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "estate_name") // Προαιρετικά, αν θέλεις να ορίσεις το όνομα της στήλης
    private String estateName;

    @Column
    private String location;

    @Column(name = "price")
    private int price;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;


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
}
