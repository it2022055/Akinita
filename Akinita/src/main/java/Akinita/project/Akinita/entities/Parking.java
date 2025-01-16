package Akinita.project.Akinita.entities;

import Akinita.project.Akinita.Interfaces.LimitedMethods.ConstructionDate;
import Akinita.project.Akinita.Interfaces.RealEstate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class Parking extends Property implements ConstructionDate {

    public Parking(int id, String estateName, String location, int price, String description, Owner owner, boolean availability, String visibility, Date constructionDate) {
        super(id, estateName, location, price, description, owner, availability, visibility);
        this.constructionDate = constructionDate;
    }


    @Past(message = "The construction date must be in the past")
    @Temporal(TemporalType.DATE)                                       // Date is saved without 'hours'
    @Column(name = "construction_date")
    private Date constructionDate;

    public Parking() {

    }

    @Override
    public Date getConstructionDate() {
        return constructionDate;
    }

    @Override
    public void setConstructionDate(Date date) {

    }
}
