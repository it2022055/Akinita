package Akinita.project.Akinita.Interfaces;

import Akinita.project.Akinita.Entities.Actors.Owner;

public interface RealEstate {

    // Id
    int getId();
    void setId(int id);

    // Estate Name
    String getEstateName();
    void setEstateName(String estateName);

    // Location
    String getLocation();
    void setLocation(String location);

    // Price
    Double getPrice();
    void setPrice(Double price);

    // Description
    String getDescription();
    void setDescription(String description);

    // Sale Availability
    Boolean isAvailableForSale();
    void setAvailability(Boolean availability);

    // Owner
    void setOwner(Owner owner);

    int getOwnerId();
    int getRenterId();
}
