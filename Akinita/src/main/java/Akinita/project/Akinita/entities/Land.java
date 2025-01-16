package Akinita.project.Akinita.entities;

import Akinita.project.Akinita.Interfaces.RealEstate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class Land extends Property {
    public Land(int id, String estateName, String location, int price, String description, Owner owner, boolean availability, String visibility) {
        super(id, estateName, location, price, description, owner, availability, visibility);
    }

    public Land() {
    }
}
