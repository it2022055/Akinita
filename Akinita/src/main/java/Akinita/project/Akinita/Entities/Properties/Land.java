package Akinita.project.Akinita.Entities.Properties;

import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Actors.Renter;
import jakarta.persistence.*;

@Entity
public class Land extends Property {
    public Land(int id, String estateName, String location, Double price, String description, Owner owner, Renter renter, int squareMeter , Boolean availability, String visibility) {
        super(id, estateName, location, price, description, owner, renter,availability, visibility, squareMeter);
    }

    public Land() {
    }
}
