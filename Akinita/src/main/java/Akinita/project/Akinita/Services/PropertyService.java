package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Enums.EnergyClass;
import Akinita.project.Akinita.Entities.Enums.Facilities;
import Akinita.project.Akinita.Entities.Properties.*;
import Akinita.project.Akinita.Repositories.RealEstate.*;
import Akinita.project.Akinita.Repositories.RentalApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static Akinita.project.Akinita.Entities.Enums.Facilities.*;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class PropertyService {

    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    HouseGenericRepository houseRepository;
    @Autowired
    LandGenericRepository landRepository;
    @Autowired
    ParkingGenericRepository parkingRepository;
    @Autowired
    CommercialPropertyGenericRepository commercialPropertyRepository;
    @Autowired
    RentalApplicationRepository rentalApplicationRepository;


    public List<Property> findProperties(String location, String propertyType, Double minPrice, Double maxPrice,
                                         Integer minSize, Integer maxSize, Boolean buildingFees, Date constructionDate,
                                         Double priceSlider, Integer sizeSlider, Boolean availability, List<Facilities> facilities,
                                         EnergyClass energyClass) {

        List<Property> filter = findByTypeAndLocation(propertyType, location);                 // Filtering by Type and Location

        filter = findByPriceAndSize(filter,minPrice, maxPrice, priceSlider, minSize, maxSize, sizeSlider);        // Filtering by Price and Size

        filter = findByBuildingFees(filter, propertyType, buildingFees);                          // Filtering by Building fees

        filter = findByConstructionDate(filter, propertyType, constructionDate);        // Filtering by construction date

        filter = findByAvailability(filter, availability);

        filter = findByFacilities(filter, facilities, propertyType);

        filter = findByEnergyClass(filter, propertyType, energyClass);

        return filter;
    }

    private List<Property> findByTypeAndLocation(String type,String location) {
        List<Property> property_results = List.of();

        if (location.equals("All")) {
            switch (type) {
                case "All" -> property_results = propertyRepository.findAll();
                case "House" -> property_results = houseRepository.findAllProperties();
                case "CommercialProperty" -> property_results = commercialPropertyRepository.findAllProperties();
                case "Parking" -> property_results = parkingRepository.findAllProperties();
                case "Land" -> property_results = landRepository.findAllProperties();
            }
        } else {
            // Αν η τοποθεσία δεν είναι "All", πρέπει να αναζητήσουμε με βάση και τον τύπο και την τοποθεσία.
            switch (type) {
                case "All" -> property_results = propertyRepository.findPropertiesByLocation(location);
                case "House" -> property_results = houseRepository.findCommonPropertiesByLocation(location);
                case "CommercialProperty" -> property_results = commercialPropertyRepository.findCommonPropertiesByLocation(location);
                case "Parking" -> property_results = parkingRepository.findCommonPropertiesByLocation(location);
                case "Land" -> property_results = landRepository.findCommonPropertiesByLocation(location);
            }
        }
        return property_results;
    }

    private List<Property> findByPriceAndSize(List<Property> properties, Double minPrice, Double maxPrice, Double priceSlider,Integer minSize, Integer maxSize, Integer sizeSlider) {
        return properties.stream()
                .filter(p -> minPrice == null || p.getPrice() >= minPrice) // Φιλτράρισμα με βάση την τιμή
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .filter(p -> minSize == null || p.getSquareMeter() >= minSize)  // Φιλτράρισμα με βάση το μέγεθος
                .filter(p -> maxSize == null || p.getSquareMeter() <= maxSize)
                .filter(p -> priceSlider == null || p.getPrice() <= priceSlider) // Φιλτράρισμα με βάση το slider
                .filter(p -> sizeSlider == null || p.getSquareMeter() <= sizeSlider)
                .collect(Collectors.toList()); // Επιστροφή της φιλτραρισμένης λίστας
    }

    private List<Property> findByBuildingFees(List<Property> properties, String type, Boolean buildingFees) {
        return properties.stream()
                .filter(p -> !type.equals("House") || buildingFees == null || houseRepository.findByHouseId(p.getId()).getBuildingFees().equals(buildingFees))
                .filter(p -> !type.equals("CommercialProperty") || buildingFees == null  || commercialPropertyRepository.findByCommercialPropertyId(p.getId()).getBuildingFees().equals(buildingFees))
                .collect(Collectors.toList());
    }

    private List<Property> findByConstructionDate(List<Property> properties, String type, Date constructionDate) {
        return properties.stream()
                .filter(p -> !type.equals("House") || constructionDate == null || houseRepository.findByHouseId(p.getId()).getConstructionDate().after(constructionDate))
                .filter(p -> !type.equals("CommercialProperty") || constructionDate == null || commercialPropertyRepository.findByCommercialPropertyId(p.getId()).getConstructionDate().after(constructionDate))
                .filter(p -> !type.equals("Parking") || constructionDate == null || parkingRepository.findByParkingId(p.getId()).getConstructionDate().after(constructionDate))
                .collect(Collectors.toList());
    }

    private List<Property> findByAvailability(List<Property> properties, Boolean availability) {
        return properties.stream()
                .filter(p -> availability == null || p.getAvailability().equals(availability))
                .collect(Collectors.toList());
    }

    private List<Property> findByFacilities(List<Property> properties, List<Facilities> facilities, String type) {
        return properties.stream()
                .filter(p -> !type.equals("House") || facilities == null || facilities.contains(ALL) || houseRepository.hasFacilities(p.getId(),facilities ,facilities.size()))
                .filter(p -> !type.equals("CommercialProperty") || facilities == null || facilities.contains(ALL) || commercialPropertyRepository.hasFacilities(p.getId(),facilities,facilities.size()))
                .collect(Collectors.toList());
    }

    private List<Property> findByEnergyClass(List<Property> properties, String type, EnergyClass energyClass) {
        return properties.stream()
                .filter(p -> !type.equals("House") || energyClass == null || houseRepository.hasEnergyClass(p.getId(),energyClass))
                .filter(p -> !type.equals("CommercialProperty") || energyClass == null || commercialPropertyRepository.hasEnergyClass(p.getId(),energyClass))
                .filter(p -> !type.equals("Parking") || energyClass == null || parkingRepository.hasEnergyClass(p.getId(),energyClass))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateProperty(Property property) { //Μέθοδος ενημέρωσης property
        propertyRepository.save(property);
    }

    public List<Property> findAllInvisibleProperties(){return propertyRepository.findByVisibility("Invisible");} //Μέθοδος επιστροφής μη αποδεχτών properties

    public Property getPropertyById(int id) { //Μέθοδος επιστροφής ιδιοκτησίας απο το ID
        return propertyRepository.findPropertyById(id);
    }

    public void SaveLandProperty(Land land) {
        landRepository.save(land);
    } //Μέθοδος αποθήκευσης Land

    public void SaveHouseProperty(House house){
        houseRepository.save(house);
    } //Μέθοδος αποθήκευσης house

    public void SaveParkingProperty(Parking parking){
        parkingRepository.save(parking);
    } //Μέθοδος αποθήκευσης parking

    public void SaveCommercialProperty(CommercialProperty commercialProperty){
        commercialPropertyRepository.save(commercialProperty);
    } //Μέθοδος αποθήκευσης commercial property

    @Transactional
    public void DeleteProperty(Property property, int propertyId) {
        rentalApplicationRepository.deleteByPropertyId(propertyId);
        propertyRepository.delete(property);} //Μέθοδος διαγραφής property

    public List<Property> findPropertiesByRenterId(int renterId) {
        return propertyRepository.findByRenterId(renterId);
    }

    public List<Facilities>  getPropertyFacilitiesById(int propertyId) {
        List<Facilities> facilities = new ArrayList<>();
        try{
            facilities.addAll(houseRepository.findFacilitiesByPropertyId(propertyId));
            if (!facilities.isEmpty()) {
                return facilities;
            }
            facilities.addAll(commercialPropertyRepository.findFacilitiesByPropertyId(propertyId));
            return facilities;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
}
