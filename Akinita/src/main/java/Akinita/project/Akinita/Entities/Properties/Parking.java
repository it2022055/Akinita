package Akinita.project.Akinita.Entities.Properties;

import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Actors.Renter;
import Akinita.project.Akinita.Interfaces.LimitedMethods.ConstructionDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Parking extends Property implements ConstructionDate {

    public Parking(int id, String estateName, String location, int price, String description, Owner owner, Renter renter, Boolean availability, String visibility, Date constructionDate, int squareMeter) {
        super(id, estateName, location, price, description, owner, renter, availability, visibility, squareMeter);
        this.constructionDate = constructionDate;
    }


    @Past(message = "The construction date must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
        this.constructionDate = date;
    }
}
