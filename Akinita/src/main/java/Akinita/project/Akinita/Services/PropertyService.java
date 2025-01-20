package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Actors.User;
import Akinita.project.Akinita.Entities.Properties.*;
import Akinita.project.Akinita.Repositories.RealEstate.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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


    public int SaveProperty(Property property) {
        propertyRepository.save(property);
        return propertyRepository.findIdByProperty(property);
    }

    public List<? extends Property> findAllPropertiesByLocation(String location, String propertyType) {
        return switch (propertyType) {
            case "House" -> houseRepository.findByLocation(location);
            case "Land" -> landRepository.findByLocation(location);
            case "Parking" -> parkingRepository.findByLocation(location);
            case "CommercialProperty" -> commercialPropertyRepository.findByLocation(location);
            default -> throw new IllegalArgumentException("Invalid property type");
        };
    }

    public List findProperties(String area, String propertyType, Double minPrice, Double maxPrice, Double minSize, Double maxSize, Boolean buildingFees, Date constructionDate, Double priceSlider, Double sizeSlider) {

        if (propertyType.equals("All")) {
            return propertyRepository.findAll();
        }


        List<Property> property_results = switch (propertyType) {
            case "House" -> houseRepository.findCommonProperties(area);
            case "Commercial Property" -> commercialPropertyRepository.findCommonProperties(area);
            case "Parking" -> parkingRepository.findCommonProperties(area);
            default -> landRepository.findAllProperties();
        };

        List<Property> price_results = new ArrayList<>();

        if(priceSlider == null) {
            price_results = propertyRepository.findPropertiesWithinPriceRange(minPrice,maxPrice);
        } else {
            price_results = propertyRepository.findByPriceLessThanEqual(priceSlider);
        }

        List<Property> size_results = new ArrayList<>();


        if(sizeSlider == null) {
            size_results = propertyRepository.findPropertiesWithinPriceRange(minSize,maxSize);
        } else {
            size_results = propertyRepository.findBySquareMeterLessThanEqual(sizeSlider);
        }

        List<Property> bf_results = new ArrayList<>();
        bf_results.addAll(houseRepository.findByBuildingFees(buildingFees));
        bf_results.addAll(commercialPropertyRepository.findByBuildingFees(buildingFees));

        List<Property> construction_results = new ArrayList<>();

        if(constructionDate != null) {
            construction_results.addAll(commercialPropertyRepository.findByConstructionDate(constructionDate));
            construction_results.addAll(houseRepository.findByConstructionDate(constructionDate));
            construction_results.addAll(parkingRepository.findByConstructionDate(constructionDate));
        }


// Χρησιμοποιούμε Sets για να βρούμε τα κοινά properties
        Set<Property> commonResults = new HashSet<>(property_results);
        System.out.println("1. "+ commonResults);


        // Κοινά μεταξύ location και  price
        commonResults.retainAll(price_results); // Κοινά μεταξύ location και price

        System.out.println("2. "+ commonResults);

        // Κοινά μεταξύ location και slider size
        commonResults.retainAll(size_results); // Κοινά μεταξύ location και  size

        System.out.println("3. "+ commonResults);

        if(constructionDate != null || !propertyType.equals("Land")){
            commonResults.retainAll(construction_results); // Κοινά μεταξύ location και construction date
            System.out.println("4. "+ commonResults);
        }

        if(buildingFees && ( propertyType.equals("House") || propertyType.equals("Commercial Property") )) {
            commonResults.retainAll(bf_results); // Κοινά μεταξύ location και building fees
            System.out.println("5. "+ commonResults);
        }

        System.out.println("common results"+commonResults);
        System.out.println("property results" + property_results);
        System.out.println("price results" +price_results);
        System.out.println("size results" +size_results);
        System.out.println("bf results" +bf_results);
        System.out.println("construt results" +construction_results);
        System.out.println("Properties Found: " + commonResults.size());            // Thelw ena kalutero tropo gia anazhthsh

        return new ArrayList<>(commonResults);
    }

    @Transactional
    public Integer updateProperty(Property property) {
        property=propertyRepository.save(property);
        return property.getId();
    }
    public List findAllProperties() {
        return propertyRepository.findByAvailability(true);
    }

    public List<Property> findAllInvisibleProperties(){return propertyRepository.findByVisibility("Invisible");}

    public Property getPropertyById(int id) {
        return propertyRepository.findPropertyById(id);
    }

    public void SaveLandProperty(Land land) {
        landRepository.save(land);
    }

    public void SaveHouseProperty(House house){houseRepository.save(house);}

    public void SaveParkingProperty(Parking parking){parkingRepository.save(parking);}

    public void SaveCommercialProperty(CommercialProperty commercialProperty){commercialPropertyRepository.save(commercialProperty);}

    public void DeleteProperty(Property property) {propertyRepository.delete(property);}


    public Integer findOwnerIdByPropertyId(int propertyId) {
        return propertyRepository.findOwnerIdByPropertyId(propertyId);
    }
}
