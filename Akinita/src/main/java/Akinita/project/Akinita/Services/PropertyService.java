package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Properties.*;
import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Repositories.RealEstate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PropertyService {


    PropertyRepository propertyRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    LandRepository landRepository;
    @Autowired
    ParkingRepository parkingRepository;
    @Autowired
    CommercialPropertyRepository commercialPropertyRepository;


    public int SaveProperty(RealEstate property) {
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

    public List findProperties(String area, String propertyType, Double minPrice, Double maxPrice, Double minSize, Double maxSize, Boolean buildingFees, Date constructionDate) {

        if (propertyType.equals("All")) {
            return propertyRepository.findAll();
        }

        // Πάρε τα αποτελέσματα από τις αναζητήσεις
        List<Property> property_results = propertyRepository.findByLocation(area);
        List<Property> price_greater_results = propertyRepository.findByPriceGreaterThanEqual(minPrice);
        List<Property> price_less_results = propertyRepository.findByPriceLessThanEqual(maxPrice);
        List<Property> bf_results = new ArrayList<>();
        bf_results.addAll(houseRepository.findByBuildingFees(buildingFees));
        bf_results.addAll(commercialPropertyRepository.findByBuildingFees(buildingFees));

        List<Property> construction_results = new ArrayList<>();
        construction_results.addAll(commercialPropertyRepository.findByConstructionDate(constructionDate));
        construction_results.addAll(houseRepository.findByConstructionDate(constructionDate));
        construction_results.addAll(parkingRepository.findByConstructionDate(constructionDate));

// Χρησιμοποιούμε Sets για να βρούμε τα κοινά properties
        Set<Property> commonResults = new HashSet<>(property_results);
        commonResults.retainAll(price_greater_results); // Κοινά μεταξύ location και min price
        commonResults.retainAll(price_less_results); // Κοινά μεταξύ location και max price
        commonResults.retainAll(bf_results); // Κοινά μεταξύ location και building fees
        commonResults.retainAll(construction_results); // Κοινά μεταξύ location και construction date

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
}
