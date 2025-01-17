package Akinita.project.Akinita.Entities;

import Akinita.project.Akinita.Interfaces.LimitedMethods.BuildingFees;
import Akinita.project.Akinita.Interfaces.LimitedMethods.ConstructionDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.util.Date;

@Entity
public class House extends Property implements BuildingFees, ConstructionDate {

    public House(int id, String estateName, String location, int price, String description, Owner owner, boolean availability, String visibility, Date constructionDate, boolean buildingFees) {
        super(id, estateName, location, price, description, owner, availability, visibility);
        this.constructionDate = constructionDate;
        this.buildingFees = buildingFees;
    }

    @Past(message = "The construction date must be in the past")
    @Temporal(TemporalType.DATE)                                       // Date is saved without 'hours'
    @Column(name = "construction_date")
    private Date constructionDate;

    @NotNull(message = "Building fees are required")
    @Column(name = "building_fees")
    private boolean buildingFees;

    public House() {

    }


    @NotBlank(message = "Building fees are required")
    public boolean getBuildingFees() {
        return buildingFees;
    }

    @Override
    public void setBuildingFees(boolean buildingFees) {
        this.buildingFees = buildingFees;
    }

    @Override
    public Date getConstructionDate() {
        return constructionDate;
    }

    @Override
    public void setConstructionDate(Date date) {

    }
}
