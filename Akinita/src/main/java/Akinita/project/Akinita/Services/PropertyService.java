package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Properties.*;
import Akinita.project.Akinita.Repositories.RealEstate.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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


    public int SaveProperty(Property property) { //Δες αν το θελεις αυτό- Ζαχος
        propertyRepository.save(property);
        return propertyRepository.findIdByProperty(property);
    }

    public List<Property> findProperties(String location, String propertyType, Double minPrice, Double maxPrice, Integer minSize, Integer maxSize, Boolean buildingFees, Date constructionDate, Double priceSlider, Integer sizeSlider, Boolean availability) {

        List<Property> filter = findByTypeAndLocation(propertyType, location);                 // Filtering by Type and Location

        filter = findByPriceAndSize(filter,minPrice, maxPrice, priceSlider, minSize, maxSize, sizeSlider);        // Filtering by Price and Size

        System.out.println(filter);

        filter = findByBuildingFees(filter, propertyType, buildingFees);                          // Filtering by Building fees

        System.out.println("2" + filter);

        filter = findByConstructionDate(filter, propertyType, constructionDate);        // Filtering by construction date

        System.out.println("3" + filter);

        filter = findByAvailability(filter, availability);

        System.out.println("4" +  filter);

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

    @Transactional
    public Integer updateProperty(Property property) { //Μέθοδος ενημέρωσης property
        property=propertyRepository.save(property);
        return property.getId();
    }
    public List findAllProperties() { //Δες αν το θελεις αυτό- Ζαχος
        return propertyRepository.findByAvailability(true);
    }

    public List<Property> findAllInvisibleProperties(){return propertyRepository.findByVisibility("Invisible");} //Μέθοδος επιστροφής μη αποδεχτών properties

    public Property getPropertyById(int id) { //Μέθοδος επιστροφής ιδιοκτησίας απο το ID
        return propertyRepository.findPropertyById(id);
    }

    public Land SaveLandProperty(Land land) {
        return landRepository.save(land);
    } //Μέθοδος αποθήκευσης Land

    public House SaveHouseProperty(House house){return houseRepository.save(house);} //Μέθοδος αποθήκευσης house

    public Parking SaveParkingProperty(Parking parking){return parkingRepository.save(parking);} //Μέθοδος αποθήκευσης parking

    public CommercialProperty SaveCommercialProperty(CommercialProperty commercialProperty){return commercialPropertyRepository.save(commercialProperty);} //Μέθοδος αποθήκευσης commercial property

    public void DeleteProperty(Property property) {propertyRepository.delete(property);} //Μέθοδος διαγραφής property


    public Integer findOwnerIdByPropertyId(int propertyId) { //Δες αν το θελεις αυτό- Ζαχος
        return propertyRepository.findOwnerIdByPropertyId(propertyId);
    }
}
