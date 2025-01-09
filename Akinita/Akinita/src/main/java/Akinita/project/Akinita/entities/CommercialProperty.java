package Akinita.project.Akinita.entities;

import Akinita.project.Akinita.Interfaces.RealEstate;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class CommercialProperty implements RealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "estate_name") // Προαιρετικά, αν θέλεις να ορίσεις το όνομα της στήλης
    private String estateName;

    @Column(name = "location")
    private String location;

    @Column(name = "price")
    private int price;

    @Column(name = "construction_date")
    private Date constructionDate;

    @Column(name = "description")
    private String description;

    @Column(name = "building_fees")
    private boolean buildingFees;

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
    public Date getConstructionDate() {
        return constructionDate;
    }

    @Override
    public void setConstructionDate(Date constructionDate) {
        this.constructionDate = constructionDate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBuildingFees() {
        return buildingFees;
    }

    public void setBuildingFees(boolean buildingFees) {
        this.buildingFees = buildingFees;
    }
}
