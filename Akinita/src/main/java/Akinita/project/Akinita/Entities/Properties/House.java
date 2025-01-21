package Akinita.project.Akinita.Entities.Properties;

import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Actors.Renter;
import Akinita.project.Akinita.Entities.Enums.EnergyClass;
import Akinita.project.Akinita.Entities.Enums.Facilities;
import Akinita.project.Akinita.Interfaces.LimitedMethods.BuildingFees;
import Akinita.project.Akinita.Interfaces.LimitedMethods.ConstructionDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class House extends Property implements BuildingFees, ConstructionDate {

    @Past(message = "The construction date must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE) // Date is saved without 'hours'
    @Column(name = "construction_date")
    private Date constructionDate;

    @NotNull(message = "Building fees are required")
    @Column(name = "building_fees")
    private Boolean buildingFees;

    @ElementCollection(targetClass = Facilities.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "house_facilities", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "facility")
    private Set<Facilities> facilities = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column
    private EnergyClass energyClass;

    // Getter and Setter
    public EnergyClass getEnergyClass() {
        return energyClass;
    }

    public void setEnergyClass(EnergyClass energyClass) {
        this.energyClass = energyClass;
    }

    public House() {}

    public House(int id, String estateName, String location, int price, String description, Owner owner, Renter renter, int squareMeter, Boolean availability, String visibility, Date constructionDate, boolean buildingFees) {
        super(id, estateName, location, price, description, owner, renter, availability, visibility, squareMeter);
        this.constructionDate = constructionDate;
        this.buildingFees = buildingFees;
    }

    public Set<Facilities> getFacilities() {
        return facilities;
    }

    public void setFacilities(Set<Facilities> facilities) {
        this.facilities = facilities;
    }

    @Override
    public Date getConstructionDate() {
        return constructionDate;
    }

    @Override
    public void setConstructionDate(Date constructionDate) {
        this.constructionDate = constructionDate;
    }

    public Boolean getBuildingFees() {
        return buildingFees;
    }

    @Override
    public void setBuildingFees(Boolean buildingFees) {
        this.buildingFees = buildingFees;
    }
}

