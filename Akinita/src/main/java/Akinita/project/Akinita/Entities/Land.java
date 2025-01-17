package Akinita.project.Akinita.Entities;

import jakarta.persistence.*;

@Entity
public class Land extends Property {
    public Land(int id, String estateName, String location, int price, String description, Owner owner, boolean availability, String visibility) {
        super(id, estateName, location, price, description, owner, availability, visibility);
    }

    public Land() {
    }
}
