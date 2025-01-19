package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Properties.*;
import Akinita.project.Akinita.Repositories.RealEstate.*;
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

        List<Property> property_results = new ArrayList<>();

        // Πάρε τα αποτελέσματα από τις αναζητήσεις
        if (area != null) {
            property_results = propertyRepository.findByLocation(area);
        } else {
            property_results = propertyRepository.findAll();
        }

        List<Property> price_greater_results = new ArrayList<>();
        List<Property> price_less_results = new ArrayList<>();

        if(priceSlider == null) {
            price_greater_results = propertyRepository.findByPriceGreaterThanEqual(minPrice);
            price_less_results = propertyRepository.findByPriceLessThanEqual(maxPrice);
        } else {
            price_less_results = propertyRepository.findByPriceLessThanEqual(priceSlider);
        }

        List<Property> size_greater_results = new ArrayList<>();
        List<Property> size_less_results = new ArrayList<>();

        if(sizeSlider == null) {
            size_greater_results = propertyRepository.findByPriceGreaterThanEqual(minSize);
            size_less_results = propertyRepository.findBySquareMeterLessThanEqual(maxSize);
        } else {
            size_less_results = propertyRepository.findBySquareMeterLessThanEqual(priceSlider);
        }

        List<Property> bf_results = new ArrayList<>();
        bf_results.addAll(houseRepository.findByBuildingFees(buildingFees));
        bf_results.addAll(commercialPropertyRepository.findByBuildingFees(buildingFees));

        List<Property> construction_results = new ArrayList<>();

        if(constructionDate != null) {
            construction_results.addAll(commercialPropertyRepository.findByConstructionDate(constructionDate));
            construction_results.addAll(houseRepository.findByConstructionDate(constructionDate));
            construction_results.addAll(parkingRepository.findByConstructionDate(constructionDate));
        } else {
            construction_results.addAll(propertyRepository.findAll());
        }


// Χρησιμοποιούμε Sets για να βρούμε τα κοινά properties
        Set<Property> commonResults = new HashSet<>(property_results);

        if(priceSlider == 0) {
            commonResults.retainAll(price_greater_results); // Κοινά μεταξύ location και min price
            commonResults.retainAll(price_less_results); // Κοινά μεταξύ location και max price
        } else {
            commonResults.retainAll(price_less_results); // Κοινά μεταξύ location και slider price
        }

        if(sizeSlider == 0) {
            commonResults.retainAll(size_greater_results); // Κοινά μεταξύ location και min size
            commonResults.retainAll(size_less_results); // Κοινά μεταξύ location και max size
        } else {
            commonResults.retainAll(size_less_results); // Κοινά μεταξύ location και slider size
        }

        commonResults.retainAll(bf_results); // Κοινά μεταξύ location και building fees
        commonResults.retainAll(construction_results); // Κοινά μεταξύ location και construction date

        System.out.println("Properties Found: " + commonResults.size());

        return new ArrayList<>(commonResults);
    }




    public List findAllProperties() {
        return propertyRepository.findByAvailability(true);
    }

    public Property getPropertyById(int id) {
        return propertyRepository.findPropertyById(id);
    }

    public void SaveLandProperty(Land land) {
        landRepository.save(land);
    }

    public void SaveHouseProperty(House house){houseRepository.save(house);}

    public void SaveParkingProperty(Parking parking){parkingRepository.save(parking);}

    public void SaveCommercialProperty(CommercialProperty commercialProperty){commercialPropertyRepository.save(commercialProperty);}
}
