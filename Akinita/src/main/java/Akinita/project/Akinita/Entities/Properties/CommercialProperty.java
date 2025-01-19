package Akinita.project.Akinita.Entities.Properties;

import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Actors.Renter;
import Akinita.project.Akinita.Interfaces.LimitedMethods.BuildingFees;
import Akinita.project.Akinita.Interfaces.LimitedMethods.ConstructionDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.util.Date;

@Entity
public class CommercialProperty extends Property implements ConstructionDate, BuildingFees {

    public CommercialProperty(int id, String estateName, String location, int price, String description, Owner owner, Renter renter, int squareMeter, Boolean availability, String visibility, Date constructionDate, boolean buildingFees) {
        super(id, estateName, location, price, description, owner, renter,availability, visibility, squareMeter);
        this.constructionDate = constructionDate;
        this.buildingFees = buildingFees;
    }

    @Past(message = "The construction date must be in the past")
    @Temporal(TemporalType.DATE)                                       // Date is saved without 'hours'
    @Column(name = "construction_date")
    private Date constructionDate;

    @NotNull(message = "Building fees are required")
    @Column(name = "building_fees")
    private Boolean buildingFees;

    public CommercialProperty() {

    }

    public boolean getBuildingFees() {
        return buildingFees;
    }

    @Override
    public void setBuildingFees( Boolean buildingFees) {
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
