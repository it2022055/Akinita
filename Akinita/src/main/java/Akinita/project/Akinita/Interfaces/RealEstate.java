package Akinita.project.Akinita.Interfaces;

import Akinita.project.Akinita.entities.Owner;

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
    int getPrice();
    void setPrice(int price);

    // Description
    String getDescription();
    void setDescription(String description);

    // Sale Availability
    boolean isAvailableForSale();
    void setAvailableForSale(Boolean availability);

    // Owner
    Owner whoIsOwner();
    void setOwner(Owner owner);

}
