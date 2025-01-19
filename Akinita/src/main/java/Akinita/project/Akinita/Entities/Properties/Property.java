package Akinita.project.Akinita.Entities.Properties;

import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Actors.Renter;
import Akinita.project.Akinita.Interfaces.RealEstate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Property implements RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Estate name is required")
    @Size(max = 50)
    @Column(name = "estate_name")
    private String estateName;

    @NotBlank(message = "Location is required")
    @Size(max = 50)
    @Column
    private String location;

    @NotNull(message = "Price is required")
    @Column
    private int price;

    @NotBlank(message = "Description is required")
    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = true)
    private Renter renter;

    @NotNull(message = "Availability for Sale is required")
    @Column(name = "availability")
    private Boolean availability;

    @NotNull(message = "Square meter is required")
    @Column(name = "square_meter")
    private int squareMeter;

    @Column(name = "visibility")
    private String visibility;

    public Property(int id, String estateName, String location, int price, String description, Owner owner, Renter renter, Boolean availability, String visibility, int squareMeter) {
        this.id = id;
        this.estateName = estateName;
        this.location = location;
        this.price = price;
        this.description = description;
        this.owner = owner;
        this.renter = renter;
        this.availability = availability;
        this.visibility = visibility;
        this.squareMeter = squareMeter;
    }

    public Property() {

    }

    public Boolean isAvailableForSale() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

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

    @Override
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public int getOwnerId() {
        return owner.getUserId();
    }

    @Override
    public int getRenterId() {
        return renter.getUserId();
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    @NotNull(message = "Square meter is required")
    public int getSquareMeter() {
        return squareMeter;
    }

    public void setSquareMeter(@NotNull(message = "Square meter is required") int squareMeter) {
        this.squareMeter = squareMeter;
    }
}
